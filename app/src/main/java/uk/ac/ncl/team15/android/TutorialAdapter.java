package uk.ac.ncl.team15.android;

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
            R.drawable.tut_img1,
            R.drawable.tut_img1,
            R.drawable.tut_img1,
            R.drawable.tut_img1
    };

    // Slide titles: rename as needed
    public String[] tut_slide_title = {
            "TITLE 1",
            "TITLE 2",
            "TITLE 3",
            "TITLE 4"
    }   ;

    // Slide descriptions: re-write as needed
    public String[] tut_slide_desc = {
            "Sample description (long desc) 1, Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Sample description 2,",
            "Sample description 3, 1234567890!@#$%^&*()-+=",
            "Sample description 4, Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
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
