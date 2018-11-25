package com.nongke.jindao.update;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpNet {
    /**
     * 下载apk
     */
    public static void httpDownLoadApk(String url, okhttp3.Callback callback) {
        // 打印日志
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }
}
