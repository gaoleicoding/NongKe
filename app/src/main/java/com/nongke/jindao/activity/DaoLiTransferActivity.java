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
public class DaoLiTransferActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_transfer_amount)
    EditText et_transfer_amount;
    @BindView(R.id.et_transfer_account)
    EditText et_transfer_account;
    @BindView(R.id.tv_transfer)
    TextView tv_transfer;
    @BindView(R.id.tv_transfer_record)
    TextView tv_transfer_record;



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
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_transfer, R.id.tv_transfer_record })
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                    finish();

                break;

            case R.id.tv_transfer:

                break;
            case R.id.tv_transfer_record:

                break;

            default:
                break;
        }

    }


}
