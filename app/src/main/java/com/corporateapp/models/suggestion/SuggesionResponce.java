
package com.corporateapp.models.suggestion;

import com.corporateapp.webservice.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggesionResponce extends BaseResponse {

    @SerializedName("suggestion_data")
    @Expose
    private List<SuggestionDatum> suggestionData = null;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    public List<SuggestionDatum> getSuggestionData() {
        return suggestionData;
    }

    public void setSuggestionData(List<SuggestionDatum> suggestionData) {
        this.suggestionData = suggestionData;
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
