package uk.ac.ncl.team15.android.retrofit.models;

/**
 * @Purpose: A JSON API response on a specific job
 *
 * @authors  Callum
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelJob {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("hours")
    @Expose
    private String hours;

    @SerializedName("salary")
    @Expose
    private Double salary;

    @SerializedName("skill_level")
    @Expose
    private String skillLevel;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
