package com.nongke.jindao.base.mmodel;

import java.util.List;

public class MessageResData extends BaseResData {

    public List<MessageBody> rspBody;

    public class MessageBody {
        public String title, content, createTime;
        public int id;
    }

}
