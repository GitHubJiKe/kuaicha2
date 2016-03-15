package com.ypf.kuaicha.bean;

import com.google.gson.annotations.SerializedName;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
@Table(name = "resultroot")
public class ResultRoot implements Serializable{
    @Column(name = "resultcode")
    @SerializedName("resultcode")
    private String resultcode;
    @Column(name = "reason")
    @SerializedName("reason")
    private String reason;
    @Column(name = "result")
    @SerializedName("result")
    private Result result;
    @Column(name = "error_code")
    @SerializedName("error_code")
    private int error_code;

    public void setResultcode(String resultcode){
        this.resultcode = resultcode;
    }
    public String getResultcode(){
        return this.resultcode;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return this.reason;
    }
    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public void setError_code(int error_code){
        this.error_code = error_code;
    }
    public int getError_code(){
        return this.error_code;
    }

}
