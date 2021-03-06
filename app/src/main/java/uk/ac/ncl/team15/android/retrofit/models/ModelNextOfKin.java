package uk.ac.ncl.team15.android.retrofit.models;

/**
 * @Purpose: A JSON API response on a next of kin
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNextOfKin {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("relationship")
    @Expose
    private String relationship;

    @SerializedName("address")
    @Expose
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*
     * Begin custom methods, prior are auto-generated
     */
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
