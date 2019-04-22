package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uk.ac.ncl.team15.android.retrofit.models.ModelAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Login");

        setSupportActionBar(toolbar);

        // auth
        username = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(view -> validate(username.getText().toString(), password.getText().toString()));

        // forgot password
        TextView forgotPasswordTv = findViewById(R.id.tvForgotPassword);
        forgotPasswordTv.setOnClickListener((view) -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void validate(String userName, String userPassword) {
        Call<ModelAuth> callMt = SaggezzaApplication.getRetrofitService().fetch_token(userName, userPassword);
        callMt.enqueue(new Callback<ModelAuth>() {
            @Override
            public void onResponse(Call<ModelAuth> call, Response<ModelAuth> response) {
                if (response.code() == 200) {
                    SaggezzaApplication.setUserAuthToken(response.body().getToken());
                    SaggezzaApplication.setUserAuthData(response.body().getUserData());
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
                if (throwable != null)
                    Log.e("RETROFIT", "onFailure(...) fetching login token", throwable);
                Toast.makeText(LoginActivity.this, "Login error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}