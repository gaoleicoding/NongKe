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
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.mcontract.MyAddressContract;
import com.nongke.jindao.mpresenter.MyAddressPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class MyAddressActivity extends BaseMvpActivity<MyAddressPresenter> implements MyAddressContract.View {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_user_phone_num)
    EditText et_user_phone_num;
    @BindView(R.id.et_user_address)
    EditText et_user_address;

    @BindView(R.id.tv_save_modify)
    TextView tv_save_modify;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyAddressActivity.class);
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
    public MyAddressPresenter initPresenter() {
        return new MyAddressPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getUserAddress();
    }

    @OnClick({R.id.iv_back, R.id.tv_save_modify})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_save_modify:
                String userName = et_user_name.getText().toString();
                String userPhone = et_user_phone_num.getText().toString();
                String userAddress = et_user_address.getText().toString();

                mPresenter.saveOrUpdateUserAddress(userName, userPhone, userAddress);
                break;

            default:
                break;
        }

    }


    @Override
    public void showUserAddressResData(MyAddressResData userAddressResData) {
        if (userAddressResData == null || userAddressResData.rspBody == null) return;
        et_user_name.setText(userAddressResData.rspBody.userName);
        et_user_phone_num.setText(userAddressResData.rspBody.phone);
        et_user_address.setText(userAddressResData.rspBody.address);
    }
}
