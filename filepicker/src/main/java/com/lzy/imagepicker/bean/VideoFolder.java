package com.lzy.imagepicker.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：视频文件夹
 * 修订历史：
 * ================================================
 */
public class VideoFolder implements Serializable {

    public String name;  //当前文件夹的名字
    public String path;  //当前文件夹的路径
    public VideoItem cover;   //当前文件夹需要要显示的缩略图，默认为最近的一次图片
    public ArrayList<VideoItem> videos;  //当前文件夹下所有图片的集合

    /** 只要文件夹的路径和名字相同，就认为是相同的文件夹 */
    @Override
    public boolean equals(Object o) {
        try {
            VideoFolder other = (VideoFolder) o;
            return this.path.equalsIgnoreCase(other.path) && this.name.equalsIgnoreCase(other.name);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
