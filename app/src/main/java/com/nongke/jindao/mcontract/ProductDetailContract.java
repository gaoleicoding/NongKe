package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mmodel.RegisterResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class ProductDetailContract {

    public interface Presenter {

        void saveCart(int amount, int productId);

    }

    public interface View {

    }
}
