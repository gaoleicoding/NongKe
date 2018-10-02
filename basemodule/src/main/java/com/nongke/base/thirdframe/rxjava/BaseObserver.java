package com.nongke.thirdframe.rxjava;

import android.app.Dialog;
import android.content.Context;

import com.nongke.base.activity.BaseActivity;
import com.nongke.base.application.CustomApplication;
import com.nongke.base.mview.BaseView;
import com.nongke.base.utils.NetUtils;
import com.nongke.base.utils.Utils;
import com.nongke.base.view.CustomProgressDialog;

import java.io.IOException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    protected String errMsg = "";
    private boolean isShowError = true;
    private Context context;
    Dialog prgressDialog;

    protected BaseObserver(boolean isShowDialog){
        // context在CustomProgressDialog中用到
        this.context=context;
        if(isShowDialog) {
            prgressDialog = CustomProgressDialog.createLoadingDialog(BaseActivity.context);
            prgressDialog.setCancelable(true);//允许返回
            prgressDialog.show();//显示
        }
    }
    protected BaseObserver(BaseView view, boolean isShowError){
        this.isShowError = isShowError;
    }



    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if(prgressDialog!=null)
      prgressDialog.cancel();
        if (!NetUtils.isConnected()) {
            errMsg = "网络连接出错,请检查网络";

        } else if (e instanceof HttpException) {
            errMsg = "服务器访问异常(HttpException)";
        } else if (e instanceof IOException) {
            errMsg = "服务器访问异常(IOException)";
        }

            Utils.showToast(errMsg);

    }

    @Override
    public void onComplete() {
        if(prgressDialog!=null)
        prgressDialog.cancel();
    }

}
