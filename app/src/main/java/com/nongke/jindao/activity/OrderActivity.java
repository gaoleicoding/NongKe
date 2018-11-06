package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nongke.jindao.R;
import com.nongke.jindao.adapter.OrderProductAdapter;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.pay.PayResult;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.event.UpdateAddressEvent;
import com.nongke.jindao.mcontract.OrderProductContract;
import com.nongke.jindao.mpresenter.OrderProductPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nongke.jindao.base.pay.alipay.AliPayUtil.SDK_PAY_FLAG;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class OrderActivity extends BaseMvpActivity<OrderProductPresenter> implements OrderProductContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_product_total_price)
    TextView tv_product_total_price;
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;
    @BindView(R.id.tv_order_discount_money)
    TextView tv_order_discount_money;
    @BindView(R.id.tv_order_indeed_pay)
    TextView tv_order_indeed_pay;
    @BindView(R.id.et_balance_pay)
    EditText et_balance_pay;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_order_postage)
    TextView tv_order_postage;
    @BindView(R.id.order_product_recyclerview)
    RecyclerView order_product_recyclerview;
    @BindView(R.id.ll_order_discount_layout)
    LinearLayout ll_order_discount_layout;
    @BindView(R.id.ll_balance_pay)
    LinearLayout ll_balance_pay;
    @BindView(R.id.ll_order_postage)
    LinearLayout ll_order_postage;
    @BindView(R.id.rl_add_address)
    RelativeLayout rl_add_address;

    private List<Product> orderProductList;
    private OrderProductAdapter orderProductAdapter;
    String TAG = "OrderActivity";
//    float totalPrice, indeedPayPrice;
    float discountMoney ,totalMoney,rmb,totalPay,cornMoney=0;
    String orderId, phone, userName, address;
    //totalPay(折后金额) = rmb（用户付款金额）+cornMoney（余额）
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }

        ;
    };

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("params", bundle);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
        title.setText(getString(R.string.order_detail));
        iv_back.setVisibility(View.VISIBLE);
        orderProductList = (List<Product>) bundle.getSerializable("product_list");

        initRecyclerView();
//        if (UserUtil.getUserInfo().rspBody.money > 10)
//            ll_balance_pay.setVisibility(View.GONE);
//        for (int i = 0; i < orderProductList.size(); i++) {
//            Product productInfo = orderProductList.get(i);
//            totalPrice = totalPrice + productInfo.productPrice * productInfo.amount;
//        }
//        tv_order_money.setText(totalPrice + "");
//        if (UserUtil.getUserInfo().rspBody.isVip == 1) {
//            ll_order_discount_layout.setVisibility(View.VISIBLE);
//            int productDiscount = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.vip_discount.content);
//            tv_order_discount_money.setText(discountMoney + "");
//        } else {
//            indeedPayPrice = totalPrice;
//        }
//
//        tv_order_indeed_pay.setText(indeedPayPrice + "");
//        int postage = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.postage.content);
//        if (postage > 0) {
//            ll_order_postage.setVisibility(View.VISIBLE);
//            tv_order_postage.setText(postage + "");
//        }

        LogUtil.d(TAG, "orderProductList.size():" + orderProductList.size());
        et_balance_pay.setHint(UserUtil.userInfo.rspBody.money+"");
        et_balance_pay.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                //text  输入框中改变后的字符串信息
                //start 输入框中改变后的字符串的起始位置
                //before 输入框中改变前的字符串的位置 默认为0
                //count 输入框中改变后的一共输入字符串的数量
                String ammountText = text.toString();
                float ammount = 0;
                if (ammountText.trim().equalsIgnoreCase("")) return;
                ammount = Utils.stringToFloat(ammountText);
                if (ammount > UserUtil.getUserInfo().rspBody.money) {
                    Utils.showToast("您输入的金额大于你的余额", false);
                    return;
                }
                cornMoney=ammount;

            }

            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                //text  输入框中改变前的字符串信息
                //start 输入框中改变前的字符串的起始位置
                //count 输入框中改变前后的字符串改变数量一般为0
                //after 输入框中改变后的字符串与起始位置的偏移量

            }

            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息

            }
        });
    }

    @Override
    public OrderProductPresenter initPresenter() {
        return new OrderProductPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.buyProduct(orderProductList);
        mPresenter.getUserAddress();
    }

    @OnClick({R.id.rl_address, R.id.rl_add_address, R.id.tv_pay})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_pay:
                if (rl_add_address.getVisibility() == View.VISIBLE) {
                    Utils.showToast("请先完善收货人信息", false);
                    return;
                }
                if(orderId==null){
                    Utils.showToast("订单还未生成，请稍后再支付",false);
                    return;
                }
                float postage = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.postage.content);
                mPresenter.payForProductOnline(orderId, 4, 3, new Gson().toJson(orderProductList),
                        cornMoney, discountMoney-cornMoney, discountMoney-cornMoney+postage, 0, UserUtil.userInfo.rspBody.uid, phone,
                        userName, address);


                break;
            case R.id.rl_address:
                MyAddressActivity.startActivity(OrderActivity.this);
                break;
            case R.id.rl_add_address:
                MyAddressActivity.startActivity(OrderActivity.this);
                break;


        }
    }


    private void initRecyclerView() {
        orderProductAdapter = new OrderProductAdapter(this, orderProductList);
        order_product_recyclerview.addItemDecoration(new SpacesItemDecoration(this, 2));

        order_product_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        order_product_recyclerview.setNestedScrollingEnabled(false);
        order_product_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        order_product_recyclerview.setFocusable(false);
        order_product_recyclerview.setAdapter(orderProductAdapter);
    }


    @Override
    public void showUserAddressResData(MyAddressResData userAddressResData) {
        if (tv_phone == null || tv_name == null || tv_address == null) return;

        if (userAddressResData == null)
            return;
        if (userAddressResData.retCode.equals("10000") && userAddressResData.retDesc.equals("操作成功")) {
            if (userAddressResData.rspBody.userName.length() > 0 && userAddressResData.rspBody.address.length() > 0)
                rl_add_address.setVisibility(View.GONE);
            phone = UserUtil.userInfo.rspBody.phone;
            userName = userAddressResData.rspBody.userName;
            address = userAddressResData.rspBody.address;
            tv_phone.setText(phone);
            tv_name.setText("收货人：" + userName);
            tv_address.setText("收货地址：" + address);
        }
    }

    @Override
    public void showOrderProduct(OrderProductResData productResData) {
        orderId = productResData.rspBody.orderId;
        totalMoney=productResData.rspBody.totalMoney;
        discountMoney = productResData.rspBody.discountMoney;
        float postage = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.postage.content);
        if (postage > 0) {
            ll_order_postage.setVisibility(View.VISIBLE);
            tv_order_postage.setText(postage + "");
        }
        tv_order_money.setText(totalMoney + "");
        tv_order_indeed_pay.setText(discountMoney-cornMoney + "");
        tv_product_total_price.setText(discountMoney-cornMoney + postage + "");
    }

    @Override
    public void showOrderProductPayRes(RechargeResData rechargeResData) {
        final String paySign = rechargeResData.rspBody.paySign;
        Log.d(TAG, "paySign:" + paySign);
        AliPayUtil.pay(mHandler, this, paySign);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateAddressEvent updateAddressEvent) {
        mPresenter.getUserAddress();
    }


    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
