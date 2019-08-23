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
public class RedSpotView extends View implements View.OnTouchListener {
    String TAG=this.getClass().getName();

    boolean show=false;
    private int dpSpot;
    private Paint mPaint;
    private float x=0;
    private float y=0;

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
        }
    }

    /**
     * 显示。
     * @param x
     * @param y
     */
    public void onShow(float x,float y){
        this.x=x;
        this.y=y;
        show=true;
        invalidate();
    }

    private void initView() {
        this.setOnTouchListener(this);
        dpSpot = dp2px(20);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
       Log.e(TAG, "onTouch: " );
        if (event.getAction() == MotionEvent.ACTION_UP){
            show=false;
        }else if (event.getAction()==MotionEvent.ACTION_DOWN){
            show=true;
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            x = event.getX();
            y = event.getY();
            invalidate();
        }
        return true;
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
