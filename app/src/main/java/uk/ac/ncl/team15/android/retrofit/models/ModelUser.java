package uk.ac.ncl.team15.android.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUser
{
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("is_staff")
    @Expose
    private Boolean isStaff;

    @SerializedName("date_joined")
    @Expose
    private String dateJoined;

    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("department")
    @Expose
    private String department;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Boolean isStaff()
    {
        return isStaff;
    }

    public void isStaff(Boolean isStaff)
    {
        this.isStaff = isStaff;
    }

    public String getDateJoined()
    {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined)
    {
        this.dateJoined = dateJoined;
    }

    public Boolean isActive()
    {
        return isActive;
    }

    public void isActive(Boolean isActive)
    {
        this.isActive = isActive;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getDob()
    {
        return dob;
    }

    public void setDob(String dob)
    {
        this.dob = dob;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }
}
