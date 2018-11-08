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
import com.nongke.jindao.adapter.UserRecordAdapter;
import com.nongke.jindao.adapter.divider.RecycleViewDivider;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.UserRecordResData;
import com.nongke.jindao.base.mmodel.UserRecordResData.UserRecordBody;
import com.nongke.jindao.mcontract.UserRecordContract;
import com.nongke.jindao.mpresenter.UserRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class DaoLiTransferRecordActivity extends BaseMvpActivity<UserRecordPresenter> implements UserRecordContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_commission_hint)
    TextView tv_commission_hint;
    @BindView(R.id.recyclerview_commission)
    RecyclerView recyclerview_commission;

    private UserRecordAdapter userRecordAdapter;
    private List<UserRecordBody> userRecordList;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DaoLiTransferRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public UserRecordPresenter initPresenter() {
        return new UserRecordPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.listUserRecord("2");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commission;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.transfer_record));
        iv_back.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        userRecordList = new ArrayList<>();
        userRecordAdapter = new UserRecordAdapter(this, userRecordList);
        recyclerview_commission.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        recyclerview_commission.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        recyclerview_commission.setNestedScrollingEnabled(false);
        recyclerview_commission.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerview_commission.setFocusable(false);
        recyclerview_commission.setAdapter(userRecordAdapter);
    }

    @Override
    public void showUserRecord(UserRecordResData userRecordResData) {
        if (userRecordResData.rspBody.size() == 0) {
            tv_commission_hint.setText("你还没有转账记录");
            tv_commission_hint.setVisibility(View.VISIBLE);
        } else {
            userRecordAdapter.setList(userRecordResData.rspBody);
            tv_commission_hint.setVisibility(View.GONE);
        }
    }
}
