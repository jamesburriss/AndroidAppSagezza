package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    ListView listView;
    private String [] info;
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
                Intent intent = new Intent(AdminActivity.this, DashboardActivity.class);
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
        TextView name=(TextView) findViewById(R.id.actualName);
        name.setText(infoArray);
        CustomListAdapter whatever = new CustomListAdapter(this,phoneNo,emailArray,dob);
        listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(whatever);

    }

    String [] dob={
            "23/06"
    };

    String infoArray =
          "James burris";
    String[] phoneNo = {
            "07936459821"};
    String[] emailArray = {"jamesburris@nonce.com" };

}


