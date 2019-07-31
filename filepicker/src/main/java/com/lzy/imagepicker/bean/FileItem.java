package com.lzy.imagepicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：图片信息
 * 修订历史：
 * ================================================
 */
public class FileItem implements Serializable, Parcelable {

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间
    public String title;//标题
    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        if (o instanceof FileItem) {
            FileItem item = (FileItem) o;
            return this.path.equalsIgnoreCase(item.path) && this.addTime == item.addTime;
        }

        return super.equals(o);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.title);
        dest.writeLong(this.size);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
    }

    public FileItem() {
    }

    protected FileItem(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
        this.title= in.readString();
    }

    public static final Creator<FileItem> CREATOR = new Creator<FileItem>() {
        @Override
        public FileItem createFromParcel(Parcel source) {
            return new FileItem(source);
        }

        @Override
        public FileItem[] newArray(int size) {
            return new FileItem[size];
        }
    };
}
