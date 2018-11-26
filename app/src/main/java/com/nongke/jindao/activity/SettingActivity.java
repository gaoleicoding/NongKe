package com.nongke.jindao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.event.LogoutEvent;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.update.UpdateApk;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class SettingActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_versionName)
    TextView tv_versionName;
    @BindView(R.id.help_feedback_layout)
    LinearLayout help_feedback_layout;
    @BindView(R.id.version_update_layout)
    LinearLayout version_update_layout;
    @BindView(R.id.my_logout_layout)
    LinearLayout my_logout_layout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_setting));
        iv_back.setVisibility(View.VISIBLE);
        tv_versionName.setText(Utils.getVersionName(this));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.help_feedback_layout, R.id.version_update_layout, R.id.my_logout_layout})
    public void click(View view) {
        if (!UserUtil.isLogined()) {
            RegisterLoginActivity.startActivity(this);
            Utils.showToast(getString(R.string.user_not_login), true);
            return;
        }
        switch (view.getId()) {
            case R.id.help_feedback_layout:
                HelpFeedbackActivity.startActivity(SettingActivity.this);
                break;
            case R.id.version_update_layout:
                checkUpdate();
                break;
            case R.id.my_logout_layout:
                EventBus.getDefault().post(new LogoutEvent());
                finish();
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    private void checkUpdate() {

        if (OnlineParamUtil.getParamResData() != null && OnlineParamUtil.getParamResData().rspBody != null) {
            String android_versionCode = OnlineParamUtil.getParamResData().rspBody.android_versionCode.content;

            String android_update_content = OnlineParamUtil.getParamResData().rspBody.android_update_content.content;
            String android_must_update = OnlineParamUtil.getParamResData().rspBody.android_must_update.content;
            final String android_app_download_url = OnlineParamUtil.getParamResData().rspBody.android_app_download_url.content;
            if (Utils.stringToInt(android_versionCode) <= Utils.getVersionCode(this)) {
                Utils.showToast("当前是最新版本", true);
                return;
            }
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setTitle("升级提醒");
            builder.setIcon(R.drawable.update);
            builder.setMessage(android_update_content);
            builder.setCancelable(false);
            if ("false".equals(android_must_update)) {
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
            }
            builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    UpdateApk.downFile(android_app_download_url, SettingActivity.this);
                }
            });
            builder.show();
        }
    }

}
