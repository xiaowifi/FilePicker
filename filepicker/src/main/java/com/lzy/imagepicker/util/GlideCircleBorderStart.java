package com.lzy.imagepicker.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.util.TypedValue;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.lzy.imagepicker.R;

/**
 * 圆形边框的图片。
 */

public class GlideCircleBorderStart extends BitmapTransformation {
    private Paint mBorderPaint;
    private float mBorderWidth;
    private static float radius = 0f;
    private int start;
    Context context;
    String TAG="GlideCircleBorderStart";
    public GlideCircleBorderStart(Context context, int borderColor, int start) {
        super(context);
        this.start=start;
        this.context=context;
        mBorderWidth=dp2px(1.5f);
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        Log.e(TAG, "GlideCircleBorderStart: "+start );
    }

    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        //在这个bitmap 上的底部的30%处，居中绘制几个小星星。如果等于0 就没有
        if (start>0){
            drStart(new Canvas(squared));
        }
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        //创建画笔 画布 手动描绘边框
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    private void drStart(Canvas canvas) {
        int w= canvas.getWidth();
        int h= (int) (w*0.3f);
        int start_w=h/2;
        Paint mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#ff0077"));
        mPaint.setAlpha(200);
       // mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);       // 设置抗锯齿
        //在底部的区域固定区域内绘制透明
        RectF rectF=new RectF(0, w-h, w, w);
        canvas.drawRect(rectF,mPaint);
        float c_x=rectF.centerX();
        float c_y=rectF.centerY()-10;
        float left_x=c_x;
        float right_x=c_x;
        float gap=10;
        //在这个地方 开始绘制星星。计算出将要绘制出的星星的大小，由于懒，所以在小于等于3的时候就是除以2。其他就除以4
        if (start>3){
            start_w=h/4;
        }
        Bitmap startb= getStartBitmap(start_w);
        if (start%2==1){
            //中间有一颗
            for (int i=1;i<=start;i++){
                if (i==1){
                    canvas.drawBitmap(startb,c_x-(start_w/2),c_y-(start_w/2),mPaint);
                    left_x=left_x-(start_w/2)-gap;
                    right_x=right_x+(start_w/2)+gap;
                }else {
                    //现在绘制两边的。
                    if (i%2==1){
                        //绘制右边
                        if (right_x+start_w+gap<w){
                            canvas.drawBitmap(startb,right_x,c_y-(start_w/2),mPaint);
                            right_x=right_x+start_w+gap;
                        }
                    }else {
                        //绘制左边。如果 现有空间大于 一个星星宽度+10
                        if (left_x>(start_w+gap)){
                            canvas.drawBitmap(startb,left_x-start_w,c_y-(start_w/2),mPaint);
                            left_x=left_x-start_w-gap;
                        }

                    }
                }
            }
        }else {
            //中线对齐。
            left_x=left_x-gap/2;
            right_x=right_x+gap/2;
            for (int i=1;i<=start;i++){
                if (i%2==1){
                    //绘制右边
                    if (right_x+start_w+gap<w){
                        canvas.drawBitmap(startb,right_x,c_y-(start_w/2),mPaint);
                        right_x=right_x+start_w+gap;
                    }
                }else {
                    //绘制左边。如果 现有空间大于 一个星星宽度+10
                    if (left_x>(start_w+gap)){
                        canvas.drawBitmap(startb,left_x-start_w,c_y-(start_w/2),mPaint);
                        left_x=left_x-start_w-gap;
                    }

                }
            }

        }


    }


    /**
     *
     *
     */
    private  Bitmap getStartBitmap(int w) {
        Bitmap origin= BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_maint_start);
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) w) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }


    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }
}
