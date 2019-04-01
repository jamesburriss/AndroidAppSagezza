package uk.ac.ncl.team15.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobAdPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView jobDesc=findViewById(R.id.DescriptionBox);
        jobDesc.setText("Take part in exciting talks and specialist panels from key areas of creative " +
                "industry in music, film/TV and software. Hear inspirational stories and get expert advice and guidance, " +
                "as well as a chance to network with experienced speakers and visit specialist industry exhibition stands " +
                "including Generator and the Musicians Unions. If you’ve always wondered what " +
                "working in these creative sectors involves, " +
                "this is the chance to find out and get advice on how to take the next steps in your creative careers.");
        setContentView(R.layout.activity_job_ad_page);
    }
}
