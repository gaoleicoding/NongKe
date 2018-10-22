package com.nongke.jindao.base.mmodel;

public class MsgCodeResData extends BaseResData{

/*    {
        "extra": {},
        "retCode": "string",
            "retDesc": "string",
            "rspBody": true,
            "timestamp": "string"
    }*/

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
