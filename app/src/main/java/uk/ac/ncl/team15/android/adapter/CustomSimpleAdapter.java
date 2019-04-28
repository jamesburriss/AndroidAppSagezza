package uk.ac.ncl.team15.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * An extension of SimpleAdapter which allows an optional per-item
 * onclick listener to be set
 */
public class CustomSimpleAdapter extends SimpleAdapter {
    private List<? extends Map<String, ?>> data;

    public CustomSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Map<String, ?> map = this.data.get(position);
        Object obj = map.get("_onclick");
        if (obj != null) {
            if (!(obj instanceof View.OnClickListener)) {
                throw new RuntimeException("_onclick property of list item " + position
                        + " is not an instance of " + View.OnClickListener.class.getCanonicalName());
            }
            view.setOnClickListener((View.OnClickListener) obj);
        }

        return view;
    }
}
