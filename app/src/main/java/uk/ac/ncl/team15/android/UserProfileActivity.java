package uk.ac.ncl.team15.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.adapter.FileListAdapter;
import uk.ac.ncl.team15.android.adapter.UserProfileAdapter;
import uk.ac.ncl.team15.android.retrofit.SaggezzaService;
import uk.ac.ncl.team15.android.retrofit.models.ModelFile;
import uk.ac.ncl.team15.android.retrofit.models.ModelFiles;
import uk.ac.ncl.team15.android.retrofit.models.ModelNextOfKin;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DialogHelper;
import uk.ac.ncl.team15.android.util.DownloadImageTask;
import uk.ac.ncl.team15.android.util.DummyAttribute;
import uk.ac.ncl.team15.android.util.StaticAttributeMap;
import uk.ac.ncl.team15.android.util.UserAttributeValidators;
import uk.ac.ncl.team15.android.util.Util;

import static uk.ac.ncl.team15.android.SaggezzaApplication.getInstance;


public class UserProfileActivity extends AppCompatActivity {
    private static StaticAttributeMap<String, String> MAP_GENDER =
        new StaticAttributeMap<String, String>()
            .map("M", "Male")
            .map("F", "Female");

    private static StaticAttributeMap<String, String> MAP_MARITAL_STATUS =
        new StaticAttributeMap<String, String>()
            .map("S", "Single")
            .map("M", "Married")
            .map("W", "Widowed")
            .map("D", "Separated");

    private static final int READ_REQUEST_CODE = 42;

    private SaggezzaApplication app;
    private SaggezzaService retrofitService;

    private List<Object> listData;
    private ModelUser modelUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        this.app = SaggezzaApplication.getInstance();
        this.retrofitService = this.app.getRetrofitService();

