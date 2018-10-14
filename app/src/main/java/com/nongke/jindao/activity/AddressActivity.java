package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mpresenter.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class DeliveryAddressActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_transfer_amount)
    EditText et_transfer_amount;
    @BindView(R.id.et_contact_phone_num)
    EditText et_contact_phone_num;
    @BindView(R.id.et_bank_card)
    EditText et_bank_card;
    @BindView(R.id.et_bank_card_owner)
    EditText et_bank_card_owner;
    @BindView(R.id.et_bank_branch_address)
    EditText et_bank_branch_address;
    @BindView(R.id.et_commission_convert_amount)
    EditText et_commission_convert_amount;

    @BindView(R.id.tv_commission_convert_to_balance)
    TextView tv_commission_convert_to_balance;
    @BindView(R.id.tv_withdraw_immediate)
    TextView tv_withdraw_immediate;
    @BindView(R.id.ll_withdraw_amount)
    LinearLayout ll_withdraw_amount;
    @BindView(R.id.ll_commission_convert_amount)
    LinearLayout ll_commission_convert_amount;

    boolean isInCommission = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeliveryAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delivery_address;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_address));
        iv_back.setVisibility(View.VISIBLE);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.tv_commission_convert_to_balance, R.id.tv_withdraw_immediate})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (isInCommission) {
                    ll_commission_convert_amount.setVisibility(View.GONE);
                    ll_withdraw_amount.setVisibility(View.VISIBLE);
                    title.setText(getString(R.string.my_withdraw));
                    isInCommission = false;
                } else
                    finish();

                break;

            case R.id.tv_commission_convert_to_balance:
                ll_commission_convert_amount.setVisibility(View.VISIBLE);
                ll_withdraw_amount.setVisibility(View.GONE);
                title.setText(getString(R.string.commission_convert));
                isInCommission = true;
                break;
            case R.id.tv_withdraw_immediate:

                break;

            default:
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isInCommission) {
                ll_commission_convert_amount.setVisibility(View.GONE);
                ll_withdraw_amount.setVisibility(View.VISIBLE);
                title.setText(getString(R.string.my_withdraw));
                isInCommission = false;
            } else
                finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
