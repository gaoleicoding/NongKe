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
import android.widget.Toast;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.mpresenter.RegisterLoginPresenter;
import com.nongke.jindao.view.CountDownButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class RegisterLoginActivity extends BaseMvpActivity<RegisterLoginPresenter> implements RegisterLoginContract.View {
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

    @BindView(R.id.et_register_phone_num)
    EditText et_register_phone_num;
    @BindView(R.id.et_register_password)
    EditText et_register_password;
    @BindView(R.id.et_register_confirm_password)
    EditText et_register_confirm_password;
    @BindView(R.id.et_register_verify_code)
    EditText et_register_verify_code;
    @BindView(R.id.et_forget_pwd_verify_code)
    EditText et_forget_pwd_verify_code;
    @BindView(R.id.et_forget_pwd_phone_num)
    EditText et_forget_pwd_phone_num;
    @BindView(R.id.et_forget_pwd_password)
    EditText et_forget_pwd_password;
    @BindView(R.id.et_forget_pwd_confirm_password)
    EditText et_forget_pwd_confirm_password;

    @BindView(R.id.et_login_phone_num)
    EditText et_login_phone_num;
    @BindView(R.id.et_login_password)
    EditText et_login_password;

    @BindView(R.id.tv_register_get_verify_code)
    CountDownButton tv_register_get_verify_code;
    @BindView(R.id.tv_forget_pwd_get_verify_code)
    CountDownButton tv_forget_pwd_get_verify_code;

    boolean isInRegister = false, isInForgetPwd = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterLoginActivity.class);
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
    public RegisterLoginPresenter initPresenter() {
        return new RegisterLoginPresenter();
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_register_free, R.id.tv_register_get_verify_code, R.id.tv_register, R.id.tv_forget_password, R.id.layout_forget_password})
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
                String phoneNum = et_login_phone_num.getText().toString();
                String password = et_login_password.getText().toString();
                mPresenter.getLoginData(phoneNum, password);
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

            case R.id.tv_register_get_verify_code:

                phoneNum = et_register_phone_num.getText().toString();
                if (Utils.isMobileNO(phoneNum)) {
                    if (tv_register_get_verify_code.isFinish()) {
                        //发送验证码请求成功后调用
                        tv_register_get_verify_code.start();
                    }
                    mPresenter.getMessageCode(phoneNum, 1);
                }

                break;

            case R.id.tv_forget_pwd_get_verify_code:
                phoneNum = et_forget_pwd_phone_num.getText().toString();
                if (Utils.isMobileNO(phoneNum)) {
                    if (tv_forget_pwd_get_verify_code.isFinish()) {
                        //发送验证码请求成功后调用
                        tv_forget_pwd_get_verify_code.start();
                    }
                }

                break;
            case R.id.tv_register:
                phoneNum = et_register_phone_num.getText().toString();
                password = et_register_password.getText().toString();
                if (password.length() < 6)
                    Utils.showToast(getString(R.string.register_password_too_short), true);
                String confirmPassword = et_register_confirm_password.getText().toString();
                String code = et_register_verify_code.getText().toString();
                mPresenter.getRegisterData(phoneNum, password, confirmPassword, code);
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

    @Override
    public void showRegisterResData(RegisterResData registerResData) {
        LogUtil.d("registerResData.toString():" + registerResData.toString());
        if ("10000".equals(registerResData.retCode)) {
            Utils.showToast(getString(R.string.register_success), true);
        } else {
            Utils.showToast(getString(R.string.register_failure), true);
        }
    }

    @Override
    public void showLoginResData(LoginResData loginResData) {
        LogUtil.d("loginResData.toString():" + loginResData.toString());
        if ("10000".equals(loginResData.retCode)) {
            Utils.showToast(getString(R.string.login_success), true);
        } else {
            Utils.showToast(getString(R.string.login_failure), true);
        }
    }

    @Override
    public void showMsgCodeResData(MsgCodeResData msgCodeResData) {
        LogUtil.d("msgCodeResData.toString():" + msgCodeResData.toString());
        if ("10000".equals(msgCodeResData.retCode)) {
//            Utils.showToast(getString(R.string.get_msgcode_success), true);
        } else if (msgCodeResData.retDesc.contains("isv.BUSINESS_LIMIT_CONTROL") && "20000".equals(msgCodeResData.retCode))
            Utils.showToast(getString(R.string.get_msgcode_frequent), true);
        else Utils.showToast(getString(R.string.get_msgcode_failure), true);
    }
}
