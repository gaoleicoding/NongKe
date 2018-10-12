package com.nongke.jindao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nongke.jindao.MainActivity;
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
public class LoginRegisterActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register_free)
    TextView tv_register_free;
    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.layout_login)
    LinearLayout layout_login;
    @BindView(R.id.layout_register)
    LinearLayout layout_register;
    @BindView(R.id.layout_forget_password)
    LinearLayout layout_forget_password;

    boolean isInRegister = false, isInForgetPwd = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginRegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.login));
        iv_back.setVisibility(View.VISIBLE);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_register_free, R.id.tv_forget_password, R.id.tv_register, R.id.layout_forget_password})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!isInRegister && !isInForgetPwd) {
                    finish();
                }
                if (isInRegister) {
                    layout_register.setVisibility(View.GONE);
                    title.setText(getString(R.string.login));
                    isInRegister = false;
                }
                if (isInForgetPwd) {
                    layout_forget_password.setVisibility(View.GONE);
                    title.setText(getString(R.string.login));
                    isInForgetPwd = false;
                }

                break;
            case R.id.tv_login:

                break;
            case R.id.tv_register_free:
                layout_register.setVisibility(View.VISIBLE);
                title.setText(getString(R.string.register));
                isInRegister = true;
                break;
            case R.id.tv_forget_password:
                layout_forget_password.setVisibility(View.VISIBLE);
                title.setText(getString(R.string.forget_password));
                isInForgetPwd = true;
                break;

            case R.id.tv_register:

                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isInRegister && !isInForgetPwd) {
                finish();
            }
            if (isInRegister) {
                layout_register.setVisibility(View.GONE);
                isInRegister = false;
            }
            if (isInForgetPwd) {
                layout_forget_password.setVisibility(View.GONE);
                isInForgetPwd = false;
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
