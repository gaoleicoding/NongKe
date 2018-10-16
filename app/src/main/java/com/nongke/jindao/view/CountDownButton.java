package com.nongke.jindao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.nongke.jindao.R;

public class CountDownButton extends AppCompatTextView {

    //总时长
    private long millisinfuture;

    //间隔时长
    private long countdowninterva;

    //默认背景颜色
    private int normalColor;

    //倒计时 背景颜色
    private int countDownColor;

    //是否结束
    private boolean isFinish;

    //定时器
    private CountDownTimer countDownTimer;

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownButton, defStyleAttr, 0);
        //设置默认时长
        millisinfuture = (long) typedArray.getInt(R.styleable.CountDownButton_millisinfuture, 60000);
        //设置默认间隔时长
        countdowninterva = (long) typedArray.getInt(R.styleable.CountDownButton_countdowninterva, 1000);
        //设置默认背景色
        normalColor = typedArray.getColor(R.styleable.CountDownButton_normalColor, getResources().getColor(R.color.app_color));
        //设置默认倒计时 背景色
        countDownColor = typedArray.getColor(R.styleable.CountDownButton_countDownColor, getResources().getColor(R.color.app_color));
        typedArray.recycle();
        //默认为已结束状态
        isFinish = true;
        //字体居中
        setGravity(Gravity.CENTER);
        //默认文字和背景色
//        normalBackground();
        //设置定时器
        countDownTimer = new CountDownTimer(millisinfuture, countdowninterva) {
            @Override
            public void onTick(long millisUntilFinished) {
                //未结束
                isFinish = false;

                setText((Math.round((double) millisUntilFinished / 1000) - 1) + "秒");

//                setBackgroundResource(countDownColor);

            }

            @Override
            public void onFinish() {
                //结束
                isFinish = true;
                setText(getResources().getString(R.string.get_veirfy_code));
//                normalBackground();
            }
        };
//        setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //这里判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
//                if (isFinish()) {
//                    //发送验证码请求成功后调用
//                    start();
//                }
//            }
//        });

    }

    private void normalBackground() {
        setText("获取验证码");
        setTextColor(getResources().getColor(R.color.white));
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void cancel() {
        countDownTimer.cancel();
    }

    public void start() {
        countDownTimer.start();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isFinish()) {
            cancel();
        }
    }

}
