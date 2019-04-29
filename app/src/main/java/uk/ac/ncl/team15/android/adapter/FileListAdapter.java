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
import uk.ac.ncl.team15.android.SaggezzaApplication;
import uk.ac.ncl.team15.android.retrofit.models.ModelFile;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DownloadImageTask;
import uk.ac.ncl.team15.android.util.Util;

public class FileListAdapter extends ArrayAdapter<ModelFile> {
    private List<ModelFile> data;

    public FileListAdapter(Context context, List<ModelFile> data) {
        super(context, R.layout.listview_file_search_result, data);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelFile mf = data.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_file_search_result, parent, false);
            viewHolder.tvFileDesc = convertView.findViewById(R.id.tvFileDesc);
            viewHolder.tvFileUpdated = convertView.findViewById(R.id.tvFileUpdated);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvFileDesc.setText(
                String.format("%s (%s)",
                        mf.getFilename(), Util.humanReadableByteCount(mf.getSize(), false)));
        viewHolder.tvFileUpdated.setText(mf.getLastModified());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvFileDesc;
        TextView tvFileUpdated;
    }
}
