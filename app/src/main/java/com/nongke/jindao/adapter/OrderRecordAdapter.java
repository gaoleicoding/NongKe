package com.nongke.jindao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductOrder;
import com.nongke.jindao.base.thirdframe.glide.ImageLoader;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;

public class OrderRecordAdapter extends BaseExpandableListAdapter {

    private List<ProductOrder> list;
    private Context context;
    String TAG = "OrderRecordAdapter";

    public OrderRecordAdapter(Context context, List<ProductOrder> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<ProductOrder> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).products.size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).products.get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.order_parent_view, null);
        }
        TextView tv_order = convertView.findViewById(R.id.tv_order);
        TextView tv_time = convertView.findViewById(R.id.tv_time);
        TextView tv_order_status = convertView.findViewById(R.id.tv_order_status);

        ProductOrder productOrder = list.get(groupPosition);
        tv_order.setText("订单号:" + productOrder.orderId);
        tv_time.setText(Utils.ms2Date(Long.parseLong(productOrder.createTime)));
        if (productOrder.statusDesc == 0)
            tv_order_status.setText("无效订单");
        if (productOrder.statusDesc == 1)
            tv_order_status.setText("未付款");
        if (productOrder.statusDesc == 2)
            tv_order_status.setText("已付款");
        if (productOrder.statusDesc == 3)
            tv_order_status.setText("交易成功");
        if (productOrder.statusDesc == 4)
            tv_order_status.setText("订单已取消");

        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.order_child_view, null);
        }
        //获取布局控件id
        ImageView iv_product = convertView.findViewById(R.id.iv_product);
        TextView tv_product_name = convertView.findViewById(R.id.tv_product_name);
        TextView tv_product_price = convertView.findViewById(R.id.tv_product_price);
        TextView tv_product_ammount = convertView.findViewById(R.id.tv_product_ammount);

        //根据服务器返回的数据来显示和隐藏按钮
        final Product productInfo = list.get(groupPosition).products.get(childPosition);

        tv_product_name.setText(productInfo.productName);
        tv_product_price.setText(productInfo.productPrice + "元");
        tv_product_ammount.setText("x "+productInfo.amount );
        ImageLoader.getInstance().load(context, productInfo.img, iv_product);

//        if (isLastChild) {
//            Log.d(TAG, "isLastChild----------------" + isLastChild);
//            View footerView = View.inflate(context, R.layout.order_child_footer_view, null);
//            parent.addView(footerView);
//            //设置评价按钮的点击事件
//            btnChildviewEvaluate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT).show();
//                }
//            });
//            //设置删除按钮的点击事件
//            btnChildviewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dataMap.get(titleArr[groupPosition]).remove(childPosition);
//                    notifyDataSetChanged();
//                }
//            });
//        }
        return convertView;

    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
