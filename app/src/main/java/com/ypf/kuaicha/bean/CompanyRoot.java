package com.ypf.kuaicha.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class CompanyRoot {
    @SerializedName("resultcode")
    private String resultcode;
    @SerializedName("reason")
    private String reason;
    @SerializedName("result")
    private List<Company> result;

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultcode() {
        return this.resultcode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public void setResult(List<Company> result) {
        this.result = result;
    }

    public List<Company> getResult() {
        return this.result;
    }
}
