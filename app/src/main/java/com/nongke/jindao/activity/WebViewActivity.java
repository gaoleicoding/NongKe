package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.mcontract.MyInviterContract;
import com.nongke.jindao.mpresenter.MyInviterPresenter;
import com.nongke.jindao.view.CustomWebview;

import butterknife.BindView;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class WebViewActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    CustomWebview webView;
    String fromWhere;
    String url;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("params", bundle);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initData(Bundle bundle) {
        fromWhere = bundle.getString("fromWhere", "");
        url = bundle.getString("url", "");
        LogUtil.d("fromWhere-------------:" + fromWhere);
        if (Constants.FROM_DOWNLOAD.equals(fromWhere)) {
            title.setText(getString(R.string.app_download));
        }
        if (Constants.COMPANY_WEBSITE.equals(fromWhere)) {
            title.setText(getString(R.string.company_introduction));
        }
        webView.loadUrl(url);
        iv_back.setVisibility(View.VISIBLE);

    }

    @Override
    public MyInviterPresenter initPresenter() {
        return new MyInviterPresenter();
    }

    @Override
    protected void loadData() {

    }


}
