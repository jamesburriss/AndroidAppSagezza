package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.retrofit.models.ModelUserData;
import uk.ac.ncl.team15.android.util.UserAttribListBuilder;


public class UserProfileActivity extends AppCompatActivity {
    ListView listView;
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

        final ListView userAttribList = findViewById(R.id.userAttributesList);
        final TextView userRealName = findViewById(R.id.userRealName);

        final ModelUserData userData = SaggezzaApplication.getUserAuthData();


        // NOTE: Using SaggezzaApplication.getUserAuthData() is for development purposes
        userRealName.setText(userData.getFirstName() + " " + userData.getLastName());
        userAttribList.setAdapter(new UserAttribListBuilder(userData).buildSimpleAdapter(this));
    }
}


