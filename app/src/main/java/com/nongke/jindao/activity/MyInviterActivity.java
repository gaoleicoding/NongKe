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
import com.nongke.jindao.adapter.InviterAdapter;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mmodel.MyInviterResData.InviterBody;
import com.nongke.jindao.mcontract.MyInviterContract;
import com.nongke.jindao.mpresenter.MyInviterPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class MyInviterActivity extends BaseMvpActivity<MyInviterPresenter> implements MyInviterContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_inviter_amount)
    TextView tv_inviter_amount;
    @BindView(R.id.recyclerview_inviter)

    RecyclerView recyclerview_inviter;
    private List<InviterBody> inviterList;
    InviterAdapter inviterAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyInviterActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_inviter;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_contact));
        iv_back.setVisibility(View.VISIBLE);
        initRecyclerView();
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
    public void showUListUserInviter(MyInviterResData inviterResData) {

        if (inviterResData == null || inviterResData.rspBody == null) return;
        inviterAdapter.setList(inviterResData.rspBody);
        tv_inviter_amount.setText("粉丝："+inviterResData.rspBody.size()+"人");
    }

    private void initRecyclerView() {
        inviterList = new ArrayList<>();
        inviterAdapter = new InviterAdapter(this, inviterList);
        recyclerview_inviter.addItemDecoration(new SpacesItemDecoration(1));

        recyclerview_inviter.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        recyclerview_inviter.setNestedScrollingEnabled(false);
        recyclerview_inviter.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerview_inviter.setFocusable(false);
        recyclerview_inviter.setAdapter(inviterAdapter);
    }
}
