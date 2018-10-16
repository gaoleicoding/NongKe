package com.nongke.jindao.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.utils.Constants;
import com.nongke.jindao.view.CountDownButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class UserProfileActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_select_bank)
    TextView tv_select_bank;
    @BindView(R.id.et_bank_card)
    EditText et_bank_card;
    @BindView(R.id.et_bank_card_owner)
    EditText et_bank_card_owner;
    @BindView(R.id.et_bank_branch_address)
    EditText et_bank_branch_address;
    @BindView(R.id.et_contact_phone_num)
    EditText et_contact_phone_num;
    @BindView(R.id.et_modify_profile_verify_code)
    EditText et_modify_profile_verify_code;
    @BindView(R.id.tv_modify_profile_get_verify_code)
    CountDownButton tv_modify_profile_get_verify_code;

    @BindView(R.id.ll_select_bank)
    LinearLayout ll_select_bank;

    @BindView(R.id.tv_save_modify)
    TextView tv_save_modify;

    boolean isInCommission = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_profile));
        iv_back.setVisibility(View.VISIBLE);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.ll_select_bank, R.id.tv_save_modify, R.id.tv_modify_profile_get_verify_code})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                finish();

                break;
            case R.id.ll_select_bank:
                Integer storedWhich = (Integer) SharedPreferencesUtils.getParam(UserProfileActivity.this, "which_bank", new Integer(-1));
                Dialog myDialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.please_select_bank))
                        .setSingleChoiceItems(Constants.banks, storedWhich.intValue(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(UserProfileActivity.this, banks[which], Toast.LENGTH_SHORT).show();
                                tv_select_bank.setText(Constants.banks[which]);
                                SharedPreferencesUtils.setParam(UserProfileActivity.this, "which_bank", which);

                                dialog.cancel();
                            }
                        })
                        .create();
                myDialog.show();
                break;
            case R.id.tv_modify_profile_get_verify_code:
                String phoneNum;
                phoneNum = et_contact_phone_num.getText().toString();
                if (Utils.isMobileNO(phoneNum)) {
                    if (tv_modify_profile_get_verify_code.isFinish()) {
                        //发送验证码请求成功后调用
                        tv_modify_profile_get_verify_code.start();
                    }
//                    mPresenter.getMessageCode(phoneNum, 1);
                }

                break;
            case R.id.tv_save_modify:

                break;

            default:
                break;
        }

    }

}
