package com.example.myapplication.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelUsers
{
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("next")
    @Expose
    private String next;

    @SerializedName("previous")
    @Expose
    private Object previous;

    @SerializedName("users")
    @Expose
    private List<ModelUser> users = null;

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public String getNext()
    {
        return next;
    }

    public void setNext(String next)
    {
        this.next = next;
    }

    public Object getPrevious()
    {
        return previous;
    }

    public void setPrevious(Object previous)
    {
        this.previous = previous;
    }

    public List<ModelUser> getUsers()
    {
        return users;
    }

    public void setUsers(List<ModelUser> users)
    {
        this.users = users;
    }
}
