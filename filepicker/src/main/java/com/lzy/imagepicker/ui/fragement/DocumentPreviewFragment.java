package com.lzy.imagepicker.ui.fragement;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.MyFilePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.ui.PDFShowActivity;
import com.lzy.imagepicker.ui.X5Activity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * 文件预览。
 */
public class DocumentPreviewFragment extends Fragment implements View.OnClickListener, TbsReaderView.ReaderCallback {
    private TbsReaderView mTbsReaderView;
    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    private String downPath=Environment.getExternalStorageDirectory()+"/yibaitong.down.file";

    private String path="http://192.168.1.51:10010/renqin/uploadfile/file/学习平台使用及常见疑问指导.pptx";
    private RelativeLayout reContent;
    private TextView tPlay;
    private RelativeLayout relatErr;
    private TextView tTitle;
    private TextView tErr;
    private TextView tBtn;
    private String fileName="";
    public DocumentPreviewFragment setPath(String path) {
        this.path = path;
        return this;
    }

    public void setChangePath(String path) {
        this.path = path;
        displayFile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_decument_preview, container, false);
        initView(view);
        displayFile();
        return view;
    }

    /**
     * 显示。
     */
    private void displayFile() {
        initTabs();
        relatErr.setVisibility(View.GONE);
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.e("YulanActivity", "准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.e("YulanActivity", "创建/TbsReaderTemp失败！！！！！");
            }
        }
        //设置是否打开。
        tPlay.setVisibility(path.endsWith(".pdf")?View.VISIBLE:View.GONE);

        Bundle bundle = new Bundle();
        bundle.putString("filePath", path);
        bundle.putString("tempPath", tbsReaderTemp);
        String[] paths = path.split("\\.");
        if (path.startsWith("http")){
            relatErr.setVisibility(View.VISIBLE);
            tTitle.setText(""+paths[paths.length - 1]);
            tErr.setText("当前文件为网络资源，请先下载后预览播放");
            tBtn.setText("下载");
            return;
        }
        if (paths.length >= 2) {
            fileName=paths[paths.length - 1];
            boolean result = mTbsReaderView.preOpen(paths[paths.length - 1], false);
            Log.e("YulanActivity", "查看文档---" + result);
            if (result) {
                mTbsReaderView.openFile(bundle);
            } else {
                relatErr.setVisibility(View.VISIBLE);
                tTitle.setText(""+paths[paths.length - 1]);
                if (path.startsWith("http")){
                    tErr.setText("当前文件为网络资源，请先下载后预览播放");
                    tBtn.setText("下载");
                }else if (!MyFilePicker.init().isX5down()){
                    tErr.setText("系统检测内核加载失败，请点击 手动下载内核 ，打开界面后，选择 安装线上内核");
                    tBtn.setText("手动下载内核");
                }else {
                    tErr.setText("未知错误，当前文件无法被打开，请使用其他应用打开");
                    tBtn.setText("其他应用打开");
                }
            }
        }
    }

    /**
     *
     */
    private void initTabs() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
        reContent.removeAllViews();
        mTbsReaderView = new TbsReaderView(getContext(), this);
        reContent.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
    }

    /**
     * 采用第3方打开文件。
     * @param file
     */
    private void show(String file) {
        try {
            Uri uri = null;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName()+".provider",new File(file));
            } else {
                uri = Uri.fromFile(new File(file));
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, URLConnection.guessContentTypeFromName(file));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }

    @Override
    public void onClick(View view) {
        if (view==tBtn){
            if (tBtn.getText().toString().equals("其他应用打开")){
                show(path);
            }else if (tBtn.getText().toString().equals("手动下载内核")){
                startActivity(new Intent(getContext(), X5Activity.class));
            }else if (tBtn.getText().toString().equals("下载")){
                //开始下载。
                toDown();
            }else if (tBtn.getText().toString().startsWith("下载中")){
                tBtn.setText("继续下载");
                //取消下载。
                OkGo.getInstance().cancelTag(path);
            }else if (tBtn.getText().toString().equals("继续下载")){
                toDown();
            }
        }else if (view==tPlay){
            if (path.endsWith(".pdf")){
                PDFShowActivity.openactivity(getContext(),path);
            }else {
                Toast.makeText(getContext(),"当前文件不支持播放",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 网络请求下载文件。
     */
    private void toDown() {
        OkGo.<File>get(path)
                .tag(path)
                .execute(new FileCallback(downPath,fileName){
                    @Override
                    public void onSuccess(Response<File> response) {
                        Toast.makeText(getContext(),"下载完成",Toast.LENGTH_SHORT).show();
                        path=response.body().getPath();
                        displayFile();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        DecimalFormat df = new DecimalFormat("#.00");
                        tBtn.setText("下载中("+df.format(progress.fraction*100f)+"% "+(progress.speed/1024)+"kb/s )");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        tErr.setText("下载失败，请稍后重试");
                        tBtn.setText("下载");
                    }
                });
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    /**
     *
     * @param view
     */
    private void initView(View view) {
        relatErr = view. findViewById(R.id.relat_err);
        tTitle =  view.findViewById(R.id.t_title);
        tErr =  view.findViewById(R.id.t_err);
        tBtn =  view.findViewById(R.id.t_btn);
        reContent = view.findViewById(R.id.re_content);
        tPlay = view.findViewById(R.id.t_play);
        tPlay.setOnClickListener(this);
        tBtn.setOnClickListener(this);
    }
}
