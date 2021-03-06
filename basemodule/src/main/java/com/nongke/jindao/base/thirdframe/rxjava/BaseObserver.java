package com.nongke.jindao.base.thirdframe.rxjava;

import android.app.Dialog;
import android.content.Context;

import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mview.BaseView;
import com.nongke.jindao.base.utils.NetUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.view.CustomProgressDialog;

import java.io.IOException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    protected String errMsg;
    private boolean isShowError = true;
    private Context context;
    Dialog prgressDialog;

    protected BaseObserver(boolean isShowDialog) {
        // context在CustomProgressDialog中用到
        this.context = context;
        if (isShowDialog) {
            prgressDialog = CustomProgressDialog.createLoadingDialog(BaseActivity.context);
            prgressDialog.setCancelable(true);//允许返回
            prgressDialog.show();//显示
        }
    }

    protected BaseObserver(BaseView view, boolean isShowError) {
        this.isShowError = isShowError;
    }


    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (prgressDialog != null)
            prgressDialog.cancel();
        if (!NetUtils.isConnected()) {
            errMsg = "网络连接出错,请检查网络";

        } else if (e instanceof HttpException) {
            errMsg = "服务器访问异常(HttpException)";
        } else if (e instanceof IOException) {
            errMsg = "服务器访问异常(IOException)";
        }
        if (errMsg != null)
            Utils.showToast(errMsg, true);

    }

    @Override
    public void onComplete() {
        if (prgressDialog != null)
            prgressDialog.cancel();
    }

}
