package uk.ac.ncl.team15.android.util;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.retrofit.models.ModelJob;
import uk.ac.ncl.team15.android.retrofit.models.ModelJobs;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class JobSearchResultListBuilder {
    private List<ModelJob> jobs;

    private List<ResultEntry> results;

    public JobSearchResultListBuilder(List<ModelJob> jobs) {
        this.jobs = jobs;

        doBuildResultsList();
    }

    /**
     * Build an ORDERED list of attributes using correct string translations
     *
     * Converts the ModelUserData datatype into a list of presentable attributes,
     * being aware of null values and excluding them
     */
    private void doBuildResultsList() {
        results = new ArrayList<>();

        // TODO: Use strings.xml for translations
        for (ModelJob job : jobs) {
            results.add(new ResultEntry(job.getId(), job.getTitle(), job.getLocation()));
        }
    }

    public SimpleAdapter buildSimpleAdapter(Context context) {
        // Code below based on: https://stackoverflow.com/questions/8554443/custom-list-item-to-listview-android

        // Create the item mapping
        String[] from = new String[] { "jobResultTitle", "jobResultLocation"};
        int[] to = new int[] { R.id.jobResultTitle, R.id.jobResultLocation};

        List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();

        for (ResultEntry result : this.results) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("_jobId", result.id);
            map.put("jobResultTitle", result.title);
            map.put("jobResultLocation", result.location);
            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.listview_job_search_result, from, to);
        return adapter;
    }

    private static final class ResultEntry {
        final int id;
        final String title;
        final String location;

        ResultEntry(int id, String title, String location) {
            this.id = id;
            this.title = title;
            this.location = location;
        }
    }
}
