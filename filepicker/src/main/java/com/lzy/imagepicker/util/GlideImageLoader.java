package com.lzy.imagepicker.util;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
}
