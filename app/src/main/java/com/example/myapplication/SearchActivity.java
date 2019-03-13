package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar ;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {
    ListView searchPeople;
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolBar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

//        searchPeople=(ListView) findViewById(R.id.list_search);
//        ArrayList<String> arrayPeople=new ArrayList<String>();
//        arrayPeople.addAll(Arrays.asList(getResources().getStringArray(R.array.my_People)));
//        adapter = new ArrayAdapter<String>(
//                SearchActivity.this,android.R.layout.simple_list_item_1,arrayPeople);
//        searchPeople.setAdapter(adapter);
    }


}
