package com.nongke.jindao.base.mmodel;

import java.io.Serializable;
import java.util.List;

public class ProductResData extends BaseResData {

    public RspBody rspBody;


    public class RspBody {
        public int pageNum, pageSize, size, startRow, endRow, total, pages;
        public String uid,totalPrice;
        public boolean hasNextPage;
        public List<Product> list;

    }

}
