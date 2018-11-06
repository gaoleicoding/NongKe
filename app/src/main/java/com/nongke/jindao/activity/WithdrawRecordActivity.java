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
import com.nongke.jindao.adapter.MessageAdapter;
import com.nongke.jindao.adapter.divider.RecycleViewDivider;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.utils.account.MessageUtil;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class WithdrawRecordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview_message)
    RecyclerView message_recyclerview;

    private MessageAdapter messageAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WithdrawRecordActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_withdraw_record));
        iv_back.setVisibility(View.VISIBLE);
    }
    private void initRecyclerView() {
        messageAdapter = new MessageAdapter(this, MessageUtil.getMessageResData().rspBody);
        message_recyclerview.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));

        message_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        message_recyclerview.setNestedScrollingEnabled(false);
        message_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        message_recyclerview.setFocusable(false);
        message_recyclerview.setAdapter(messageAdapter);
    }

}
