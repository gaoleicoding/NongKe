package com.nongke.jindao.base.mmodel;

import java.util.List;

public class OnlineParamResData extends BaseResData {

    public RspBody rspBody;

    public class RspBody {
        public ParamContent is_maintaining_boolean;//后台是否在维护，维护时App不能用会有提醒
        public ParamContent is_maintaining_content;//维护时，App提醒的内容
        public ParamContent card_discount;
        public ParamContent Papay_vip_bill_extract_3;
        public ParamContent pay_vip_bill_extract_2;
        public ParamContent pay_vip_bill_extract_1;

        public ParamContent vip_phone_discount;//vip会员充话费折扣
        public ParamContent pay_phone_bill_extract_3;
        public ParamContent pay_phone_bill_extract_2;
        public ParamContent pay_phone_bill_extract_1;
        public ParamContent register_redirect_url;//App下载网页
        public ParamContent vip_phone_recharge_ammount_50;
        public ParamContent vip_phone_recharge_ammount_100;
        public ParamContent daoli_recharge_limit_100;

        public ParamContent support_phone_recharge;//是否支持话费充值，如果后期有特殊情况不支持，vip也将不能使用话费充值业务
        public ParamContent pay_product_bill_extract;
        public ParamContent vip_price;//升级为vip的价格
        public ParamContent vip_original_price;//升级为vip原价
        public ParamContent vip_to_user_money;//升级为vip转到余额的金额
//        public ParamContent vip_recharge_gift_boolean;//vip充值是否送礼物，如果送礼物需要表现出来
//        public ParamContent vip_recharge_gift;//vip充值送的什么礼物

        public ParamContent vip_phone_recharge;
        public ParamContent vip_discount;
        public ParamContent vip_contract;//升级为vip的协议
        public ParamContent vip_right_desc;//vip权益介绍
        public ParamContent daoli_use_desc;//稻粒使用说明
        public ParamContent phone_recharge_desc;//话费充值说明
        public ParamContent postage;//邮费，如果邮费不为0，那么购买商品时要加上体现出来
        public ParamContent custom_service_qq;//客服qq，在我的中在线客服用到
        public ParamContent customer_service_phone;
        public ParamContent custom_service_subscription_num;
        public ParamContent custom_service_email_receiver;//客服邮箱，在我的反馈与意见中用到
        public ParamContent company_website;//公司网址，首页的公司介绍跳转到此网页

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
