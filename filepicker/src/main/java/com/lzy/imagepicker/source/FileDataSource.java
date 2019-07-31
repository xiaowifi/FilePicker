package com.lzy.imagepicker.source;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.bean.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * 传入特定查询数据。文件。
 */
public class FileDataSource implements LoaderManager.LoaderCallbacks<Cursor> {
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
    AppCompatActivity activity;
    OnFileLoadedListener loadedListener;
    String path;

    /**
     * 构造函数，后缀名查询。
     * @param querySuffix
     * @param activity
     * @param loadedListener
     * @param path
     */
    public FileDataSource(ArrayList<String> querySuffix, AppCompatActivity activity, OnFileLoadedListener loadedListener, String path) {
        this.querySuffix = querySuffix;
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

    private ArrayList<FileFolder> fileFolders = new ArrayList<>();   //所有的文件文件夹

    /**
     * 构造 查询全部。
     * @param activity
     * @param loadedListener
     */
    public FileDataSource(AppCompatActivity activity, OnFileLoadedListener loadedListener) {
        this.activity = activity;
        this.loadedListener = loadedListener;
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, this);
    }

    /**
     * 构造。新增参数，可以传入 路径。
     * @param activity
     * @param loadedListener
     * @param path
     */
    public FileDataSource(AppCompatActivity activity, OnFileLoadedListener loadedListener, String path) {
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";

        CursorLoader cursorLoader;
        if (i==LOADER_ALL){
            if (querySuffix.size()!=0){
                selection=getQuery();
            }
            //创建查询。上下文   URI   将要取出的值   选择   选择参数   排序顺序
             cursorLoader = new CursorLoader(activity, MediaStore.Files.getContentUri("external"), FILE_PROJECTION, selection, null, FILE_PROJECTION[5] + " DESC");
            return cursorLoader;
        }else {
            //路径上的条件查询。
            if (querySuffix.size()==0){
                selection= FILE_PROJECTION[0] + " like '%" + bundle.getString("path") + "%'";
            }else {
                String query = getQuery();
                Log.e(TAG, "onCreateLoader: "+ query);
                selection= FILE_PROJECTION[0] + " like '%" + bundle.getString("path") + "%' and ( "+query+" ) ";
            }
            cursorLoader = new CursorLoader(activity, MediaStore.Files.getContentUri("external"), FILE_PROJECTION, selection, null, FILE_PROJECTION[5] + " DESC");
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
