package uk.ac.ncl.team15.android.util;

/**
 * @Purpose: Misc utility class
 *
 * @authors  Callum
 * @since   1/5/2018
 * extended by @authors
 *
 **/

public class ValueContainer<T>
{
    private T value = null;

    public ValueContainer() {}

    public ValueContainer(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}
