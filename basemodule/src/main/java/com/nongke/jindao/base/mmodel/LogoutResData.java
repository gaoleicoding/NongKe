package com.nongke.jindao.base.mmodel;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class LogoutResData {

    /*{
  "retCode": "20000",
  "retDesc": "用户尚未登录,请登录",
  "rspBody": "token不能为空",
  "timestamp": "2018-10-18 19:28:36.773"
}*/

    public String retCode, retDesc, rspBody;

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

    public String getRspBody() {
        return rspBody;
    }

    public void setRspBody(String rspBody) {
        this.rspBody = rspBody;
    }

    @Override
    public String toString() {
        return "LogoutResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' +
                ", rspBody='" + rspBody + '\'' +
                '}';
    }
}
