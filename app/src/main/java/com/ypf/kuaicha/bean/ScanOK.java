package com.ypf.kuaicha.bean;

/**
 * Created by Administrator on 2016/3/2.
 */
public class ScanOK {
    private boolean isSaoMiaoOK;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSaoMiaoOK() {
        return isSaoMiaoOK;
    }

    public void setSaoMiaoOK(boolean saoMiaoOK) {
        isSaoMiaoOK = saoMiaoOK;
    }
}
