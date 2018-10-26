package com.nongke.jindao.base.mmodel;

import java.util.List;

public class ProductResData extends BaseResData {

    public RspBody rspBody;


    public class RspBody {
        public int pageNum, pageSize, size, startRow, endRow, total, pages;
        public boolean hasNextPage;


        public List<Product> list;


    }
    public class Product {
        public String productName, coverImg, img, detailImgs, detail;
        public int pageNum, pageSize, productId, stockAmount, salesAmount, status, isHot;
        public float  productPrice, costPrice;

    }

}
