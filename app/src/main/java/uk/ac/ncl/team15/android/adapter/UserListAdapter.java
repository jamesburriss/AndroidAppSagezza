package uk.ac.ncl.team15.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.SaggezzaApplication;
import uk.ac.ncl.team15.android.UserProfileActivity;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.DownloadImageTask;

public class UserListAdapter extends ArrayAdapter<ModelUser> {
    private List<ModelUser> data;

    public UserListAdapter(Context context, List<ModelUser> data) {
        super(context, R.layout.listview_user_search_result, data);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelUser mu = data.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_user_search_result, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.userResultName);
            viewHolder.txtPosition = convertView.findViewById(R.id.userResultPosition);
            viewHolder.imgUser = convertView.findViewById(R.id.userImg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(mu.getFullName());
        viewHolder.txtPosition.setText(mu.getPosition());
        viewHolder.imgUser.setImageResource(R.drawable.user);
        new DownloadImageTask(viewHolder.imgUser).execute(SaggezzaApplication.userImageUrl(mu));

        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtPosition;
        ImageView imgUser;
    }
}
