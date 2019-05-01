package uk.ac.ncl.team15.android;

/**
 * @Purpose: The Job Search activity lets the user to find
 *  jobs hosted by Saggezza.
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.ncl.team15.android.adapter.JobListAdapter;
import uk.ac.ncl.team15.android.retrofit.models.ModelJob;
import uk.ac.ncl.team15.android.retrofit.models.ModelJobs;

public class JobSearchActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        lv = findViewById(R.id.jobResultList);

        lv.setOnItemClickListener((adapter, v, position, id) -> {
            ModelJob mj = (ModelJob) lv.getItemAtPosition(position);
            int selectedJobId = mj.getId();

            Intent profileIntent = new Intent(JobSearchActivity.this, JobAdActivity.class);
            profileIntent.putExtra("_jobId", selectedJobId);
            startActivity(profileIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_jobs, menu);
        MenuItem item = menu.findItem(R.id.job_impli_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            {
                onQuery("");
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQuery(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return onQuery(newText);
            }

            private boolean onQuery(String query) {
                Call<ModelJobs> callJs = SaggezzaApplication.getInstance().getRetrofitService().jobs(query, null, null, null, 1);
                callJs.enqueue(new Callback<ModelJobs>() {
                    @Override
                    public void onResponse(Call<ModelJobs> call, Response<ModelJobs> response) {
                        if (response.code() == 200) {
                            ModelJobs responseBody = response.body();
                            List<ModelJob> jobs = responseBody.getJobs();

                            lv.setAdapter(new JobListAdapter(JobSearchActivity.this.getApplicationContext(), jobs));
                        }
                        else {
                            Toast.makeText(JobSearchActivity.this, getString(R.string.JobSearch_eroor), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelJobs> call, Throwable throwable) {
                        Toast.makeText(JobSearchActivity.this, getString(R.string.JobSearch_eroor), Toast.LENGTH_LONG).show();
                    }
                });
                // adapter.clear(); // NOTE: Uncomment this to clear list while waiting for network
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}

