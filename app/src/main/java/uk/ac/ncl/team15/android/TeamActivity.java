package uk.ac.ncl.team15.android;

/**
 * @Purpose: The Team activity is made to show the users
 *  team.
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.ncl.team15.android.R;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
    }
}
