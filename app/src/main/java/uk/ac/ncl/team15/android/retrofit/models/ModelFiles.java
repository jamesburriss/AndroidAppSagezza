package uk.ac.ncl.team15.android.retrofit.models;

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
