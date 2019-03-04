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
    private final String[] nameArray;

    //to store the list of countries
    private final String[] email;

    private final String[] phoneNo;
private final String[] info;
    public CustomListAdapter(Activity context, String[] nameArrayParam,String[] phoneNo, String[] emailArrayParam,String [] info)
{
        super(context,R.layout.listview_layout , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
        this.email = emailArrayParam;
        this.phoneNo=phoneNo;
        this.info=info;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_layout, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.NameHint);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.actuaName);
        TextView emailTextField=(TextView) rowView.findViewById(R.id.ActualEmail);
        TextView phoneId=(TextView) rowView.findViewById(R.id.actulPhone);
        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(info[position]);
        emailTextField.setText(email[position]);
        phoneId.setText(phoneNo[position]);
   return rowView;
    }
}
