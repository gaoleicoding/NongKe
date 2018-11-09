package com.nongke.jindao.base.mmodel;

import java.util.List;

public class OnlineParamResData extends BaseResData {

    public RspBody rspBody;

    public class RspBody {
        public ParamContent is_maintaining_boolean;
        public ParamContent is_maintaining_content;
        public ParamContent card_discount;
        public ParamContent Papay_vip_bill_extract_3;
        public ParamContent pay_vip_bill_extract_2;
        public ParamContent pay_vip_bill_extract_1;

        public ParamContent vip_phone_discount;
        public ParamContent pay_phone_bill_extract_3;
        public ParamContent pay_phone_bill_extract_2;
        public ParamContent pay_phone_bill_extract_1;
        public ParamContent register_redirect_url;
        public ParamContent company_address;

        public ParamContent support_phone_recharge;
        public ParamContent pay_product_bill_extract;
        public ParamContent vip_price;
        public ParamContent vip_original_price;
        public ParamContent vip_to_user_money;
        public ParamContent vip_recharge_gift;
        public ParamContent vip_recharge_gift_boolean;

        public ParamContent vip_phone_recharge;
        public ParamContent vip_discount;
        public ParamContent vip_contract;
        public ParamContent vip_right_desc;
        public ParamContent daoli_use_desc;
        public ParamContent phone_recharge_desc;
        public ParamContent postage;
        public ParamContent custom_service_qq;
        public ParamContent customer_service_phone;

    }

    public class ParamContent {
        public int id;
        public String title, content;

        @Override
        public String toString() {
            return "ParamContent{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
