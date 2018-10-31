package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.UserUtil;
import com.nongke.jindao.base.event.LoginAccountEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class RechageDetailFragment extends BaseMvpFragment {

    @BindView(R.id.tv_recharge_50)
    TextView tv_recharge_50;
    @BindView(R.id.tv_recharge_100)
    TextView tv_recharge_100;
    @BindView(R.id.tv_vip_recharge)
    TextView tv_vip_recharge;
    @BindView(R.id.ll_pay_alipay)
    LinearLayout ll_pay_alipay;
    @BindView(R.id.ll_pay_wechat)
    LinearLayout ll_pay_wechat;
    @BindView(R.id.img_pay_alipay)
    ImageView img_pay_alipay;
    @BindView(R.id.img_pay_wechat)
    ImageView img_pay_wechat;

    @Override
    public void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
        judgeVip();
    }

    @Override
    public void initView() {

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_recharge_detail;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({ R.id.tv_vip_recharge,R.id.tv_recharge_50, R.id.tv_recharge_100,R.id.ll_pay_alipay, R.id.ll_pay_wechat})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_vip_recharge:
                VipRechargeActivity.startActivity(getActivity());
                break;
            case R.id.tv_recharge_50:
                tv_recharge_50.setBackgroundResource(R.drawable.shape_recharge_ammount_bg_select);
                tv_recharge_50.setTextColor(getResources().getColor(R.color.white));
                tv_recharge_100.setBackgroundResource(R.drawable.shape_recharge_ammount_bg);
                tv_recharge_100.setTextColor(getResources().getColor(R.color.color_black));
//                throw new NullPointerException();
                break;
            case R.id.tv_recharge_100:
                tv_recharge_100.setBackgroundResource(R.drawable.shape_recharge_ammount_bg_select);
                tv_recharge_100.setTextColor(getResources().getColor(R.color.white));
                tv_recharge_50.setBackgroundResource(R.drawable.shape_recharge_ammount_bg);
                tv_recharge_50.setTextColor(getResources().getColor(R.color.color_black));
                break;
            case R.id.ll_pay_alipay:
                img_pay_alipay.setImageResource(R.drawable.icon_pay_select);
                img_pay_wechat.setImageResource(R.drawable.icon_pay_unselect);
                break;
            case R.id.ll_pay_wechat:
                img_pay_wechat.setImageResource(R.drawable.icon_pay_select);
                img_pay_alipay.setImageResource(R.drawable.icon_pay_unselect);
                break;
            default:
                break;
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginAccountEvent accountEvent) {
        judgeVip();
    }

    private void judgeVip(){
        if (UserUtil.isLogined()) {
            if(UserUtil.getUserInfo().rspBody.isVip==1){
                tv_vip_recharge.setVisibility(View.GONE);
                tv_recharge_50.setText(getResources().getString(R.string.recharge_50));
                tv_recharge_100.setText(getResources().getString(R.string.recharge_100));
                tv_recharge_50.setTextSize(16);
                tv_recharge_100.setTextSize(16);
            }else{
                tv_vip_recharge.setVisibility(View.VISIBLE);
                tv_recharge_50.setText("50元");
                tv_recharge_100.setText("100元");
                tv_recharge_50.setTextSize(18);
                tv_recharge_100.setTextSize(18);
            }
        }
    }
}