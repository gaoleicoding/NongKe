package com.nongke.jindao.base.mmodel;

public class BaseResData {


    public String retCode, retDesc, timestamp;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BaseResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
