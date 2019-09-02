package com.lzy.imagepicker.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.imagepicker.R;

public class IconUtils {
    static String TAG="IconUtils";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getIconForPath(String path){
        String[] split = path.split("\\.");
        Log.e(TAG, "getIconForPath: "+new Gson().toJson(split));
        if (split.length>=2){
           return  GetBtimapStyle1(split[split.length-1]);
        }else {
           return GetBtimapStyle1("?");
        }
    }

    /**
     * 通过 文件 后缀 获取 文件图标。
     * @param path
     * @return
     */
    public static Bitmap getFileIconForPath(String path, Context context){
        String[] split = path.split("\\.");
        Log.e(TAG, "getIconForPath: "+new Gson().toJson(split));
        if (split.length>=2){
            return  GetBtimapStyle2(split[split.length-1],context);
        }else {
            return GetBtimapStyle2("?",context);
        }
    }


    /**
     * 创建已知的后缀类型。
     * @param s
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap GetBtimapStyle1(String s) {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#BBFFFF"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);       // 设置抗锯齿
        canvas.drawRoundRect(2.5f, 2.5f, 100-2.5f, 100-2.5f, 20, 20, mPaint);
        TextPaint textPaint=new TextPaint();
        textPaint.setColor(Color.parseColor("#FFFFFF"));
        if (s.length()>3){
            textPaint.setTextSize(30);
        }else {
            textPaint.setTextSize(40);
        }
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);       // 设置抗锯齿
        textPaint.setTextAlign(Paint.Align.CENTER); // 设置字体居中
        RectF rectF=new RectF(0, 0, 100, 100);
        //计算baseline
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline=rectF.centerY()+distance;
        canvas.drawText(s, rectF.centerX(), baseline, textPaint);
        return bitmap;
    }

    /**
     * 获取svg
     * @param s
     * @param context
     * @return
     */
    private static Bitmap GetBtimapStyle2(String s, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#BBFFFF"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);       // 设置抗锯齿
        TextPaint textPaint=new TextPaint();
        textPaint.setColor(Color.parseColor("#FFFFFF"));
        RectF rectF=new RectF(0, 80, 200, 200);
        canvas.drawBitmap( getBgBitmap(context),0,0,mPaint);
        if (s.length()>3){
            textPaint.setTextSize(40);
        }else {
            textPaint.setTextSize(50);
        }
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);       // 设置抗锯齿
        textPaint.setTextAlign(Paint.Align.CENTER); // 设置字体居中

        //计算baseline
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline=rectF.centerY()+distance;
        canvas.drawText(s, rectF.centerX(), baseline, textPaint);
        return bitmap;
    }

    /**
     *
     * @param context
     */
    private static Bitmap getBgBitmap(Context context) {
        Bitmap origin= BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_file_bg);
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) 200) / width;
        float scaleHeight = ((float) 200) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }
}
