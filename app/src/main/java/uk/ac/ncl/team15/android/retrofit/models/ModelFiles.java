package uk.ac.ncl.team15.android.retrofit.models;

/**
 * @Purpose: A JSON API response on files
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFiles {
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("files")
    @Expose
    private List<ModelFile> files = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ModelFile> getFiles() {
        return files;
    }

    public void setFiles(List<ModelFile> files) {
        this.files = files;
    }
}
