package com.nongke.jindao.base.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.nongke.jindao.base.utils.ScreenUtils;
import com.umeng.commonsdk.UMConfigure;


public class CustomApplication extends Application {
    public static ConnectivityManager connectivityManager;
    public static Context context;
    public static int screenWidth, screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        context = this;
        screenWidth = ScreenUtils.getScreenWidth(this);
        screenWidth = ScreenUtils.getScreenHeight(this);
//        LeakCanary.install(this);
//        BlockCanary.install(this, new AppContext()).start();

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "4167f539d904ec5d156185559c14d5d6");
        initUmengPush();
    }

    private void initUmengPush() {
//        PushAgent mPushAgent = PushAgent.getInstance(this);
////注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//            }
//        });
    }

//    public class AppContext extends BlockCanaryContext {
//        private static final String TAG = "AppContext";
//
//        @Override
//        public String provideQualifier() {
//            String qualifier = "";
//            try {
//                PackageInfo info = getApplicationContext().getPackageManager()
//                        .getPackageInfo(getApplicationContext().getPackageName(), 0);
//                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
//            } catch (PackageManager.NameNotFoundException e) {
//                Log.e(TAG, "provideQualifier exception", e);
//            }
//            return qualifier;
//        }
//
//        @Override
//        public int provideBlockThreshold() {
//            return 1000;
//        }
//
//        @Override
//        public boolean displayNotification() {
//            return BuildConfig.DEBUG;
//        }
//
//        @Override
//        public boolean stopWhenDebugging() {
//            return false;
//        }
//    }
//
//
}
