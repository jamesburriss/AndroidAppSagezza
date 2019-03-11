package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the list of countries
    private final String[] email;
    private final String [] dateOfBirth;

    private final String[] phoneNo;
//private final String[] info;
    public CustomListAdapter(Activity context,String[] phoneNo, String[] emailArrayParam,String [] dateOfBirth)
{
        super(context,R.layout.listview_layout , emailArrayParam);
        this.context=context;
        this.email = emailArrayParam;
        this.phoneNo=phoneNo;
        this.dateOfBirth=dateOfBirth;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_layout, null,true);

        //this code gets references to objects in the listview_row.xml file
        //TextView infoTextField = (TextView) rowView.findViewById(R.id.actualName);
        TextView emailTextField=(TextView) rowView.findViewById(R.id.actualEmail);
        TextView phoneId=(TextView) rowView.findViewById(R.id.actualPhone);
        TextView dateofBirthTextField=(TextView) rowView.findViewById(R.id.actual_dob);
        //this code sets the values of the objects to values from the arrays
        emailTextField.setText(email[position]);
        phoneId.setText(phoneNo[position]);
        dateofBirthTextField.setText(dateOfBirth[position]);
   return rowView;
    }
}
