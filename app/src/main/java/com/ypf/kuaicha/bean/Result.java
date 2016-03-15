package com.ypf.kuaicha.bean;

import com.google.gson.annotations.SerializedName;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
@Table(name = "result")
public class Result implements Serializable {
    @Column(name = "company")
    @SerializedName("company")
    private String company;
    @Column(name = "com")
    @SerializedName("com")
    private String com;
    @Column(name = "no")
    @SerializedName("no")
    private String no;
    @Column(name = "status")
    @SerializedName("status")
    private int status;
    @Column(name = "list")
    @SerializedName("list")
    private ArrayList<DetialInfo> list;

    @Column(name = "id" ,isId = true)
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCom() {
        return this.com;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return this.no;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setList(ArrayList<DetialInfo> list) {
        this.list = list;
    }

    public ArrayList<DetialInfo> getList() {
        return this.list;
    }

}
