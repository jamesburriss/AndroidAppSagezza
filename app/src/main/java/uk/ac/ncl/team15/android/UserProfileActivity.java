package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.adapter.UserAttribAdapter;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DialogHelper;
import uk.ac.ncl.team15.android.util.DownloadImageTask;
import uk.ac.ncl.team15.android.adapter.UserAttrib;

import static uk.ac.ncl.team15.android.SaggezzaApplication.getInstance;


public class UserProfileActivity extends AppCompatActivity {
    private ModelUser modelUser = null;
    private ListView listView;

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
            userRealName.setText(userData.getFirstName() + " " + userData.getLastName());
            userAttribList.setAdapter(new UserAttribAdapter(UserProfileActivity.this, UserAttrib.buildAttribs(userData)));
            new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(userData));
        });

        userAttribList.setOnItemClickListener((adapter, v, position, id) -> {
            if (this.modelUser != null) {
                if (this.modelUser.isReadOnly()) {
                    Toast.makeText(UserProfileActivity.this, "You do not have permission to edit this", Toast.LENGTH_LONG).show();
                } else {
                    UserAttrib attrib = (UserAttrib) userAttribList.getItemAtPosition(position);
                    Map<String, String> options = attrib.getOptions();

                    Consumer<String> callback = (val) -> {
                        ModelUser patchModel = new ModelUser();
                        //patchModel.setId(this.modelUser.getId());
                        attrib.getSetter().set(patchModel, val);
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
                        DialogHelper.textInputDialog(UserProfileActivity.this, attrib.getKey(),
                                "Update", "Cancel", attrib.getValue(), callback);
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
}


