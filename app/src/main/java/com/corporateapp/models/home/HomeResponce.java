
package com.corporateapp.models.home;

import com.corporateapp.webservice.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponce extends BaseResponse {

    @SerializedName("Corporator_data")
    @Expose
    private CorporatorData corporatorData;
    @SerializedName("News_data")
    @Expose
    private List<NewsDatum> newsData = null;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("development")
    @Expose
    private List<Development> development = null;
    @SerializedName("department")
    @Expose
    private List<Department> department = null;
    @SerializedName("emergency_contact")
    @Expose
    private EmergencyContact emergencyContact;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public CorporatorData getCorporatorData() {
        return corporatorData;
    }

    public void setCorporatorData(CorporatorData corporatorData) {
        this.corporatorData = corporatorData;
    }

    public List<NewsDatum> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsDatum> newsData) {
        this.newsData = newsData;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<Development> getDevelopment() {
        return development;
    }

    public void setDevelopment(List<Development> development) {
        this.development = development;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