        reload();
    }

    private void reload() {
        final ListView userAttribList = findViewById(R.id.userAttributesList);
        final TextView userRealName = findViewById(R.id.userRealName);
        final ImageView userImg = findViewById(R.id.userImg);

        final int userId = getIntent().getIntExtra("_userId", -1);
        assert(userId != -1);

        userImg.setImageResource(R.drawable.user);

        // lambda consumer is called after the service request is complete
        getInstance().getInstance().getUserDataById(userId, (userData) -> {
            this.modelUser = userData;

            new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(userData));
            userRealName.setText(userData.getFullNameWithTitle());

            listData = new ArrayList<>();
            listData.addAll(buildAttribs(userData));
            if (userData.getNextOfKins() != null)
                listData.addAll(userData.getNextOfKins());
            if (userData.getVisibility() >= Constants.VISIBILITY_ADMIN)
                listData.add(new DummyAttribute("Files", "Click to view", "files"));
            ListAdapter adapter = new UserProfileAdapter(UserProfileActivity.this, listData);
            userAttribList.setAdapter(adapter);
        });

        userAttribList.setOnItemClickListener((adapter, v, position, id) -> {
            if (this.modelUser != null && this.listData != null) {
                Object listObject = listData.get(position);

                if (listObject instanceof UserAttribute)
                    onAtrributeClick((UserAttribute) listObject);
                else if (listObject instanceof ModelNextOfKin)
                    onNOKClick((ModelNextOfKin) listObject);
                else if (listObject instanceof DummyAttribute)
                    onDummyAttributeClick((DummyAttribute) listObject);
            }
        });
    }

    private void onAtrributeClick(UserAttribute attrib) {
        if (this.modelUser.isReadOnly()) {
            Toast.makeText(UserProfileActivity.this, "You do not have permission to edit this", Toast.LENGTH_LONG).show();
        } else {
            Map<String, String> options = attrib.getOptions();

            Consumer<String> callback = (val) -> {
                if (!attrib.isValid(this.modelUser, val)) {
                    Toast.makeText(UserProfileActivity.this, "Please enter a valid value", Toast.LENGTH_LONG).show();
                    return;
                }
                ModelUser patchModel = new ModelUser();
                //patchModel.setId(this.modelUser.getId());
                attrib.setValue(patchModel, val);
                Call<ResponseBody> callRb = retrofitService.patchUser(this.modelUser.getId(), patchModel);
                callRb.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int msgRes = response.code() == 200 ? R.string.toast_update_saved : R.string.toast_update_failed;
                        Toast.makeText(UserProfileActivity.this, msgRes, Toast.LENGTH_LONG).show();
                        reload();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(UserProfileActivity.this, R.string.toast_update_failed, Toast.LENGTH_LONG).show();
                        if (throwable != null)
                            Log.e("UserProfileActivity_RETROFIT", "patchUser(" + UserProfileActivity.this.modelUser.getId() + ")", throwable);
                    }
                });
            };

            if (options == null) {
                Function<String, Boolean> validationFunc =
                        (val) -> attrib.isValid(modelUser, val);
                DialogHelper.textInputDialog(UserProfileActivity.this,
                        attrib.getKey(), "Update", "Cancel",
                        attrib.getValue(), callback, validationFunc);
            } else {
                String[] keys = new String[options.size()];
                options.keySet().toArray(keys);
                DialogHelper.dropdownSelectionDialog(
                        UserProfileActivity.this,
                        attrib.getKey(), keys,
                        "Cancel", (val) -> callback.accept(options.get(val)));
            }
        }
    }

    private void onNOKClick(ModelNextOfKin modelNok) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Modify Details");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_nextof_kins, null);
        final EditText etRelationship = view.findViewById(R.id.etRelationship);
        final EditText etFirstName = view.findViewById(R.id.etFirstName);
        final EditText etLastName = view.findViewById(R.id.etLastName);
        final EditText etAddress = view.findViewById(R.id.etAddress);
        etRelationship.setText(modelNok.getRelationship());
        etFirstName.setText(modelNok.getFirstName());
        etLastName.setText(modelNok.getLastName());
        etAddress.setText(modelNok.getAddress());
        builder.setView(view);

        // Set up the buttons
        builder.setPositiveButton("Update", ((dialog, which) -> {
            ModelNextOfKin patchModel = new ModelNextOfKin();
            patchModel.setRelationship(etRelationship.getText().toString());
            patchModel.setFirstName(etFirstName.getText().toString());
            patchModel.setLastName(etLastName.getText().toString());
            patchModel.setAddress(etAddress.getText().toString());
            Call<ResponseBody> callRb = retrofitService.nextOfKin(modelNok.getId(), patchModel);
            callRb.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int msgRes = response.code() == 200 ? R.string.toast_update_saved : R.string.toast_update_failed;
                    Toast.makeText(UserProfileActivity.this, msgRes, Toast.LENGTH_LONG).show();
                    reload();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(UserProfileActivity.this, R.string.toast_update_failed, Toast.LENGTH_LONG).show();
                    if (throwable != null)
                        Log.e("UserProfileActivity_RETROFIT", "nextOfKin(" + UserProfileActivity.this.modelUser.getId() + ", patchModel)", throwable);
                }
            });
        }));
        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));

        AlertDialog alertDialog = builder.create();

        TextWatcher twNotEmpty = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        etRelationship.addTextChangedListener(twNotEmpty);
        etFirstName.addTextChangedListener(twNotEmpty);
        etLastName.addTextChangedListener(twNotEmpty);
        etAddress.addTextChangedListener(twNotEmpty);

        alertDialog.show();
    }

    private void onDummyAttributeClick(DummyAttribute attrib) {
        if ("files".equals(attrib.getTag())) {
            Call<ModelFiles> callFiles =
                    retrofitService.files(this.modelUser.getId());
            callFiles.enqueue(new Callback<ModelFiles>() {
                @Override
                public void onResponse(Call<ModelFiles> call, Response<ModelFiles> response) {
                    if (response.code() == 200)
                        displayFiles(response.body().getFiles());
                    else
                        Toast.makeText(UserProfileActivity.this, "Error loading files", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ModelFiles> call, Throwable throwable) {
                    Toast.makeText(UserProfileActivity.this, "Error loading files", Toast.LENGTH_LONG).show();
                    if (throwable != null)
                        Log.e("UserProfileActivity_RETROFIT", "files(" +
                                UserProfileActivity.this.modelUser.getId() + ")", throwable);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                byte[] fileContents = null;
                try {
                    InputStream in = getContentResolver().openInputStream(uri);
                    fileContents = new byte[in.available()];
                } catch (IOException ioe) {
                    Log.e("UserProfileActivity_IO", "error reading file from storage", ioe);
                    Toast.makeText(UserProfileActivity.this, "Error reading file", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(getContentResolver().getType(uri)),
                                fileContents
                        );

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", "file", requestFile);

                Call<ResponseBody> callUf =
                        retrofitService.uploadUserFile(this.modelUser.getId(),
                                Util.getFileName(uri, getContentResolver()), body);
                callUf.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("callUf", "onResponse(" + response.code() + ")");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("callUf", "onFailure(...)", t);
                    }
                });

            }
        }
    }

    private void displayFiles(List<ModelFile> files) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Files");

        LayoutInflater inflater = this.getLayoutInflater();
        ListView listView = new ListView(this);
        listView.setAdapter(new FileListAdapter(this, files));
        builder.setView(listView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static List<UserAttribute> buildAttribs(ModelUser modelUser) {
        List<UserAttribute> attribs = new ArrayList<>();

        attribs.add(
                new UserAttribute(
                        "Position", modelUser.getPosition(), // key, value
                        (mu, val) -> mu.setPosition(val) // setter
                ).setValidator(UserAttributeValidators.NOT_EMPTY));
        attribs.add(
                new UserAttribute(
                        "Phone Number", modelUser.getPhoneNumber(),
                        (mu, val) -> mu.setPhoneNumber(val)
                ).setValidator(UserAttributeValidators.PHONE_NUM));

        if (modelUser.getVisibility() == Constants.VISIBILITY_PUBLIC) {
            attribs.add(
                    new UserAttribute(
                            "Birthday", modelUser.getDob(),
                            (mu, val) -> mu.setDob(val)
                    ).setValidator(UserAttributeValidators.DOB));
        }

        if (modelUser.getVisibility() >= Constants.VISIBILITY_PRIVATE) {
            attribs.add(
                    new UserAttribute(
                            "Address", modelUser.getAddress(),
                            (mu, val) -> mu.setAddress(val)
                    ).setValidator(UserAttributeValidators.NOT_EMPTY));
            attribs.add(
                    new UserAttribute(
                            "Company Email", modelUser.getEmail(),
                            (mu, val) -> mu.setEmail(val)
                    ).setValidator(UserAttributeValidators.EMAIL));
            attribs.add(
                    new UserAttribute(
                            "Personal Email", modelUser.getPersonalEmail(),
                            (mu, val) -> mu.setPersonalEmail(val)
                    ).setValidator(UserAttributeValidators.EMAIL));
            attribs.add(
                    new UserAttribute(
                            "DOB", modelUser.getDob(), // TODO: Process this value correctly
                            (mu, val) -> mu.setDob(val))
                            .setValidator(UserAttributeValidators.DOB));
            // TODO: Next of kin
        }

        if (modelUser.getVisibility() >= Constants.VISIBILITY_ADMIN) {
            attribs.add(
                    new UserAttribute(
                            "Marital Status", MAP_MARITAL_STATUS.readable(modelUser.getMaritalStatus()),
                            (mu, val) -> mu.setMaritalStatus(val)
                    ).setOptions(MAP_MARITAL_STATUS.getMapToSymbol()));
            attribs.add(
                    new UserAttribute(
                            "Nationality", modelUser.getNationality(),
                            (mu, val) -> mu.setNationality(val)
                    ).setValidator(UserAttributeValidators.NOT_EMPTY));
            attribs.add(
                    new UserAttribute(
                            "Visa Status", modelUser.getVisaStatus(),
                            (mu, val) -> mu.setVisaStatus(val)
                    ).setValidator(UserAttributeValidators.NOT_EMPTY));
            attribs.add(
                    new UserAttribute(
                            "Medical Conditions", modelUser.getMedicalConditions(),
                            (mu, val) -> mu.setMedicalConditions(val)
                    ).setValidator(UserAttributeValidators.NOT_EMPTY));
            attribs.add(
                    new UserAttribute(
                            "Languages Spoken", modelUser.getLanguagesSpoken(),
                            (mu, val) -> mu.setLanguagesSpoken(val)
                    ).setValidator(UserAttributeValidators.NOT_EMPTY));
            attribs.add(
                    new UserAttribute(
                            "Gender", MAP_GENDER.readable(modelUser.getGender()),
                            (mu, val) -> mu.setGender(val)
                    ).setOptions(MAP_GENDER.getMapToSymbol()));
        }

        return attribs;
    }
}


