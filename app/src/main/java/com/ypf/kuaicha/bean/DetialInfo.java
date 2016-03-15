package com.ypf.kuaicha.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/1 0001.
 */

public class DetialInfo implements Serializable {

    @SerializedName("datetime")
    private String datetime;

    @SerializedName("remark")
    private String remark;

    @SerializedName("zone")
    private String zone;

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZone() {
        return this.zone;
    }
}
