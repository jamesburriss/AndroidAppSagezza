package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.retrofit.SaggezzaService;
import com.example.myapplication.retrofit.models.ModelToken;
import com.example.myapplication.retrofit.models.ModelUsers;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.Telephony.Carriers.PASSWORD;

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });

    }

    private void validate(String userName, String userPassword) {
        Call<ModelToken> callMt = SaggezzaApplication.getRetrofitService().fetch_token(userName, userPassword);
        callMt.enqueue(new Callback<ModelToken>() {
            @Override
            public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                if (response.code() == 200) {
                    SaggezzaApplication.setUserAuthToken(response.body().getToken());
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
            public void onFailure(Call<ModelToken> call, Throwable throwable) {
                System.out.println("[!] Get token failed!");
                if (throwable != null)
                    throwable.printStackTrace();
            }
        });
    }
}