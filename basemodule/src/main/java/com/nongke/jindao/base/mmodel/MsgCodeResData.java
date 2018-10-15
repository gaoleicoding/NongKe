package com.nongke.jindao.base.mmodel;

public class MsgCodeResData {

/*    {
        "extra": {},
        "retCode": "string",
            "retDesc": "string",
            "rspBody": true,
            "timestamp": "string"
    }*/

    public String retCode, retDesc, timestamp;
    public boolean rspBody;

    @Override
    public String toString() {
        return "MsgCodeResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", rspBody=" + rspBody +
                '}';
    }
}
