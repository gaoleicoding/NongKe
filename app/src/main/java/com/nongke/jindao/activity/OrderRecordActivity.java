package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.OrderRecordAdapter;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.event.ManageOrderEvent;
import com.nongke.jindao.base.mmodel.OrderRecordResData;
import com.nongke.jindao.base.mmodel.ProductOrder;
import com.nongke.jindao.mcontract.OrderRecordContract;
import com.nongke.jindao.mpresenter.OrderRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class OrderRecordActivity extends BaseMvpActivity<OrderRecordPresenter> implements OrderRecordContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_order_record_hint)
    TextView tv_order_record_hint;
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.order_expand_listview)
    ExpandableListView order_expand_listview;
    private OrderRecordAdapter orderRecordAdapter;
    private List<ProductOrder> orderRecordList;
    boolean hasNextPage = true;
    String TAG = "OrderRecordActivity";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OrderRecordActivity.class);
        context.startActivity(intent);
    }


    @Override
    public OrderRecordPresenter initPresenter() {
        return new OrderRecordPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.pageUserOrderInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_record;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_order));
        iv_back.setVisibility(View.VISIBLE);
        orderRecordList = new ArrayList<ProductOrder>();
        initSmartRefreshLayout();
        orderRecordAdapter = new OrderRecordAdapter(this, orderRecordList);
        order_expand_listview.setAdapter(orderRecordAdapter);
        order_expand_listview.setGroupIndicator(null);
        EventBus.getDefault().register(this);
    }

    @Override
    public void showUserOrderInfo(OrderRecordResData orderRecordResData) {

        final List<ProductOrder> newDataList = orderRecordResData.rspBody.list;
        hasNextPage = orderRecordResData.rspBody.hasNextPage;
        if (newDataList.size() == 0) {
            smartRefreshLayout.finishLoadMore();
            return;
        }
        orderRecordList.addAll(newDataList);
        if (orderRecordList.size() == 0) {
            tv_order_record_hint.setText("你还没有订单");
            tv_order_record_hint.setVisibility(View.VISIBLE);
        } else {
            orderRecordAdapter.setList(orderRecordList);
            tv_order_record_hint.setVisibility(View.GONE);
            //        //设置列表展开
            for (int i = 0; i < orderRecordList.size(); i++) {
                order_expand_listview.expandGroup(i);
            }
            smartRefreshLayout.finishLoadMore();
        }
    }


    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                Log.d(TAG, "hasNextPage----------------" + hasNextPage);
                if (hasNextPage)
                    mPresenter.onLoadMore();
                else
                    smartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.onRefreshMore();
            }
        });
    }

    /**
     * 初始点击事件
     */
    private void initOnclickListener() {
        //设置父标题点击不能收缩
        order_expand_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //订单子条目的点击事件
        order_expand_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(OrderRecordActivity.this, "跳转到订单详细页面:" + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ManageOrderEvent manageOrderEvent) {
        Log.d(TAG,"manageOrderEvent.orderId----------"+manageOrderEvent.orderId);
        order_expand_listview.collapseGroup(manageOrderEvent.groupPosition);
        order_expand_listview.expandGroup(manageOrderEvent.groupPosition);
        if (manageOrderEvent.manageType == -1)
            mPresenter.deleteUserOrderInfo(manageOrderEvent.orderId);
        if (manageOrderEvent.manageType == 1)
            mPresenter.confirmUserOrderInfo(manageOrderEvent.orderId);
    }

}
