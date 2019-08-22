package com.lzy.imagepicker.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.Log;

import com.google.gson.Gson;

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
}
