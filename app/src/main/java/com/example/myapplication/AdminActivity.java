package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class AdminActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray);
        listView = (ListView) findViewById(R.id.ListViewId);
        listView.setAdapter(whatever);

    }
    String[] nameArray = {"Husain","19"};

    String[] infoArray = {
          "Smart","CSC"};
}
