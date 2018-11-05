package com.nongke.jindao.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.base.utils.OnlineParamUtil;
import com.nongke.jindao.base.utils.UserUtil;
import com.nongke.jindao.base.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class PayView extends FrameLayout implements View.OnClickListener {

    LinearLayout ll_pay_alipay;
    LinearLayout ll_pay_wechat;
    ImageView img_pay_alipay;
    ImageView img_pay_wechat;
    public int payType = 3;

    public PayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_pay_style, this);
        ll_pay_alipay = findViewById(R.id.ll_pay_alipay);
        ll_pay_wechat = findViewById(R.id.ll_pay_wechat);
        img_pay_alipay = findViewById(R.id.img_pay_alipay);
        img_pay_wechat = findViewById(R.id.img_pay_wechat);
        ll_pay_alipay.setOnClickListener(this);
        ll_pay_wechat.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_alipay:
                img_pay_alipay.setImageResource(R.drawable.icon_pay_select);
                img_pay_wechat.setImageResource(R.drawable.icon_pay_unselect);
                payType = 3;
                break;
            case R.id.ll_pay_wechat:
                img_pay_wechat.setImageResource(R.drawable.icon_pay_select);
                img_pay_alipay.setImageResource(R.drawable.icon_pay_unselect);
                payType = 4;
                break;

        }

    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}