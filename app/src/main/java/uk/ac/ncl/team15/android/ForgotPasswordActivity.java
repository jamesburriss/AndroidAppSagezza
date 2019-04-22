package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        passwordEmail = (EditText)findViewById(R.id.etPasswordEmail);
        resetPassword = (Button)findViewById(R.id.btnResetPassword);
        cancel = (TextView)findViewById(R.id.tvCancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });
        //Authentication goes here
        //resetPassword.setOnClickListener(){
        // String email = passwordEmail.getText().toString().trim();
        //}
    }
}
