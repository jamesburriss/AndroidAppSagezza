package uk.ac.ncl.team15.android;

/**
 * @Purpose: The adapter helper class of Tutorial Activity.
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater ;

    // Slide images: rename as needed
    public int[] tut_slide_img = {
            R.drawable.man,
            R.drawable.employee,
            R.drawable.interview,
            R.drawable.admin
    };

    // Slide titles: rename as needed
    public String[] tut_slide_title = {
            "Your Profile",
            "Employee Page",
            "Job Listings",
            "Admin Functions"
    }   ;

    // Slide descriptions: re-write as needed
    public String[] tut_slide_desc = {
            "On your My Profile page you can view and edit any of your personal or private information. Just simply press and hold on any fields you’d like to change and modify as shown. If your using a guest profile no info is shown.",
            "The employee page allows you to search through all registered users on the app. As you type the search will start to narrow down to employees with relevant names. You can the view the profiles of any employees you have searched.",
            "Like the employee page the job listings page allows you to search any job listings currently posted by Saggezza.",
            "Administrators have extra functions compared to other users. They can view and edit all user’s information to ensure all the information is correct. They will also have out of app permissions to manipulate the database."
    };

    // Slide background colours
    public int[] tut_slide_bgColor = {
            Color.parseColor("#162B75"),
            Color.parseColor("#00bce4"),
            Color.parseColor("#162B75"),
            Color.parseColor("#00bce4")
    };


    public TutorialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return tut_slide_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tut_slide,container,false);
        RelativeLayout tutLayout = (RelativeLayout) view.findViewById(R.id.layout_tutorial);
        ImageView tutImg = (ImageView)  view.findViewById(R.id.tut_img);
        TextView tutTitle= (TextView) view.findViewById(R.id.tut_title);
        TextView tutDesc = (TextView) view.findViewById(R.id.tut_desc);
        tutLayout.setBackgroundColor(tut_slide_bgColor[position]);
        tutImg.setImageResource(tut_slide_img[position]);
        tutTitle.setText(tut_slide_title[position]);
        tutDesc.setText(tut_slide_desc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
