package uk.ac.ncl.team15.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of ArrayAdapter to allow multiple layouts to be used easily
 */
public class MultiLayoutAdapter extends ArrayAdapter<Object> {
    private List<Object> data;
    private Map<Class<?>, IGetView> typeMap;

    public MultiLayoutAdapter(Context context, List<Object> data,
                              Class<?>[] objectTypes, IGetView[] viewProviders) {
        super(context, 0, data);
        this.data = data;

        if (objectTypes.length != viewProviders.length)
            throw new IllegalArgumentException(
                    "length of objectTypes array and viewProviders array must be equal");

        typeMap = new HashMap<>();
        for (int i=0; i<objectTypes.length; i++)
            typeMap.put(objectTypes[i], viewProviders[i]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object dObj = data.get(position);
        IGetView provider = typeMap.get(dObj.getClass());
        if (provider == null)
            throw new RuntimeException(
                    "no provider for object of type: " + dObj.getClass().getCanonicalName());
        return provider.getView(getContext(), dObj, position, convertView, parent);
    }

    public interface IGetView {
        public View getView(Context context, Object obj, int position, View convertView, ViewGroup parent);
    }
}
