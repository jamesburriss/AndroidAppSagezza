package uk.ac.ncl.team15.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Button guestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button)findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.textView10);
        guestLogin = (Button) findViewById(R.id.btnGuest);

        login.setOnClickListener(view -> validate(username.getText().toString(), password.getText().toString()));

        guestLogin.setOnClickListener(view -> {
            SaggezzaApplication.getInstance().loginAsGuest();
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        });
    }

    public void forgotPass(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Forgotten your password?");
            alert.setMessage("Please contact an administrator");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
    }

    private void validate(String userName, String userPassword) {
        Call<ModelAuth> callMt = SaggezzaApplication.getInstance().getRetrofitService().fetch_token(userName, userPassword);
        callMt.enqueue(new Callback<ModelAuth>() {
            @Override
            public void onResponse(Call<ModelAuth> call, Response<ModelAuth> response) {
                if (response.code() == 200) {
                    SaggezzaApplication.getInstance().setUserAuthToken(response.body().getToken());
                    SaggezzaApplication.getInstance().setUserAuthData(response.body().getUserData());
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else if (response.code() == 400)
                    Toast.makeText(LoginActivity.this, getString(R.string.LoginActivity_error_credentials), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(LoginActivity.this, getString(R.string.LoginActivity_Unable_to_login) + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ModelAuth> call, Throwable throwable) {
                if (throwable != null)
                    Toast.makeText(LoginActivity.this, getString(R.string.LoginActivity_Unable_to_login) + throwable.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", "retrofit service failure", throwable);
            }
        });
    }
}