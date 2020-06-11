package com.example.kisaanonline.ApiResults;

import com.google.gson.annotations.SerializedName;

public class CheckoutResult {
    @SerializedName("fail")
    private String fail;
    @SerializedName("iserror")
    private String iserror;
    @SerializedName("errormsg")
    private String errormsg;

    public CheckoutResult(String fail, String iserror, String errormsg) {
        this.fail = fail;
        this.iserror = iserror;
        this.errormsg = errormsg;
    }

    public String getFail() {
        return fail;
    }

    public String getIserror() {
        return iserror;
    }

    public String getErrormsg() {
        return errormsg;
    }
}
