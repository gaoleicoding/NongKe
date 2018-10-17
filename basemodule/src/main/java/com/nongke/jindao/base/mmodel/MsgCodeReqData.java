package com.nongke.jindao.base.mmodel;

public class MsgCodeReqData {

/*    {
  "phone": "15652009705",
  "type": 1
}*/

    public String phone;
    public int type;

    public MsgCodeReqData(String phone, int type) {
        this.phone = phone;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgCodeReqData{" +
                "phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }
}
