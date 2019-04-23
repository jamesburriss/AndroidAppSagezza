package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.ac.ncl.team15.android.retrofit.models.ModelAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Login");

        setSupportActionBar(toolbar);



        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button)findViewById(R.id.btnLogin);

        login.setOnClickListener(view -> validate(username.getText().toString(), password.getText().toString()));
    }

    private void validate(String userName, String userPassword) {
        Call<ModelAuth> callMt = SaggezzaApplication.getInstance().getRetrofitService().fetch_token(userName, userPassword);
        callMt.enqueue(new Callback<ModelAuth>() {
            @Override
            public void onResponse(Call<ModelAuth> call, Response<ModelAuth> response) {
                if (response.code() == 200) {
                    SaggezzaApplication.getInstance().setUserAuthToken(response.body().getToken());
                    SaggezzaApplication.getInstance().setUserAuthData(response.body().getUserData());
                    Call<ModelUser> callMu = SaggezzaApplication.getInstance().getRetrofitService().self();
                    callMu.enqueue(new Callback<ModelUser>() {
                        @Override
                        public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                            if (response.code() == 200) {
                                Log.v("TESTING", "YEEEEP");
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelUser> call, Throwable throwable) {
                            Log.e("TESTING", "NOOOOPE", throwable);
                        }
                    });
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else if (response.code() == 400)
                    Toast.makeText(LoginActivity.this, "Username or password incorrect!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(LoginActivity.this, "Unable to login: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ModelAuth> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Error performing search", Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", "retrofit service failure", throwable);
            }
        });
    }
}