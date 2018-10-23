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
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mmodel.UserAddressResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.mcontract.UserAddressContract;
import com.nongke.jindao.mpresenter.RegisterLoginPresenter;
import com.nongke.jindao.mpresenter.UserAddressPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class UserAddressActivity extends BaseMvpActivity<UserAddressPresenter> implements UserAddressContract.View {

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
        Intent intent = new Intent(context, UserAddressActivity.class);
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
    public UserAddressPresenter initPresenter() {
        return new UserAddressPresenter();
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
    public void showUserAddressResData(UserAddressResData userAddressResData) {
        if (userAddressResData == null || userAddressResData.rspBody == null) return;
        et_user_name.setText(userAddressResData.rspBody.userName);
        et_user_phone_num.setText(userAddressResData.rspBody.phone);
        et_user_address.setText(userAddressResData.rspBody.address);
    }
}
