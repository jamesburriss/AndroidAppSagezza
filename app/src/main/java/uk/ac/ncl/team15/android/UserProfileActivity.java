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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        ListView userAttribList = findViewById(R.id.userAttributesList);

        // Below from: https://stackoverflow.com/questions/8554443/custom-list-item-to-listview-android
        // Create the item mapping
        String[] from = new String[] { "attribName", "attribDesc" };
        int[] to = new int[] { R.id.attribName, R.id.attribDesc};

        // Add some rows
        List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("attribName", "Gender"); // This will be shown in R.id.attribName
        map.put("attribDesc", "male"); // And this in R.id.attribDesc
        fillMaps.add(map);

        map = new HashMap<String, Object>();
        map.put("attribName", "Nationality");
        map.put("attribDesc", "British");
        fillMaps.add(map);

        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.listview_layout, from, to);
        userAttribList.setAdapter(adapter);
    }
}


