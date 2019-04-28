package uk.ac.ncl.team15.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
        Button tutorialButton = (Button) findViewById(R.id.btn_Tut);

        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TutorialActivity.class));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                finish();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.upload_menu:
                finish();

                final int PICKFILE_REQUEST_CODE = 8778;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("file/*");
                startActivityForResult(i, PICKFILE_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
