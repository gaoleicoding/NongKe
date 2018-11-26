package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.event.LoginEvent;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.mcontract.DaoLiTransferContract;
import com.nongke.jindao.mpresenter.DaoLiTransferPresenter;

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
public class DaoLiTransferActivity extends BaseMvpActivity<DaoLiTransferPresenter> implements DaoLiTransferContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_transfer_amount)
    EditText et_transfer_amount;
    @BindView(R.id.et_transfer_phone)
    EditText et_transfer_phone;
    @BindView(R.id.tv_transfer)
    TextView tv_transfer;
    @BindView(R.id.tv_transfer_record)
    TextView tv_transfer_record;
    @BindView(R.id.tv_daoli)
    TextView tv_daoli;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DaoLiTransferActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daoli_transfer;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_daoli_transfer));
        iv_back.setVisibility(View.VISIBLE);
        tv_daoli.setText(UserUtil.userInfo.rspBody.cardMoney + "");
        EventBus.getDefault().register(this);

    }

    @Override
    public DaoLiTransferPresenter initPresenter() {
        return new DaoLiTransferPresenter();
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_transfer, R.id.tv_transfer_record})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_transfer:
                String amountStr = et_transfer_amount.getText().toString();
                if (amountStr.trim().length() == 0) {
                    Utils.showToast("请输入金额", false);
                    return;
                }

                int amount = Utils.stringToInt(amountStr);
                if (amount > UserUtil.userInfo.rspBody.cardMoney) {
                    Utils.showToast("输入金额超过你的稻粒，请重新输入", false);
                    return;
                }
                String phoneNum = et_transfer_phone.getText().toString();
                if (Utils.isMobileNO(phoneNum)) {

                    mPresenter.cardMoneyToUser(amount, phoneNum);
                }
                break;
            case R.id.tv_transfer_record:
                DaoLiTransferRecordActivity.startActivity(DaoLiTransferActivity.this);
                break;

            default:
                break;
        }

    }

    @Override
    public void showCardMoneyToUser(BaseResData baseResData) {
        if ("10000".equals(baseResData.retCode)) finish();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent accountEvent) {
        tv_daoli.setText(UserUtil.userInfo.rspBody.cardMoney + "");
    }
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
