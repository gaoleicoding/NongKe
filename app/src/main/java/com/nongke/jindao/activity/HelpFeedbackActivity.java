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

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class HelpFeedbackActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HelpFeedbackActivity.class);
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
        return R.layout.activity_bill;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.help_feedback));
        iv_back.setVisibility(View.VISIBLE);
    }


}
