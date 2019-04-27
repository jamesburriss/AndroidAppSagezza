package uk.ac.ncl.team15.android.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Auto-generated by: http://www.jsonschema2pojo.org/
public class ModelUser
{
    @SerializedName("visibility")
    @Expose
    private Integer visibility;

    @SerializedName("read_only")
    @Expose
    private Boolean readOnly;

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

    @SerializedName("personal_email")
    @Expose
    private String personalEmail;

    @SerializedName("r2w")
    @Expose
    private String r2w;

    @SerializedName("hmrc")
    @Expose
    private String hmrc;

    @SerializedName("poa")
    @Expose
    private String poa;

    @SerializedName("dbs")
    @Expose
    private String dbs;

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

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
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

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getR2w() {
        return r2w;
    }

    public void setR2w(String r2w) {
        this.r2w = r2w;
    }

    public String getHmrc() {
        return hmrc;
    }

    public void setHmrc(String hmrc) {
        this.hmrc = hmrc;
    }

    public String getPoa() {
        return poa;
    }

    public void setPoa(String poa) {
        this.poa = poa;
    }

    public String getDbs() {
        return dbs;
    }

    public void setDbs(String dbs) {
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
