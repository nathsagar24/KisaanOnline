package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;
//Take care if the methods can be called from anywhere
public class RemoveFromCartResult {
    @SerializedName("iserror")
    private String isError;
    @SerializedName("errorString")
    private String errorString;
    @SerializedName("errorTitle")
    private String errorTitle;
    @SerializedName("errorType")
    private String errorType;

    public RemoveFromCartResult(String isError, String errorString, String errorTitle, String errorType) {
        this.isError = isError;
        this.errorString = errorString;
        this.errorTitle = errorTitle;
        this.errorType = errorType;
    }

    public  String getErrorString() {
        return errorString;
    }

    public String getIsError() {
        return isError;
    }

    public String getErrorTitle() { return errorTitle; }

    public String getErrorType() { return errorType; }
}
