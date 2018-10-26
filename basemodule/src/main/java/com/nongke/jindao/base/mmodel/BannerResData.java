package com.nongke.jindao.base.mmodel;

import java.util.List;

public class BannerResData extends BaseResData {

    public List<BannerProduct> rspBody;

    public class BannerProduct {
        public String productName, coverImg, img, detailImgs, detail;
        public int pageNum, pageSize, productId, stockAmount, salesAmount, status, isHot;
        public float  productPrice, costPrice;

    }

}
