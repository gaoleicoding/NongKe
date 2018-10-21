package com.nongke.jindao.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.qrcode.LogoConfig;
import com.nongke.jindao.base.utils.UserUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class PromotionActivity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;
    @BindView(R.id.tv_copy_link)
    TextView tv_copy_link;

    String shareContent;

    /**
     * 黑点颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 正方形二维码宽度
     */
    private static final int CODE_WIDTH = 600;
    /**
     * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
     */
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 7;
    /**
     *LOGO宽度值,最小不能小鱼二维码10%宽度值,小于影响Logo与二维码的整体搭配
     */
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;
    /**
     * 生成的二维码图片存储的URI
     */
    private Uri imageFileUri;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PromotionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_promotion;
    }

    @Override
    protected void initData(Bundle bundle) {
        generateCode();
    }

    @OnClick({ R.id.tv_copy_link})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_copy_link:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(shareContent);
                Toast.makeText(this, "复制成功，可以分享给朋友们了", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

    }
    private void generateCode(){
        if(UserUtils.getUserInfo()!=null) {

            shareContent = UserUtils.getUserInfo().rspBody.imgUrl.trim();
            try {
                if (!TextUtils.isEmpty(shareContent)) {
                    LogoConfig logoConfig = new LogoConfig();
                    Bitmap logoBitmap = logoConfig.modifyLogo(
                            BitmapFactory.decodeResource(getResources(),
                                    R.drawable.white_bg), BitmapFactory
                                    .decodeResource(getResources(),
                                            R.drawable.app_icon));
                    Bitmap codeBitmap = createCode(shareContent, logoBitmap);
                    saveBitmap(codeBitmap, "guolin_code.png");
                    iv_qrcode.setImageBitmap(codeBitmap);
                } else {
                    Toast.makeText(PromotionActivity.this, "请输入要生成的字符串",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            iv_qrcode.setOnLongClickListener(mOnLongClick);
        }
    }
    /**
     * 生成带LOGO的二维码
     */
    public Bitmap createCode(String content, Bitmap logoBitmap)
            throws WriterException {
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();
        int logoHaleWidth = logoWidth >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        int logoHaleHeight = logoHeight >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();
        float sx = (float) 2 * logoHaleWidth / logoWidth;
        float sy = (float) 2 * logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);
        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别,H为最高
        hints.put(EncodeHintType.MAX_SIZE, LOGO_WIDTH_MAX);// 设置图片的最大值
        hints.put(EncodeHintType.MIN_SIZE, LOGO_WIDTH_MIN);// 设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 2);//设置白色边距值
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_WIDTH, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /*
                 * 取值范围,可以画图理解下
                 * halfW + newLogoWidth / 2 - (halfW - newLogoWidth / 2) = newLogoWidth
                 * halfH + newLogoHeight / 2 - (halfH - newLogoHeight) = newLogoHeight
                 */
                if (x > halfW - newLogoWidth / 2 && x < halfW + newLogoWidth / 2
                        && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
                    /*
                     *  记录图片每个像素信息
                     *  halfW - newLogoWidth / 2 < x < halfW + newLogoWidth / 2
                     *  --> 0 < x - halfW + newLogoWidth / 2 < newLogoWidth
                     *   halfH - newLogoHeight / 2  < y < halfH + newLogoHeight / 2
                     *   -->0 < y - halfH + newLogoHeight / 2 < newLogoHeight
                     *   刚好取值newLogoBitmap。getPixel(0-newLogoWidth,0-newLogoHeight);
                     */
                    pixels[y * width + x] = newLogoBitmap.getPixel(
                            x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                } else {
                    pixels[y * width + x] = matrix.get(x, y) ? BLACK: WHITE;// 设置信息
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 保存生成的二维码图片
     */
    private void saveBitmap(Bitmap bitmap, String bitName){
        //获取与应用相关联的路径
        String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File imageFile = new File(imageFilePath,"/" + bitName);// 通过路径创建保存文件
        imageFileUri = Uri.fromFile(imageFile);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(imageFile);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
        }
    }
    private View.OnLongClickListener mOnLongClick = new View.OnLongClickListener(){

        @Override
        public boolean onLongClick(View v) {
            switch(v.getId()){
                case R.id.iv_qrcode:
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageFileUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, "分享到"));
                    break;
                default:
                    break;
            }
            return false;
        }

    };
}