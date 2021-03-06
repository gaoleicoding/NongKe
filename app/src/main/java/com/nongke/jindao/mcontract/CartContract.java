package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.ProductResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class CartContract {

    public interface Presenter {

        void getCartProduct();

        void updateProductAmount(int amount, int productId);
        void deleteProduct(int amount, int productId);
        void clearCart();
    }

    public interface View {

        void showProduct(ProductResData productResData);

    }
}
