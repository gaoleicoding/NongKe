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
import com.nongke.jindao.base.event.LoginAccountEvent;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.MyProfileResData;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.mcontract.WithdrawContract;
import com.nongke.jindao.mpresenter.WithdrawPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class WithdrawActivity extends BaseMvpActivity<WithdrawPresenter> implements WithdrawContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_withdraw_amount)
    EditText et_withdraw_amount;
    @BindView(R.id.et_withdraw_contact_phone_num)
    EditText et_contact_phone_num;
    @BindView(R.id.et_withdraw_bank_card)
    EditText et_bank_card_num;
    @BindView(R.id.et_withdraw_bank_card_owner)
    EditText et_bank_card_owner;
    @BindView(R.id.et_withdraw_bank_branch_address)
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
    @BindView(R.id.tv_withdraw_select_bank)
    TextView tv_select_bank;
    @BindView(R.id.tv_commission_amount)
    TextView tv_commission_amount;
    @BindView(R.id.tv_withdrawable_amount)
    TextView tv_withdrawable_amount;
    @BindView(R.id.tv_commission)
    TextView tv_commission;
    @BindView(R.id.tv_confirm_commission_convert)
    TextView tv_confirm_commission_convert;
    String bankName, bankNum, bankAdress, userName, phone;
    boolean isInCommission = false;
    String withdraw_limit_100;

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
        tv_commission_amount.setText(UserUtil.getUserInfo().rspBody.commission + "");
        tv_commission.setText(UserUtil.getUserInfo().rspBody.commission + "");
        tv_withdrawable_amount.setText(UserUtil.getUserInfo().rspBody.money + "");
        EventBus.getDefault().register(this);

        withdraw_limit_100 = OnlineParamUtil.getParamResData().rspBody.withdraw_limit_100.content;
        if ("true".equals(withdraw_limit_100)) {
            et_withdraw_amount.setHint(getResources().getString(R.string.input_with_amount_hint));
        }
    }

    @Override
    public WithdrawPresenter initPresenter() {
        return new WithdrawPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getUserProfile();
    }

    @OnClick({R.id.iv_back, R.id.tv_commission_convert_to_balance, R.id.ll_select_bank, R.id.tv_withdraw_immediate, R.id.tv_confirm_commission_convert})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (isInCommission) {
                    backFromCommissionToBalance();
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
                Integer storedWhich = (Integer) SharedPreferencesUtils.getParam(WithdrawActivity.this, "which_bank", new Integer(-1));
                Dialog myDialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.please_select_bank))
                        .setSingleChoiceItems(Constants.banks, storedWhich.intValue(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(UserProfileActivity.this, banks[which], Toast.LENGTH_SHORT).show();
                                tv_select_bank.setText(Constants.banks[which]);
                                SharedPreferencesUtils.setParam(WithdrawActivity.this, "which_bank", which);

                                dialog.cancel();
                            }
                        })
                        .create();
                myDialog.show();
                break;
            case R.id.tv_confirm_commission_convert:
                double commissionAmount = Utils.stringToDouble(et_commission_convert_amount.getText().toString());
                if (commissionAmount > UserUtil.userInfo.rspBody.commission) {
                    Utils.showToast("输入金额超过你的佣金，请重新输入", false);
                    return;
                }
                mPresenter.commissionToMoney(commissionAmount);
                break;
            case R.id.tv_withdraw_immediate:
                String withdrawStr=et_withdraw_amount.getText().toString().trim();
                if(withdrawStr.equals("")){
                    Utils.showToast("请输入提现金额", true);
                    return;
                }
                float withdrawAmount = Utils.stringToInt(withdrawStr);
                if (withdrawAmount > UserUtil.userInfo.rspBody.money) {
                    Utils.showToast("输入金额超过你的余额，请重新输入", false);
                    return;
                }
                if (bankName == null) {
                    Utils.showToast("请选择 银行名称", true);
                    return;
                }
                if (bankNum == null) {
                    Utils.showToast("请选输入 银行卡号", true);
                    return;
                }
                if (bankAdress == null) {
                    Utils.showToast("请输入 银行支行", true);
                    return;
                }
                if (userName == null) {
                    Utils.showToast("请输入 户主姓名", true);
                    return;
                }
                if (phone == null) {
                    Utils.showToast("请输入联系电话", true);
                    return;
                }

                if ("true".equals(withdraw_limit_100)) {
                    if (withdrawAmount % 100 != 0) {
                        Utils.showToast("请输入整百金额", true);
                        return;
                    }
                }
                if (Utils.isMobileNO(et_contact_phone_num.getText().toString()))
                    mPresenter.saveUserCash(withdrawAmount, bankName, bankNum, bankAdress, userName, phone);
                break;

            default:
                break;
        }

    }

    private void backFromCommissionToBalance() {
        ll_commission_convert_amount.setVisibility(View.GONE);
        ll_withdraw_amount.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.my_withdraw));
        isInCommission = false;
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

    @Override
    public void showUserProfileResData(MyProfileResData userProfileResData) {
        if (userProfileResData == null || userProfileResData.rspBody == null) return;

        bankName = userProfileResData.rspBody.bankName;
        bankNum = userProfileResData.rspBody.bankNum;
        bankAdress = userProfileResData.rspBody.bankAdress;
        userName = userProfileResData.rspBody.userName;
        phone = userProfileResData.rspBody.phone;
        tv_select_bank.setText(bankName);
        et_bank_card_num.setText(bankNum);
        et_bank_branch_address.setText(bankAdress);
        et_bank_card_owner.setText(userName);
        et_contact_phone_num.setText(phone);
    }

    @Override
    public void showCommissionToMoney(BaseResData baseResData) {
        if ("10000".equals(baseResData.retCode)) {
            backFromCommissionToBalance();
            mPresenter.getUserProfile();
        }
    }

    @Override
    public void showSaveUserCash(BaseResData baseResData) {
        if ("10000".equals(baseResData.retCode)) {
        finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginAccountEvent accountEvent) {
        tv_commission_amount.setText(UserUtil.getUserInfo().rspBody.commission + "");
        tv_commission.setText(UserUtil.getUserInfo().rspBody.commission + "");
        tv_withdrawable_amount.setText(UserUtil.getUserInfo().rspBody.money + "");
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
