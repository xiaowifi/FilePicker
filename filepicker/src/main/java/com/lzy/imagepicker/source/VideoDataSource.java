package com.lzy.imagepicker.source;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.lzy.imagepicker.bean.VideoFolder;
import com.lzy.imagepicker.bean.VideoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取本地 mp4
 */
public class VideoDataSource implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ALL = 0;         //加载所有图片
    private final String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.WIDTH,
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATE_ADDED};
    FragmentActivity activity;
    private ArrayList<VideoFolder> videoFolders = new ArrayList<>();   //所有的图片文件夹
    OnVideoLoadedListener onVideoLoadedListener;
    Fragment fragment;
    /**
     * 暂时 未传入路径，直接检索 所有信息。
     * @param activity
     * @param onVideoLoadedListener
     */
    public VideoDataSource(FragmentActivity activity, OnVideoLoadedListener onVideoLoadedListener) {
        this.activity = activity;
        this.onVideoLoadedListener = onVideoLoadedListener;
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, this);
    }
    /**
     * 暂时 未传入路径，直接检索 所有信息。
     * @param fragment
     * @param onVideoLoadedListener
     */
    public VideoDataSource(Fragment fragment, OnVideoLoadedListener onVideoLoadedListener) {
        this.fragment = fragment;
        this.onVideoLoadedListener = onVideoLoadedListener;
        LoaderManager loaderManager = fragment.getLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, this);
    }

    String TAG=getClass().getName();
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        //创建查询。
        CursorLoader cursorLoader = new CursorLoader(activity==null?fragment.getContext():activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[6] + " DESC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.e(TAG, "onLoadFinished: 查询完成" );
        videoFolders.clear();
        if (cursor != null) {
            ArrayList<VideoItem> videos = new ArrayList<>();   //所有图片的集合,不分文件夹
            while (cursor.moveToNext()) {
                //查询数据
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[0]));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[1]));

                File file = new File(imagePath);
                if (!file.exists() || file.length() <= 0) {
                    continue;
                }

                long imageSize = cursor.getLong(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[2]));
                int imageWidth = cursor.getInt(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[3]));
                int imageHeight = cursor.getInt(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[4]));
                String imageMimeType = cursor.getString(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[5]));
                long imageAddTime = cursor.getLong(cursor.getColumnIndexOrThrow(VIDEO_PROJECTION[6]));
                //封装实体
                VideoItem imageItem = new VideoItem();
                imageItem.name = imageName;
                imageItem.path = imagePath;
                imageItem.size = imageSize;
                imageItem.width = imageWidth;
                imageItem.height = imageHeight;
                imageItem.mimeType = imageMimeType;
                imageItem.addTime = imageAddTime;
                videos.add(imageItem);
                //根据父路径分类存放图片
                File videoFile = new File(imagePath);
                File imageParentFile = videoFile.getParentFile();
                VideoFolder imageFolder = new VideoFolder();
                imageFolder.name = imageParentFile.getName();
                imageFolder.path = imageParentFile.getAbsolutePath();

                if (!videoFolders.contains(imageFolder)) {
                    ArrayList<VideoItem> images = new ArrayList<>();
                    images.add(imageItem);
                    imageFolder.cover = imageItem;
                    imageFolder.videos = images;
                    videoFolders.add(imageFolder);
                } else {
                    videoFolders.get(videoFolders.indexOf(imageFolder)).videos.add(imageItem);
                }
            }
            //防止没有图片报异常
            if (cursor.getCount() > 0 && videos.size()>0) {
                //构造所有图片的集合
                VideoFolder allImages = new VideoFolder();
                allImages.name = "所有视频";
                allImages.path = "/";
                allImages.cover = videos.get(0);
                allImages.videos = videos;
                videoFolders.add(0, allImages);  //确保第一条是所有图片
            }
            onVideoLoadedListener.onVideoLoaded(videoFolders);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.e(TAG, "onLoaderReset: 重启" );
    }
    /** 所有视频加载完成的回调接口 */
    public interface OnVideoLoadedListener {
        void onVideoLoaded(List<VideoFolder> videoFolders);
    }
}
