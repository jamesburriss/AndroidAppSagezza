package uk.ac.ncl.team15.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobAdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_ad);

        final TextView jobTitleTv = findViewById(R.id.jobTitle);
        final TextView jobDescriptionTv = findViewById(R.id.DescriptionBox);
        final TextView jobLocationTv = findViewById(R.id.jobLocation);
        final TextView jobSalaryTv = findViewById(R.id.jobSalary);
        final TextView jobHoursTv = findViewById(R.id.jobHours);
        final TextView jobSkillTv = findViewById(R.id.jobSkill);
        final TextView jobUpdateTv = findViewById(R.id.jobUpdate); //For the date

        final int jobId = getIntent().getIntExtra("_jobId", -1);
        assert(jobId != -1);

        // lambda consumer is called after the service request is complete
        SaggezzaApplication.getInstance().getJobDataById(jobId, (jobData) -> {
            jobTitleTv.setText(jobData.getTitle());
            jobDescriptionTv.setText(jobData.getDescription());
        });
    }
}
