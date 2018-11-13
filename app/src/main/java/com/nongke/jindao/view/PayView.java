package com.nongke.jindao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nongke.jindao.R;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.pay.wxpay.WXPayUtil;

public class PayView extends FrameLayout implements View.OnClickListener {

    LinearLayout ll_pay_alipay;
    LinearLayout ll_pay_wechat;
    LinearLayout ll_pay_daoli;
    ImageView img_pay_alipay;
    ImageView img_pay_wechat;
    ImageView img_pay_daoli;
    public int payType = 3;

    public PayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_pay_style, this);
        ll_pay_alipay = findViewById(R.id.ll_pay_alipay);
        ll_pay_wechat = findViewById(R.id.ll_pay_wechat);
        ll_pay_daoli = findViewById(R.id.ll_pay_daoli);
        img_pay_alipay = findViewById(R.id.img_pay_alipay);
        img_pay_wechat = findViewById(R.id.img_pay_wechat);
        img_pay_daoli = findViewById(R.id.img_pay_daoli);
        ll_pay_alipay.setOnClickListener(this);
        ll_pay_wechat.setOnClickListener(this);
        ll_pay_daoli.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_alipay:
                img_pay_alipay.setImageResource(R.drawable.icon_pay_select);
                img_pay_wechat.setImageResource(R.drawable.icon_pay_unselect);
                img_pay_daoli.setImageResource(R.drawable.icon_pay_unselect);
                payType = 3;
                if (onPayTypeClickListener != null)
                    onPayTypeClickListener.onPayTypeClick(payType);
                break;
            case R.id.ll_pay_wechat:
                img_pay_wechat.setImageResource(R.drawable.icon_pay_select);
                img_pay_alipay.setImageResource(R.drawable.icon_pay_unselect);
                img_pay_daoli.setImageResource(R.drawable.icon_pay_unselect);
                payType = 4;
                if (onPayTypeClickListener != null)
                    onPayTypeClickListener.onPayTypeClick(payType);
                break;
            case R.id.ll_pay_daoli:
                img_pay_daoli.setImageResource(R.drawable.icon_pay_select);
                img_pay_wechat.setImageResource(R.drawable.icon_pay_select);
                img_pay_alipay.setImageResource(R.drawable.icon_pay_unselect);
                payType = 1;
                if (onPayTypeClickListener != null)
                    onPayTypeClickListener.onPayTypeClick(payType);
                break;

        }

    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }


    public interface OnPayTypeClickListener {
        void onPayTypeClick(int type);
    }

    OnPayTypeClickListener onPayTypeClickListener;

    public void setOnPayTypeClickListener(OnPayTypeClickListener onPayTypeClickListener) {
        this.onPayTypeClickListener = onPayTypeClickListener;
    }
}