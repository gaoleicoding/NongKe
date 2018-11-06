package com.nongke.jindao.base.mmodel;

import java.io.Serializable;
import java.util.List;

public class ProductOrder {
    public String orderId, address, payTime, createTime, description, phone, uid, userName;
    public int payType, statusDesc;
    public float cornMoney, discountMoney, discount, rmb, postCode, postage, totalMoney, totalPay;
    public List<Product> products;

}