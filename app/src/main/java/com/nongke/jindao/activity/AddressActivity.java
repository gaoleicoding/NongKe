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
public class AddressActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_delivery_name)
    EditText et_delivery_name;
    @BindView(R.id.et_delivery_phone_num)
    EditText et_delivery_phone_num;
    @BindView(R.id.et_delivery_address)
    EditText et_delivery_address;


    @BindView(R.id.tv_save_modify)
    TextView tv_save_modify;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
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
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back,  R.id.tv_save_modify})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                    finish();

                break;

            case R.id.tv_save_modify:

                break;

            default:
                break;
        }

    }


}
