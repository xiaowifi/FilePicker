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

import com.google.gson.Gson;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.bean.FileItem;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 传入特定查询数据。文件。
 */
public class FileDataByMiniTypeSource implements LoaderManager.LoaderCallbacks<Cursor> {
    String TAG="FileDataSource";
    public static final int LOADER_ALL = 0;         //加载所有
    public static final int LOADER_CATEGORY = 1;//根据内容加载。
    private final String[] FILE_PROJECTION = {
            MediaStore.Files.FileColumns.DATA,//真实地址。
            MediaStore.Files.FileColumns.DISPLAY_NAME,//显示的名字
            MediaStore.Files.FileColumns.TITLE,//标题
            MediaStore.Files.FileColumns.SIZE,//大小。
            MediaStore.Files.FileColumns.MIME_TYPE,//后缀
            MediaStore.Files.FileColumns.DATE_ADDED,//添加时间。

    };
    ArrayList<String> querySuffix=new ArrayList<>();//需要查询的后缀 名。
    //获取到的minitype
    ArrayList<String> miniTypes=new ArrayList<>();
    FragmentActivity activity;
    OnFileLoadedListener loadedListener;
    String path;
    Fragment fragment;
    String selection;
    String[] selectionArgs;

    public FileDataByMiniTypeSource(ArrayList<String> querySuffix, OnFileLoadedListener loadedListener, String path, Fragment fragment) {
        this.querySuffix = querySuffix;
        this.loadedListener = loadedListener;
        this.path = path;
        this.fragment = fragment;
        LoaderManager loaderManager = fragment.getLoaderManager();
        if (path==null||path.isEmpty()){
            Log.e(TAG, "FileDataSource: path=null" );
            loaderManager.initLoader(LOADER_ALL, null, this);
        }else {
            //加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_CATEGORY, bundle, this);
        }
    }

    /**
     * 构造函数，后缀名查询。
     * @param querySuffix
     * @param activity
     * @param loadedListener
     * @param path
     */
    public FileDataByMiniTypeSource(ArrayList<String> querySuffix, FragmentActivity activity, OnFileLoadedListener loadedListener, String path) {
        this.querySuffix.clear();
        this.querySuffix.addAll(querySuffix);
        this.activity = activity;
        this.loadedListener = loadedListener;
        this.path = path;
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        if (path==null||path.isEmpty()){
            Log.e(TAG, "FileDataSource: path=null" );
            loaderManager.initLoader(LOADER_ALL, null, this);
        }else {
            //加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_CATEGORY, bundle, this);
        }
    }



    private ArrayList<FileFolder> fileFolders = new ArrayList<>();   //所有的文件文件夹

