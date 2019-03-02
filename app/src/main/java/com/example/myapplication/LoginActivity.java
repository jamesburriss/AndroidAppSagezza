package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private  EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.EmailId);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button)findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Button has been pressed");
                login.setText("The large text has been ");
                //validate(username.getText().toString(), password.getText().toString());
            }
        });

    }
    private void validate(String userName, String userPassword){
        if((userName.equals("Admin")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(LoginActivity.this, GuestActivity.class);
            startActivity(intent);
        }
    }

}