package com.lzy.imagepicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 一个全屏小红点哦。
 */
public class RedSpotView extends View {
    String TAG=this.getClass().getName();

    boolean show=false;
    private int dpSpot;
    private Paint mPaint;
    private float x=0;
    private float y=0;
    private int dpm;

    public RedSpotView(Context context) {
        super(context);
        initView();
    }

    public RedSpotView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RedSpotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (show){
            RadialGradient radialGradient = new RadialGradient(x, y, dpSpot/2, new int[]{Color.WHITE,Color.WHITE,  Color.RED,Color.RED,Color.TRANSPARENT}, null, Shader.TileMode.MIRROR);
            mPaint.setShader(radialGradient);
            canvas.drawCircle(
                    x, y, dpSpot/2, mPaint
            );
            show=false;
        }
    }

    /**
     * 显示。
     * @param x
     * @param y
     */
    public void onShow(float x,float y){
        this.x=x;
        this.y=y-dpm;
        show=true;
        invalidate();
    }

    private void initView() {
        dpSpot = dp2px(10);
        dpm = dp2px(30);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }




    /**
     * dp转px
     * @param dp
     * @return
     */
    public  int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
