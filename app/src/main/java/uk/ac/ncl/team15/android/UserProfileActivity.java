package uk.ac.ncl.team15.android;

/**
 * @Purpose: The user profile Activity retrieves and shows
 * users information on where requested.
 *
 * @authors  Callum Errington, Natalie Neo, Io Man Kuan, James Burriss
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
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

    private static final int READ_REQUEST_CODE_UFILE = 42;
    private static final int READ_REQUEST_CODE_IFILE = 22;

    private SaggezzaApplication app;
    private SaggezzaService retrofitService;

    private List<Object> listData;
    private List<ModelFile> files;
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
        // reset lists
        this.listData = null;
        this.files = null;

        final ListView userAttribList = findViewById(R.id.userAttributesList);
        final TextView userRealName = findViewById(R.id.userRealName);
        final ImageView userImg = findViewById(R.id.userImg);
        final ImageView btnAdd = findViewById(R.id.btnAdd);
        final BottomNavigationView bottomNavView = findViewById(R.id.userTab);

        final int userId = getIntent().getIntExtra("_userId", -1);
        assert(userId != -1);

        btnAdd.setVisibility(View.INVISIBLE);
        userImg.setImageResource(R.drawable.user);

        // lambda consumer is called after the service request is complete
        getInstance().getInstance().getUserDataById(userId, (userData) -> {
            this.modelUser = userData;

            new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(userData));
            userRealName.setText(userData.getFullNameWithTitle());
            listData = new ArrayList<>();
            listData.addAll(buildAttribs(this.modelUser));
            ListAdapter adapter = new UserProfileAdapter(UserProfileActivity.this, listData);
            userAttribList.setAdapter(adapter);
        });

        userImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE_IFILE); // ImageFILE
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, READ_REQUEST_CODE_UFILE); // UserFILE
        });

        bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (this.modelUser == null)
                return false;
            btnAdd.setVisibility(View.INVISIBLE);
            ListAdapter adapter = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_info:
                    listData = new ArrayList<>();
                    listData.addAll(buildAttribs(this.modelUser));
                    adapter = new UserProfileAdapter(UserProfileActivity.this, listData);
                    break;
                case R.id.navigation_kin:
                    listData = new ArrayList<>();
                    if (this.modelUser.getNextOfKins() != null)
                        listData.addAll(this.modelUser.getNextOfKins());
                    adapter = new UserProfileAdapter(UserProfileActivity.this, listData);
                    break;
                case R.id.navigation_files:
                    listData = new ArrayList<>();
                    if (files != null) {
                        listData.addAll(files);
                        userAttribList.setAdapter(new FileListAdapter(
                                UserProfileActivity.this, files));
                        btnAdd.setVisibility(View.VISIBLE);
                    }
                    Call<ModelFiles> callFiles =
                            retrofitService.files(this.modelUser.getId());
                    callFiles.enqueue(new Callback<ModelFiles>() {
                        @Override
                        public void onResponse(Call<ModelFiles> call, Response<ModelFiles> response) {
                            if (response.code() == 200) {
                                files = response.body().getFiles();
                                listData = new ArrayList<>();
                                listData.addAll(files);
                                userAttribList.setAdapter(new FileListAdapter(
                                        UserProfileActivity.this, files));
                                btnAdd.setVisibility(View.VISIBLE);
                            }
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
                    break;
                default:
                    return false;
            }
            if (adapter != null)
                userAttribList.setAdapter(adapter);
            return true;
        });

        userAttribList.setOnItemClickListener((adapter, v, position, id) -> {
            if (this.modelUser != null && this.listData != null) {
                Object listObject = listData.get(position);

                if (listObject instanceof UserAttribute)
                    onAtrributeClick((UserAttribute) listObject);
                else if (listObject instanceof ModelNextOfKin)
                    onNOKClick((ModelNextOfKin) listObject);
                else if (listObject instanceof ModelFile)
                    onFileClick((ModelFile) listObject);
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

    private void onFileClick(ModelFile modelFile) {
        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(SaggezzaApplication.userFileUrl(modelUser, modelFile)))
                .setTitle(modelFile.getFilename())
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                //.setDestinationUri()
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .addRequestHeader("Authorization", "Token " + app.getUserAuthToken());

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        Toast.makeText(this, "Downloading...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if ((requestCode == READ_REQUEST_CODE_UFILE || requestCode == READ_REQUEST_CODE_IFILE) && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                byte[] fileContents = null;
                try (InputStream in = getContentResolver().openInputStream(uri)) {
                    DataInputStream din = new DataInputStream(in);
                    fileContents = new byte[in.available()];
                    din.readFully(fileContents);
                } catch (IOException ioe) {
                    Log.e("UserProfileActivity_IO", "error reading file from storage", ioe);
                    Toast.makeText(UserProfileActivity.this, "Error reading file", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                fileContents
                        );

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", "file", requestFile);

                Call<ResponseBody> callUf = null;
                if (requestCode == READ_REQUEST_CODE_UFILE) {
                    callUf = retrofitService.uploadUserFile(this.modelUser.getId(),
                            Util.getFileName(uri, getContentResolver()), body);
                } else if (requestCode == READ_REQUEST_CODE_IFILE) {
                    callUf = retrofitService.uploadStaticFile(this.modelUser.getId(),
                            "img", body);
                }
                callUf.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            reload();
                            Toast.makeText(UserProfileActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                        else
                            Log.w("callUf", "onResponse(" + response.code() + ")");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("callUf", "onFailure(...)", t);
                    }
                });

            }
        }
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


