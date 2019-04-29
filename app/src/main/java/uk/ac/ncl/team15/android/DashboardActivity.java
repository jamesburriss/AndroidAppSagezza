package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (SaggezzaApplication.getInstance().getUserAuthData() == null) {
            Log.w("DashboardActivity", "userData is null!");
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.DashboardActivity_title);
        setSupportActionBar(toolbar);

        TextView welcomeText = findViewById(R.id.dashboardWelcomeText);
        welcomeText.setText(String.format(getString(R.string.dashboard_welcome), SaggezzaApplication.getInstance().getUserAuthData().getFirstName()));

        CardView userSearchCard = findViewById(R.id.userSearchCard);
        userSearchCard.setOnClickListener((view) -> {
            Intent intent = new Intent(DashboardActivity.this, UserSearchActivity.class);
            startActivity(intent);
        });

        CardView jobSearchCard = findViewById(R.id.jobSearchCard);
        jobSearchCard.setOnClickListener((view) -> {
            Intent intent = new Intent(DashboardActivity.this, JobSearchActivity.class);
            startActivity(intent);
        });

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firststart", true);


        if (firstStart){

            Button tutorialButton = (Button) findViewById(R.id.btn_Tut);
            tutorialButton.setVisibility(View.VISIBLE);
            tutorialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this, TutorialActivity.class));
                }
            });
           SharedPreferences.Editor editor = prefs.edit();
           editor.putBoolean("firststart", false);
           editor.apply();
        }


    BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navigation_jobs:
                        Intent intent1 = new Intent(DashboardActivity.this, JobSearchActivity.class);
                        startActivity(intent1);
                        break;

                    case  R.id.navigation_employees:
                        Intent intent2 = new Intent(DashboardActivity.this, UserSearchActivity.class);
                        startActivity(intent2);
                        break;

                    case  R.id.navigation_myprofile:
                        Intent intent3 = new Intent(DashboardActivity.this, UserProfileActivity.class);
                        intent3.putExtra("_userId", SaggezzaApplication.getInstance().getUserAuthData().getId());
                        startActivity(intent3);
                        break;
                }
                return false;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                SaggezzaApplication.getInstance().setUserAuthData(null);
                SaggezzaApplication.getInstance().setUserAuthToken(null);
                finish();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.help_menu:
                finish();
                Intent intent1 = new Intent(DashboardActivity.this, TutorialActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }


}