    /**
     * 构造 查询全部。
     * @param activity
     * @param loadedListener
     */
    public FileDataByMiniTypeSource(FragmentActivity activity, OnFileLoadedListener loadedListener) {
        this.activity = activity;
        this.loadedListener = loadedListener;
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, this);
    }
    /**
     * 构造 查询全部。
     * @param fragment
     * @param loadedListener
     */
    public FileDataByMiniTypeSource(Fragment fragment, OnFileLoadedListener loadedListener) {
        this.fragment = fragment;
        this.loadedListener = loadedListener;
        LoaderManager loaderManager = fragment.getLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, this);
    }

    /**
     * 构造。新增参数，可以传入 路径。
     * @param activity
     * @param loadedListener
     * @param path
     */
    public FileDataByMiniTypeSource(FragmentActivity activity, OnFileLoadedListener loadedListener, String path) {
        this.activity = activity;
        this.loadedListener = loadedListener;
        this.path = path;
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        if (path==null||path.isEmpty()){
            loaderManager.initLoader(LOADER_ALL, null, this);
        }else {
            //加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_CATEGORY, bundle, this);
        }
    }
    /**
     * 构造。新增参数，可以传入 路径。
     * @param fragment
     * @param loadedListener
     * @param path
     */
    public FileDataByMiniTypeSource(Fragment fragment, OnFileLoadedListener loadedListener, String path) {
        this.fragment = fragment;
        this.loadedListener = loadedListener;
        this.path = path;
        LoaderManager loaderManager = fragment.getLoaderManager();
        if (path==null||path.isEmpty()){
            loaderManager.initLoader(LOADER_ALL, null, this);
        }else {
            //加载指定目录的图片
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            loaderManager.initLoader(LOADER_CATEGORY, bundle, this);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader;

        if (i==LOADER_ALL){
            if (querySuffix.size()!=0){

            }
            getQueryByMiniTypes();
            Log.e(TAG, "onCreateLoader: "+selection );
            Log.e(TAG, "onCreateLoader: "+new  Gson().toJson(selectionArgs) );
            //创建查询。上下文   URI   将要取出的值   选择   选择参数   排序顺序
             cursorLoader = new CursorLoader(activity==null?fragment.getContext():activity, MediaStore.Files.getContentUri("external"), FILE_PROJECTION, selection, selectionArgs, FILE_PROJECTION[5] + " DESC");
            return cursorLoader;
        }else {
            //路径上的条件查询。
            if (querySuffix.size()==0){
                selection= FILE_PROJECTION[0] + " like '%" + bundle.getString("path") + "%'";
            }else {
                String query = getQuery();
                selection= FILE_PROJECTION[0] + " like '%" + bundle.getString("path") + "%' and ( "+query+" ) ";
            }
            cursorLoader = new CursorLoader(activity==null?fragment.getContext():activity, MediaStore.Files.getContentUri("external"), FILE_PROJECTION, selection, selectionArgs, FILE_PROJECTION[5] + " DESC");
            return cursorLoader;
        }

    }

    /**
     * 通过 like 拼接 模糊查询。
     */
    private String getQuery() {
        StringBuffer buffer=new StringBuffer();
        for (String query: querySuffix){
            buffer.append(FILE_PROJECTION[0]+" like '"+"%"+query+"' or ");
        }
        buffer.delete(buffer.length()-3,buffer.length());
        return buffer.toString();
    }

    /**
     * 这个有问题，需要和 selection 一起使用吧。
     * @return
     */
    private void getQueryByMiniTypes(){
        for (String query:querySuffix){
            //说明这个地方为啥要加1 ,因为 最外面 我传的是（.pdf）这个样子的，所以要感觉要拼完整吧，可以试试 直接 用看有数据吗
            String minitype= URLConnection.guessContentTypeFromName("1"+query);
            //如果没有就添加进来。
            if (minitype!=null){
                if (!miniTypes.contains(minitype)){
                    miniTypes.add(minitype);
                }
            }

        }
        StringBuffer buffer=new StringBuffer();
        selectionArgs=new String[miniTypes.size()];
        for (int i=0;i<selectionArgs.length;i++){
            buffer.append(MediaStore.Files.FileColumns.MIME_TYPE+" = ? or ");
            selectionArgs[i]=miniTypes.get(i);
            //buffer.append(FILE_PROJECTION[0]+" like '"+"%"+query+"' or ");
        }
        buffer= buffer.delete(buffer.length()-3,buffer.length());
        selection= buffer.toString();


    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        fileFolders.clear();
        if (cursor!=null){
            ArrayList<FileItem> fileItems = new ArrayList<>();   //所有图片的集合,不分文件夹
            while (cursor.moveToNext()) {
                FileItem fileItem=new FileItem();
                fileItem.path=cursor.getString(cursor.getColumnIndexOrThrow(FILE_PROJECTION[0]));
                fileItem.title=cursor.getString(cursor.getColumnIndexOrThrow(FILE_PROJECTION[1]));
                fileItem.name=cursor.getString(cursor.getColumnIndexOrThrow(FILE_PROJECTION[2]));
                fileItem.size=cursor.getLong(cursor.getColumnIndexOrThrow(FILE_PROJECTION[3]));
                fileItem.mimeType=cursor.getString(cursor.getColumnIndexOrThrow(FILE_PROJECTION[4]));
                fileItem.addTime=cursor.getLong(cursor.getColumnIndexOrThrow(FILE_PROJECTION[5]));
                //转上一层对象。
                //根据父路径分类存放图片
                File file = new File(fileItem.path);
                File imageParentFile = file.getParentFile();
                FileFolder fileFolder = new FileFolder();
                fileFolder.name = imageParentFile.getName();
                fileFolder.path = imageParentFile.getAbsolutePath();

                if (!fileFolders.contains(fileFolder)) {
                    ArrayList<FileItem> files = new ArrayList<>();
                    files.add(fileItem);
                    fileFolder.cover = fileItem;
                    fileFolder.fileItems = files;
                    fileFolders.add(fileFolder);
                } else {
                    fileFolders.get(fileFolders.indexOf(fileFolder)).fileItems.add(fileItem);
                }
                fileItems.add(fileItem);
            }

            //防止没有图片报异常
            if (cursor.getCount() > 0 && fileItems.size()>0) {
                //构造所有图片的集合
                FileFolder allImages = new FileFolder();
                allImages.name = "所有文件";
                allImages.path = "/";
                allImages.cover = fileItems.get(0);
                allImages.fileItems = fileItems;
                fileFolders.add(0, allImages);  //确保第一条是所有图片
            }
            loadedListener.onFileLoaded(fileFolders);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    /** 所有视频加载完成的回调接口 */
    public interface OnFileLoadedListener {
        void onFileLoaded(List<FileFolder> fileFolders);
    }
}
