package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.MyBillAdapter;
import com.nongke.jindao.adapter.divider.RecycleViewDivider;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.BillResData.BillBody;
import com.nongke.jindao.base.utils.account.MessageUtil;
import com.nongke.jindao.mcontract.MyBillContract;
import com.nongke.jindao.mcontract.MyInviterContract;
import com.nongke.jindao.mpresenter.MyBillPresenter;
import com.nongke.jindao.mpresenter.MyInviterPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class MyBillActivity extends BaseMvpActivity<MyBillPresenter> implements MyBillContract.View {
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
        Intent intent = new Intent(context, MyBillActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
    }

    @Override
    public MyBillPresenter initPresenter() {
        return new MyBillPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.listUserBill();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_bill));
        iv_back.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        billList = new ArrayList<>();
        billAdapter = new MyBillAdapter(this, billList);
        message_recyclerview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        message_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        message_recyclerview.setNestedScrollingEnabled(false);
        message_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        message_recyclerview.setFocusable(false);
        message_recyclerview.setAdapter(billAdapter);
    }

    @Override
    public void showUserBill(BillResData billResData) {
        if (billResData.rspBody.size() == 0) {
            tv_bill_hint.setText("你还没有充值记录");
            tv_bill_hint.setVisibility(View.VISIBLE);
        }
        else {
            billAdapter.setList(billResData.rspBody);
            tv_bill_hint.setVisibility(View.GONE);
        }
    }
}
