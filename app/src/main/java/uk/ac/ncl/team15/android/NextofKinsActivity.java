package uk.ac.ncl.team15.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NextofKinsActivity extends AppCompatActivity {

    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText relationship;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        username = (EditText)findViewById(R.id.etUsername);
        firstName = (EditText)findViewById(R.id.etFirstName);
        lastName = (EditText)findViewById(R.id.etLastName);
        relationship = (EditText)findViewById(R.id.etRelationship);
        address = (EditText)findViewById(R.id.etAddress);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextof_kins);
    }
}
