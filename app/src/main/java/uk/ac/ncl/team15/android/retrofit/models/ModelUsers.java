package uk.ac.ncl.team15.android.retrofit.models;

/**
 * @Purpose: A JSON API response on users
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// Auto-generated by: http://www.jsonschema2pojo.org/
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
    private String previous;

    @SerializedName("users")
    @Expose
    private List<ModelUser> users = null;

    public Integer getCount()
    {
        return count;
    }

    public String getNext()
    {
        return next;
    }

    public String getPrevious()
    {
        return previous;
    }

    public List<ModelUser> getUsers()
    {
        return users;
    }
}
