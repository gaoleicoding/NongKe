package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.UserUtils;
import com.nongke.jindao.mcontract.MyInviterContract;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.mpresenter.MyInviterPresenter;
import com.nongke.jindao.mpresenter.RegisterLoginPresenter;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class MyInviterActivity extends BaseMvpActivity<MyInviterPresenter> implements MyInviterContract.View {


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyInviterActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inviter;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public MyInviterPresenter initPresenter() {
        return new MyInviterPresenter();
    }

    @Override
    protected void loadData() {

        mPresenter.listUserInviter();
    }


    @Override
    public void showUListUserInviter(MyInviterResData userContactResData) {

    }
}
