package com.nongke.jindao.base.mmodel;

public class MsgCodeRequestData {

/*    {
  "phone": "15652009705",
  "type": 1
}*/

    public String phone;
    public int type;

    public MsgCodeRequestData(String phone, int type) {
        this.phone = phone;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgCodeRequestData{" +
                "phone='" + phone + '\'' +
                ", type=" + type +
                '}';
    }
}
