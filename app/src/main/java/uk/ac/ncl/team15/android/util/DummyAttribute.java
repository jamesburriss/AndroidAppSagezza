package uk.ac.ncl.team15.android.util;

/**
 * A class representing a dummy user attribute to allow the UI to be manipulated
 */
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
