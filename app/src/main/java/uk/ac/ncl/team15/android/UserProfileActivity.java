package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import uk.ac.ncl.team15.android.adapter.UserAttribListBuilder;
import uk.ac.ncl.team15.android.util.DownloadImageTask;


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
        final ImageView userImg = findViewById(R.id.userImg);

        final int userId = getIntent().getIntExtra("_userId", -1);
        assert(userId != -1);

        userImg.setImageResource(R.drawable.user);

        // lambda consumer is called after the service request is complete
        SaggezzaApplication.getInstance().getInstance().getUserDataById(userId, (userData) -> {
            userRealName.setText(userData.getFirstName() + " " + userData.getLastName());
            userAttribList.setAdapter(new UserAttribListBuilder(userData).buildSimpleAdapter(this));
            new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(userData));
        });
    }
}


