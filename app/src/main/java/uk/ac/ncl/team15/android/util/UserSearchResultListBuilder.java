package uk.ac.ncl.team15.android.util;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class UserSearchResultListBuilder {
    private List<ModelUser> users;

    private List<ResultEntry> results;

    public UserSearchResultListBuilder(List<ModelUser> users) {
        this.users = users;

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
        for (ModelUser user : users) {
            results.add(new ResultEntry(user.getId(), user.getFullName(), user.getPosition()));
        }
    }

    public SimpleAdapter buildSimpleAdapter(Context context) {
        // Code below based on: https://stackoverflow.com/questions/8554443/custom-list-item-to-listview-android

        // Create the item mapping
        String[] from = new String[] { "userResultName", "userResultPosition"};
        int[] to = new int[] { R.id.userResultName, R.id.userResultPosition};

        List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();

        for (ResultEntry result : this.results) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("_userId", result.id);
            map.put("userResultName", result.name);
            map.put("userResultPosition", result.position);
            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.listview_user_search_result, from, to);
        return adapter;
    }

    private static final class ResultEntry {
        final int id;
        final String name;
        final String position;

        ResultEntry(int id, String name, String position) {
            this.id = id;
            this.name = name;
            this.position = position;
        }
    }
}
