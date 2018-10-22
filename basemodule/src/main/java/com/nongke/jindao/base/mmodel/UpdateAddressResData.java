package com.nongke.jindao.base.mmodel;

public class UpdateAddressResData extends BaseResData{

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
