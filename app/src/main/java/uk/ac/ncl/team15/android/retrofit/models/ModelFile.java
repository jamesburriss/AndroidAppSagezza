package uk.ac.ncl.team15.android.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFile {
    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("last_modified")
    @Expose
    private Double lastModified;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getLastModified() {
        return lastModified;
    }

    public void setLastModified(Double lastModified) {
        this.lastModified = lastModified;
    }
}
