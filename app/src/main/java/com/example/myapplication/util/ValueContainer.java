package com.example.myapplication.util;

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