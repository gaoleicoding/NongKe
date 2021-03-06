package com.nongke.jindao.update;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.base.utils.FileProvider7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static butterknife.internal.Utils.arrayOf;
import static java.lang.Thread.sleep;

public class UpdateApk {


    //下载进度
    private static ProgressDialog progressDialog;
    public static Activity activity;

    public static void downFile(final String url, final Activity context) {
        activity = context;
        progressDialog = new ProgressDialog(context);    //进度条，在下载的时候实时更新进度，提高用户友好度
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);//点击返回键，禁止退出
        progressDialog.show();

        HttpNet.httpDownLoadApk(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //下载失败
                downFial(context);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //下载。。。
                InputStream is = null;//输入流
                FileOutputStream fos = null;//输出流
                try {
                    is = response.body().byteStream();//获取输入流
                    long total = response.body().contentLength();//获取文件大小
                    setMax(total, context);//为progressDialog设置大小
                    File file = null;
                    if (is != null) {
                        file = new File(Environment.getExternalStorageDirectory(), "jindao.apk");// 设置路径
                        if (file.exists()) file.delete();
                        fos = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int process = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fos.write(buf, 0, ch);
                            process += ch;
                            downLoading(process, context);       //这里就是关键的实时更新进度了！
                        }
                    }
                    fos.flush();
                    // 下载完成
                    if (fos != null) {
                        fos.close();
                    }
                    sleep(1000);
                    downSuccess(context, file);
                } catch (Exception e) {
                    downFial(context);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

    }

    /**
     * 进度条实时更新
     *
     * @param i
     */
    public static void downLoading(final int i, Context context) {
        (activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setProgress(i);
            }
        });
    }

    public static void downSuccess(final Context context, final File file) {
        //安装
        (activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    sleep(1000);
                    installApk(file, context);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        activity=null;
    }

    public static void installApk(File file, Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//广播里面操作需要加上这句，存在于一个独立的栈里

        // 兼容系统7.0及以上版本
        FileProvider7.setIntentDataAndType(context,
                intent, "application/vnd.android.package-archive", file, false);

        context.startActivity(intent);

    }

    public static void downFial(final Context context) {
        (activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                Toast.makeText(context, "更新失败", Toast.LENGTH_LONG).show();
            }
        });
        activity=null;
    }

    public static void setMax(final long total, Context context) {
        (activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMax((int) total);
            }
        });
    }

}
