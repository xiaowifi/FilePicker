package com.lzy.imagepicker.util;

import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.R;

import java.io.File;

/**
 * 图片加载器。
 */
public class GlideImageLoader {
    public static void LoaderImage(String path, ImageView image){
        Glide.with(image.getContext())
                .load(path)
                .thumbnail(0.3f)
                .into(image);
    }

    /**
     * 加载本地图片。
     * @param path
     * @param image
     */
    public static void LoadLocalImage(String path,ImageView image){
        Glide.with(image.getContext())
                .load(Uri.fromFile(new File(path)))
                .thumbnail(0.3f)
                .into(image);
    }

    /**
     * 加载圆形 边框 图片。
     * @param url
     * @param img
     */
    public static void loadCircleBorder(String url,ImageView img,int start){
        Glide.with(img.getContext())
                .load(url)
                .centerCrop()
                .thumbnail(0.3f)
                // .placeholder(R.drawable.loading_spinner)//设置站位图片。
                .bitmapTransform(new GlideCircleBorderStart(img.getContext(), Color.parseColor("#ff943d"),start))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(img);
    }
}
