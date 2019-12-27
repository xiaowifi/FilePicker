package com.lzy.imagepicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 一个全屏小红点哦。
 */
public class RedDotView extends View {
    String TAG=this.getClass().getName();
    private int dpSpot;
    private Paint mPaint;
    private float x=0;
    private float y=0;
    int center_c=Color.WHITE;
    int edge_c=Color.RED;
    public RedDotView(Context context) {
        super(context);
        initView();
    }

    public RedDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RedDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            x=canvas.getWidth()/2;
            y=canvas.getHeight()/2;
            RadialGradient radialGradient = new RadialGradient(x, y, dpSpot, new int[]{center_c,center_c,  edge_c,edge_c}, null, Shader.TileMode.MIRROR);
            mPaint.setShader(radialGradient);
            canvas.drawCircle(
                    x, y, dpSpot, mPaint
            );
    }

    /**
     * 显示一个圆
     * @param center_c
     * @param edge_c
     * @param r
     */
    public void onShow(int center_c,int edge_c,int r){
        this.center_c=center_c;
        this.edge_c=edge_c;
        this.dpSpot=r;
        invalidate();
    }

    private void initView() {
        dpSpot = 50;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

}
