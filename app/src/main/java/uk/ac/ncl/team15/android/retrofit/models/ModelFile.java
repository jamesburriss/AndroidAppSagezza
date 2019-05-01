package uk.ac.ncl.team15.android.retrofit.models;

/**
 * @Purpose: A JSON API response on a specific file
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFile {
    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("size")
    @Expose
    private Long size;

    @SerializedName("last_modified")
    @Expose
    private String lastModified;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getLastModified() {
        return lastModified;
    }
}
