package uk.ac.ncl.team15.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import uk.ac.ncl.team15.android.util.UserAttribListBuilder;

public class JobAdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_ad);

        final TextView jobTitleTv = findViewById(R.id.jobTitle);
        final TextView jobDescriptionTv = findViewById(R.id.DescriptionBox);

        final int jobId = getIntent().getIntExtra("_jobId", -1);
        assert(jobId != -1);

        // lambda consumer is called after the service request is complete
        SaggezzaApplication.getJobDataById(jobId, (jobData) -> {
            jobTitleTv.setText(jobData.getTitle());
            jobDescriptionTv.setText(jobData.getDescription());
        });
    }
}
