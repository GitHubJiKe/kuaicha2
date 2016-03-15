package com.ypf.kuaicha.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class Company implements Serializable {
    @SerializedName("com")
    private String com;
    @SerializedName("no")
    private String no;

    public void setCom(String com){
        this.com = com;
    }
    public String getCom(){
        return this.com;
    }
    public void setNo(String no){
        this.no = no;
    }
    public String getNo(){
        return this.no;
    }
}
