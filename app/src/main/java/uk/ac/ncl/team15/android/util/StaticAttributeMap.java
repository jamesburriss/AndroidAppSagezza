package uk.ac.ncl.team15.android.util;

import android.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticAttributeMap<T, E>
{
    private Map<T, E> mapToReadable;
    private Map<E, T> mapToSymbol;

    public StaticAttributeMap() {
        this.mapToReadable = new HashMap<>();
        this.mapToSymbol = new HashMap<>();
    }

    public StaticAttributeMap<T, E> map(T symbol, E readable) {
        mapToReadable.put(symbol, readable);
        mapToSymbol.put(readable, symbol);
        return this;
    }

    public E readable(T symbol) {
        return mapToReadable.get(symbol);
    }

    public T symbol(E readable) {
        return mapToSymbol.get(readable);
    }

    public Map<T, E> getMapToReadable() {
        return Collections.unmodifiableMap(this.mapToReadable);
    }

    public Map<E, T> getMapToSymbol() {
        return Collections.unmodifiableMap(this.mapToSymbol);
    }
}
