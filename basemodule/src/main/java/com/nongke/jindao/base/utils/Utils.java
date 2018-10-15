package com.nongke.jindao.base.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.nongke.jindao.base.application.CustomApplication;

import java.security.MessageDigest;

/**
 * Created by gaolei on 2018/6/15.
 */

public class Utils {



    /**
     * md5 加密
     *
     * @param str 要加密的字符串
     * @return
     */
    public static String md5Encode(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] bytes = md5.digest();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length() == 1) {
                    buf.append("0");
                }
                buf.append(s);
            }

        } catch (Exception ex) {
        }
        return buf.toString();
    }

    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo taskInfo = manager.getRunningTasks(1).get(0);
        ComponentName componentInfo = taskInfo.topActivity;
        String shortClassName = componentInfo.getShortClassName();    //类名
//        String className = info.topActivity.getClassName();              //完整类名
//        String packageName = info.topActivity.getPackageName();
        int index = shortClassName.lastIndexOf(".");
        shortClassName = shortClassName.substring(index + 1);
        return shortClassName;
    }

    //判断手机号是否正确
    public static boolean isPhoneNumberRight(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.length() == 11) {
            /*
             * 移动:134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
             * 联通:130、131、132、152、155、156、185、186 电信:133、153、180、189、(1349卫通)
             * 总结起来就是第一位必定为1,第二位必定为3或5或8,其他位置的可以为0-9
             */
            String telRegex = "[1][0123456789]//d{9}";// "[1]"代表第1位为数字1,"[358]"代表第二位可以为3、5、8中的一个,"//d{9}"代表后面是可以是0~9的数字,有9位。
            if (!phone.matches(telRegex)) {
                Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (!TextUtils.isEmpty(phone) && phone.length() < 11) {

            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;
    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    private static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("//d+")) {
// 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static void showToast( String content, boolean isShort) {
        Toast.makeText(CustomApplication.context, content, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();

    }
}
