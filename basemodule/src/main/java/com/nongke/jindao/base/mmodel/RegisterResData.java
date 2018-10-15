package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class RegisterResData {
    /*   {
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
        return "RegisterResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", rspBody=" + rspBody +
                '}';
    }
}
