package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DialogHelper;
import uk.ac.ncl.team15.android.util.DownloadImageTask;
import uk.ac.ncl.team15.android.util.StaticAttributeMap;

import static uk.ac.ncl.team15.android.SaggezzaApplication.getInstance;


public class UserProfileActivity extends AppCompatActivity {
    private static final UserAttribute.UserAttribValidator NOT_EMPTY = ((mu, val) -> val != null && !val.isEmpty());

    private static StaticAttributeMap<String, String> MAP_GENDER =
        new StaticAttributeMap<String, String>()
            .map("M", "Male")
            .map("F", "Female");

    private ModelUser modelUser = null;
    private List<UserAttribute> userAttributes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                Intent intent = new Intent(UserProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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
            this.userAttributes = buildAttribs(userData);

            new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(userData));
            userRealName.setText(userData.getFirstName() + " " + userData.getLastName());

            ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
            for (UserAttribute attrib : userAttributes) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("key", attrib.getKey());
                map.put("value", attrib.getValue());
                if (attrib.getActionCallback() != null) {
                    map.put("actionIcon", attrib.getActionIcon());
                    map.put("_onclick", (View.OnClickListener) v -> {
                        attrib.onAction(userData);
                    });
                } else {
                    map.put("actionIcon", null);
                }
                maps.add(map);
            }

            if (modelUser.getVisibility() >= Constants.VISIBILITY_PRIVATE) {
                // Add next of kin
            }

            String[] from = {"key", "value", "actionIcon"};
            int[] to= {R.id.attribName, R.id.attribDesc, R.id.imgAttribAction};
            SimpleAdapter adapter = new SimpleAdapter(UserProfileActivity.this,
                    maps, R.layout.listview_user_attribute, from, to);
            userAttribList.setAdapter(adapter);
        });

        userAttribList.setOnItemClickListener((adapter, v, position, id) -> {
            if (this.modelUser != null && this.userAttributes != null) {
                UserAttribute attrib = userAttributes.get(position);

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
                        Call<ResponseBody> callRb = SaggezzaApplication.getInstance().getRetrofitService().patchUser(this.modelUser.getId(), patchModel);
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
                                    Log.e("RETROFIT", "patchUser("+userId+")", throwable);
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
        });
    }

    private static List<UserAttribute> buildAttribs(ModelUser modelUser) {
        List<UserAttribute> attribs = new ArrayList<>();

        attribs.add(
                new UserAttribute(
                    "Position", modelUser.getPosition(), // key, value
                    (mu, val) -> mu.setPosition(val) // setter
                ).setValidator(NOT_EMPTY));
        attribs.add(
                new UserAttribute(
                    "Phone Number", modelUser.getPhoneNumber(),
                    (mu, val) -> mu.setPhoneNumber(val))
                );

        if (modelUser.getVisibility() == Constants.VISIBILITY_PUBLIC) {
            attribs.add(
                new UserAttribute(
                    "Birthday", modelUser.getDob(), // TODO: Process this value correctly
                    (mu, val) -> mu.setDob(val)));
        }

        if (modelUser.getVisibility() >= Constants.VISIBILITY_PRIVATE) {
            attribs.add(
                new UserAttribute(
                    "Address", modelUser.getAddress(),
                    (mu, val) -> mu.setAddress(val)));
            attribs.add(
                    new UserAttribute(
                        "Company Email", modelUser.getEmail(),
                        (mu, val) -> mu.setEmail(val)));
            attribs.add(
                    new UserAttribute(
                        "Personal Email", modelUser.getPersonalEmail(),
                        (mu, val) -> mu.setPersonalEmail(val)));
            attribs.add(
                    new UserAttribute(
                        "DOB", modelUser.getDob(), // TODO: Process this value correctly
                        (mu, val) -> mu.setDob(val)));
            // TODO: Next of kin
        }

        if (modelUser.getVisibility() >= Constants.VISIBILITY_ADMIN) {
            attribs.add(
                    new UserAttribute(
                        "Marital Status", modelUser.getMaritalStatus(),
                        (mu, val) -> mu.setMaritalStatus(val)));
            attribs.add(
                    new UserAttribute(
                        "Nationality", modelUser.getNationality(),
                        (mu, val) -> mu.setNationality(val)));
            attribs.add(
                    new UserAttribute(
                        "Visa Status", modelUser.getVisaStatus(),
                        (mu, val) -> mu.setVisaStatus(val)));
            attribs.add(
                    new UserAttribute(
                        "Medical Conditions", modelUser.getMedicalConditions(),
                        (mu, val) -> mu.setMedicalConditions(val)));
            attribs.add(
                    new UserAttribute(
                        "Languages Spoken", modelUser.getLanguagesSpoken(),
                        (mu, val) -> mu.setLanguagesSpoken(val)));
            attribs.add(
                    new UserAttribute(
                        "Gender", MAP_GENDER.readable(modelUser.getGender()),
                        (mu, val) -> mu.setGender(val)
                    ).setOptions(MAP_GENDER.getMapToSymbol()));
        }

        return attribs;
    }

    private static class UserAttribute
    {
        private String key;
        private String value;
        private UserAttribSetter setter;
        private UserAttribValidator validator = null;
        private Consumer<ModelUser> actionCallback = null;
        private Object actionIcon;
        private Map<String, String> options = null;

        UserAttribute(String key, String value, UserAttribSetter setter) {
            this.key = key;
            this.value = value;
            this.setter = setter;
        }

        String getKey() {
            return key;
        }

        String getValue() {
            return value;
        }

        void setValue(ModelUser mu, String val) {
            setter.set(mu, val);
        }

        public UserAttribValidator getValidator() {
            return validator;
        }

        boolean isValid(ModelUser mu, String val) {
            return validator.valid(mu, val);
        }

        UserAttribute setValidator(UserAttribValidator validator) {
            this.validator = validator;
            return this;
        }

        UserAttribute setAction(Object actionIcon, Consumer<ModelUser> actionCallback) {
            this.actionIcon = actionIcon;
            this.actionCallback = actionCallback;
            return this;
        }

        UserAttribute setOptions(Map<String, String> options) {
            this.options = options;
            return this;
        }

        public Object getActionIcon() {
            return actionIcon;
        }

        public Consumer<ModelUser> getActionCallback() {
            return actionCallback;
        }

        void onAction(ModelUser mu) {
            actionCallback.accept(mu);
        }

        Map<String, String> getOptions() {
            if (this.options == null)
                return null;
            return Collections.unmodifiableMap(this.options);
        }

        interface UserAttribValidator {
            public boolean valid(ModelUser mu, String val);
        }

        interface UserAttribSetter {
            public void set(ModelUser mu, String val);
        }
    }
}


