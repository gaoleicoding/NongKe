package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.MyBillAdapter;
import com.nongke.jindao.adapter.divider.RecycleViewDivider;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.BillResData.BillBody;
import com.nongke.jindao.mcontract.MyBillContract;
import com.nongke.jindao.mpresenter.MyBillPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class CustomServiceActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview_message)
    RecyclerView message_recyclerview;
    @BindView(R.id.tv_bill_hint)
    TextView tv_bill_hint;
    private List<BillBody> billList;
    private MyBillAdapter billAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    public MyBillPresenter initPresenter() {
        return new MyBillPresenter();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_service;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.custom_service));
        iv_back.setVisibility(View.VISIBLE);
    }


}
