package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TutorialAdapter tutorialAdapter;
    private LinearLayout navDotsLayout;

    private TextView[] navDots;
    private Button btn_Next;
    private Button btn_Back;
    private Button btn_Fin;
    private int currPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tutorialAdapter = new TutorialAdapter(this);
        viewPager.setAdapter(tutorialAdapter);
        navDotsLayout = (LinearLayout) findViewById(R.id.navDots);

        btn_Next = (Button) findViewById(R.id.btnNext);
        btn_Back = (Button) findViewById(R.id.btnBack);
        btn_Fin = (Button) findViewById(R.id.btnFin);

        addNavDots(0);
        viewPager.addOnPageChangeListener(viewListener);

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currPage + 1);
            }
        });



        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currPage - 1);
            }
        });

        btn_Fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change this part to direct it somewhere else
                //viewPager.setCurrentItem(currPage);
            }
        });
        
//        CardView userSearchCard = findViewById(R.id.userSearchCard);
//        userSearchCard.setOnClickListener((view) -> {
//            Intent intent = new Intent(TutorialActivity.this,DashboardActivity.class);
//            startActivity(intent);
//        });
    }

    public void addNavDots(int position) {
        navDots = new TextView[4];
        navDotsLayout.removeAllViews();

        for (int i = 0; i < navDots.length; i++) {
            navDots[i] = new TextView(this);
            navDots[i].setText(Html.fromHtml("•"));
            navDots[i].setTextSize(35);
            navDots[i].setTextColor(ContextCompat.getColor(this, R.color.transparent_white));

            navDotsLayout.addView(navDots[i]);
        }

        if (navDots.length > 0) {
            navDots[position].setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addNavDots(i);
            currPage = i;

            // Navigation buttons
            if (i == 0) {
                btn_Next.setEnabled(true);
                btn_Next.setText("Next");

                btn_Back.setEnabled(false);
                btn_Back.setText("");
                btn_Back.setVisibility(View.INVISIBLE);

            } else if (i == navDots.length - 1){
                btn_Next.setEnabled(true);
                btn_Next.setText("");
                btn_Next.setVisibility(View.INVISIBLE);

                btn_Fin.setEnabled(true);
                btn_Fin.setText("Finish");
                btn_Fin.setVisibility(View.VISIBLE);


                btn_Back.setEnabled(true);
                btn_Back.setText("Back");
                btn_Back.setVisibility(View.VISIBLE);
            } else {
                btn_Next.setEnabled(true);
                btn_Next.setText("Next");

                btn_Back.setEnabled(true);
                btn_Back.setText("Back");
                btn_Back.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
