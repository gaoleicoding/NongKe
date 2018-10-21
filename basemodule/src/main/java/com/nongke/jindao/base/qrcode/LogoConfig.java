package com.nongke.jindao.base.qrcode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.media.ThumbnailUtils;

/**
 * 
 * 项目名称 : ZXingScanQRCode<br>
 * 创建人 : skycracks<br>
 * 创建时间 : 2016-4-19下午9:53:29<br>
 * 版本 :	[v1.0]<br>
 * 类描述 : LOGO图片加上白色背景图片<br>
 */
public class LogoConfig {
	/**
	 * @return 返回带有白色背景框logo
	 */
	public Bitmap modifyLogo(Bitmap bgBitmap, Bitmap logoBitmap) {
		
		//读取背景图片，并构建绘图对象
		int bgWidth = bgBitmap.getWidth();
		int bgHeigh = bgBitmap.getHeight();
		//通过ThumbnailUtils压缩原图片，并指定宽高为背景图的3/4
		logoBitmap = ThumbnailUtils.extractThumbnail(logoBitmap,bgWidth*3/4, bgHeigh*3/4, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		Bitmap cvBitmap = Bitmap.createBitmap(bgWidth, bgHeigh, Config.ARGB_8888);
		Canvas canvas = new Canvas(cvBitmap);
		// 开始绘制图片
		canvas.drawBitmap(bgBitmap, 0, 0, null);
		canvas.drawBitmap(logoBitmap,(bgWidth - logoBitmap.getWidth()) /2,(bgHeigh - logoBitmap.getHeight()) / 2, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
		canvas.restore();
		if(cvBitmap.isRecycled()){
			cvBitmap.recycle();
		}
		return cvBitmap;
	}
}
