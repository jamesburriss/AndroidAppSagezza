package uk.ac.ncl.team15.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.retrofit.models.ModelJob;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DownloadImageTask;

public class JobListAdapter extends ArrayAdapter<ModelJob> {
    private List<ModelJob> data;

    public JobListAdapter(Context context, List<ModelJob> data) {
        super(context, R.layout.listview_job_search_result, data);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelJob mj = data.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_job_search_result, parent, false);
            viewHolder.txtTitle = convertView.findViewById(R.id.jobResultTitle);
            viewHolder.txtLocation = convertView.findViewById(R.id.jobResultLocation);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtTitle.setText(mj.getTitle());
        viewHolder.txtLocation.setText(mj.getLocation());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtLocation;
    }
}
