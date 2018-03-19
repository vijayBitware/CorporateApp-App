
package com.corporateapp.models.complaints;

import com.corporateapp.webservice.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComplaintResponce extends BaseResponse {

    @SerializedName("complaint_data")
    @Expose
    private List<ComplaintDatum> complaintData = null;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public List<ComplaintDatum> getComplaintData() {
        return complaintData;
    }

    public void setComplaintData(List<ComplaintDatum> complaintData) {
        this.complaintData = complaintData;
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
