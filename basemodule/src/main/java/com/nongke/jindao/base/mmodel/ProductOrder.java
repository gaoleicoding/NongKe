package com.nongke.jindao.base.mmodel;

import java.io.Serializable;
import java.util.List;

public class ProductOrder {
    public String orderId, address, payTime, createTime, description, phone, uid, userName, statusDesc;
    public int payType;
    public float cornMoney, discountMoney, discount, rmb, postCode, postage, totalMoney, totalPay;
    public List<Product> products;
    public boolean isConfirmReceive;

}