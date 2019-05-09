package uk.ac.ncl.team15.android;

/**
 * @Purpose: The Forgot Password activity lets customers to retrieve
 * their password by requesting it with their emails
 *
 * @authors  Callum Errington, Io Man Kuan, Husain Chopdawala, Natalie Neo
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.ncl.team15.android.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
}
