package uk.ac.ncl.team15.android.util;

/**
 * @Purpose: A class representing a dummy user attribute to allow the UI to be manipulated
 *
 * @authors  Callum
 * @since   1/5/2018
 * extended by @authors
 *
 **/

public class DummyAttribute {
    private String key, value;
    private Object tag;

    public DummyAttribute(String key, String value, Object tag) {
        this.key = key;
        this.value = value;
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Object getTag() {
        return tag;
    }
}
