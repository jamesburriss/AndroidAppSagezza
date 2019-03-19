package uk.ac.ncl.team15.android.util;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class UserAttribListBuilder {
    private ModelUser userData;

    private List<AttribKeyValuePair> attribs;

    public UserAttribListBuilder(ModelUser userData) {
        this.userData = userData;

        doBuildAttribList();
    }

    /**
     * Build an ORDERED list of attributes using correct string translations
     *
     * Converts the ModelUserData datatype into a list of presentable attributes,
     * being aware of null values and excluding them
     */
    private void doBuildAttribList() {
        attribs = new ArrayList<>();

        // TODO: Use strings.xml for translations
        attribs.add(new AttribKeyValuePair("Department", userData.getDepartment()));
        attribs.add(new AttribKeyValuePair("Phone Number", userData.getPhoneNumber()));
        attribs.add(new AttribKeyValuePair("DOB", userData.getDob())); // TODO: This needs to be different per view level...
        attribs.add(new AttribKeyValuePair("Email", userData.getEmail()));
        attribs.add(new AttribKeyValuePair("Gender", userData.getGender()));
        //userData.getManagedBy();
    }

    public SimpleAdapter buildSimpleAdapter(Context context) {
        // Code below based on: https://stackoverflow.com/questions/8554443/custom-list-item-to-listview-android

        // Create the item mapping
        String[] from = new String[] { "attribName", "attribDesc" };
        int[] to = new int[] { R.id.attribName, R.id.attribDesc};

        List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();

        for (AttribKeyValuePair attrib : this.attribs) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("attribName", attrib.key); // This will be shown in R.id.attribName
            map.put("attribDesc", attrib.value); // And this in R.id.attribDesc
            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.listview_user_attribute, from, to);
        return adapter;
    }

    private static final class AttribKeyValuePair {
        final String key, value;

        AttribKeyValuePair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
