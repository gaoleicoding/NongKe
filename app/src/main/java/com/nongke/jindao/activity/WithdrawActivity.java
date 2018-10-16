package com.nongke.jindao.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class WithdrawActivity extends BaseMvpActivity {
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
    @BindView(R.id.ll_select_bank)
    LinearLayout ll_select_bank;
    @BindView(R.id.tv_select_bank)
    TextView tv_select_bank;

    boolean isInCommission = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_withdraw));
        iv_back.setVisibility(View.VISIBLE);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_commission_convert_to_balance, R.id.ll_select_bank,R.id.tv_withdraw_immediate})
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
            case R.id.ll_select_bank:
                Integer storedWhich=(Integer) SharedPreferencesUtils.getParam(WithdrawActivity.this,"which_bank",new Integer(-1));
                Dialog myDialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.please_select_bank))
                        .setSingleChoiceItems(Constants.banks, storedWhich.intValue(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(UserProfileActivity.this, banks[which], Toast.LENGTH_SHORT).show();
                                tv_select_bank.setText(Constants.banks[which]);
                                SharedPreferencesUtils.setParam(WithdrawActivity.this,"which_bank",which);

                                dialog.cancel();
                            }
                        })
                        .create();
                myDialog.show();
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
