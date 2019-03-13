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
import android.widget.TextView;

import uk.ac.ncl.team15.android.R;

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
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView name=(TextView) findViewById(R.id.actualName);
        name.setText(infoArray);
        ImageView img=(ImageView) findViewById(R.id.jamimg);
        img.setImageResource(R.drawable.jam);
        CustomListAdapter whatever = new CustomListAdapter(this,phoneNo,emailArray,dob);
        listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(whatever);

    }

    String [] dob={
            "23/06"
    };

    String infoArray =
          "Example User";
    String[] phoneNo = {
            "01234567890"};
    String[] emailArray = {"a@b.com" };

}


