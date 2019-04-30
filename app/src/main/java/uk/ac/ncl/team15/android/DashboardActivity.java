package uk.ac.ncl.team15.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.ncl.team15.android.util.DownloadImageTask;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class DashboardActivity extends AppCompatActivity {

    private ModelUser modelUser = null;
    //Do we still want this?
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

        TextView jobPosText = findViewById(R.id.dashboardPosition);
        jobPosText.setText(String.format(getString(R.string.dashboard_position), SaggezzaApplication.getInstance().getUserAuthData().getPosition()));

        ImageView userImg = findViewById(R.id.userImg);
        userImg.setImageResource(R.drawable.user);
        new DownloadImageTask(userImg).execute(SaggezzaApplication.userImageUrl(SaggezzaApplication.getInstance().getUserAuthData()));

         Button tutorialBtn = (Button) findViewById(R.id.btn_tutorial);
         tutorialBtn.setOnClickListener((view) -> {
         Intent intent = new Intent(DashboardActivity.this, TutorialActivity.class);
         startActivity(intent);
         });

        Button teamBtn = (Button) findViewById(R.id.btn_team);
        teamBtn.setOnClickListener((view) -> {
            Intent website = new Intent(android.content.Intent.ACTION_VIEW);
            website.setData(Uri.parse("http://google.com"));
            startActivity(website);
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

                    case  R.id.navigation_team:
                        Intent intent3 = new Intent(DashboardActivity.this, UserSearchActivity.class);
                        intent3.putExtra("_userId", SaggezzaApplication.getInstance().getUserAuthData().getId());
                        startActivity(intent3);
                        break;

                    case  R.id.navigation_myprofile:
                        Intent intent4 = new Intent(DashboardActivity.this, UserProfileActivity.class);
                        intent4.putExtra("_userId", SaggezzaApplication.getInstance().getUserAuthData().getId());
                        startActivity(intent4);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaggezzaApplication.getInstance().setUserAuthData(null);
                        SaggezzaApplication.getInstance().setUserAuthToken(null);
                        finish();
                        Intent intent= new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
