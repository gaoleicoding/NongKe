package com.nongke.jindao.base.mmodel;

public class UpdateAddressResData {
    public String retCode, retDesc, timestamp;
    public boolean rspBody;

    @Override
    public String toString() {
        return "UpdateAddressResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", rspBody=" + rspBody +
                '}';
    }
}
