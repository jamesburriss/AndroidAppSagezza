package uk.ac.ncl.team15.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;

import uk.ac.ncl.team15.android.util.Util;

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
            jobLocationTv.setText(jobData.getLocation());
            jobSalaryTv.setText(String.format(getString(R.string.Activity_Job_Ad_currency_fmt), jobData.getSalary()));
            jobHoursTv.setText(jobData.getHours());

            try {
                String formattedDt = Util.convertISO8601To(jobData.getUpdatedAt(), "UTC", "dd-MM-yyyy HH:mm");
                jobUpdateTv.setText(formattedDt);
            } catch (ParseException pe) {
                // nothing useful to do here
                Log.d("JobAdActivity", "error formatting job updated_at", pe);
            }
        });
    }
}
