package uk.ac.ncl.team15.android.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// Auto-generated by: http://www.jsonschema2pojo.org/
public class ModelUser
{
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("next_of_kins")
    @Expose
    private List<NextOfKin> nextOfKins = null;

    @SerializedName("personal_email")
    @Expose
    private String personalEmail;

    @SerializedName("r2w")
    @Expose
    private Object r2w;

    @SerializedName("hmrc")
    @Expose
    private Object hmrc;

    @SerializedName("poa")
    @Expose
    private Object poa;

    @SerializedName("dbs")
    @Expose
    private Object dbs;

    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("visa_status")
    @Expose
    private String visaStatus;

    @SerializedName("medical_conditions")
    @Expose
    private String medicalConditions;

    @SerializedName("languages_spoken")
    @Expose
    private String languagesSpoken;

    @SerializedName("gender")
    @Expose
    private String gender;

    public ModelUser() {
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<NextOfKin> getNextOfKins() {
        return nextOfKins;
    }

    public void setNextOfKins(List<NextOfKin> nextOfKins) {
        this.nextOfKins = nextOfKins;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public Object getR2w() {
        return r2w;
    }

    public void setR2w(Object r2w) {
        this.r2w = r2w;
    }

    public Object getHmrc() {
        return hmrc;
    }

    public void setHmrc(Object hmrc) {
        this.hmrc = hmrc;
    }

    public Object getPoa() {
        return poa;
    }

    public void setPoa(Object poa) {
        this.poa = poa;
    }

    public Object getDbs() {
        return dbs;
    }

    public void setDbs(Object dbs) {
        this.dbs = dbs;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(String visaStatus) {
        this.visaStatus = visaStatus;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public String getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Begin custom methods, prior are auto-generated
     */
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
