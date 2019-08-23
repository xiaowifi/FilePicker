package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 多文件预览。
 */
public class DocumentsPreviewActivity extends AppCompatActivity implements  TbsReaderView.ReaderCallback {
    String TAG=this.getClass().getName();
    private ImageView imgBack;
    private ImageView imgLeft;
    private TextView tTitle;
    private ImageView imgRight;
    private RelativeLayout reContent;
    ArrayList<String> paths=new ArrayList<>();
    private TbsReaderView mTbsReaderView;
    int showPage=0;
    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";

    /**
     * 打开当前预览的界面。
     * @param context
     * @param paths
     */
    public static void openactivity(Context context, List<String> paths){
        Intent intent= new Intent(context,DocumentsPreviewActivity.class);
        intent.putExtra("json",new Gson().toJson(paths));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_documents_preview);
        initView();
        try {
           String json= getIntent().getStringExtra("json");
            Log.e(TAG, "onCreate: " +json);
           List<String> paths= new Gson().fromJson(json,new  TypeToken<List<String>>(){}.getType() );
            Log.e(TAG, "onCreate: "+paths.get(0) );
           if (paths!=null){
               this.paths.clear();
               this.paths.addAll(paths);
           }
        }catch (Exception e){
            Log.e(TAG, "onCreate:  e "+e );
            onBackPressed();
        }
        displayFile(0);
    }

    /**
     * 显示。
     * @param page
     */
    private void displayFile(int page) {
        initTabs();
        showPage=page;
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String path=paths.get(page);
        tTitle.setText("预览("+new File(path).getName()+")");
       // Log.e(TAG, "displayFile: "+path );
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile =new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.e("YulanActivity","准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
                Log.e("YulanActivity","创建/TbsReaderTemp失败！！！！！");
            }
        }
       // Log.e("YulanActivity", "displayFile: "+path);
        Bundle bundle = new Bundle();
        bundle.putString("filePath", path);
        bundle.putString("tempPath", tbsReaderTemp);
        String [] paths= path.split("\\.");
        if (paths.length>=2){
            boolean result = mTbsReaderView.preOpen(paths[paths.length-1], false);
            Log.e("YulanActivity","查看文档---"+result);
            if (result) {
                mTbsReaderView.openFile(bundle);
            }else{
                Log.e("YulanActivity", "displayFile: " );
            }
        }
    }

    /**
     *
     */
    private void initTabs() {
        if (mTbsReaderView!=null){
            mTbsReaderView.onStop();
        }
        reContent.removeAllViews();
        mTbsReaderView = new TbsReaderView(this, this);
        reContent.addView(mTbsReaderView,new RelativeLayout.LayoutParams(-1,-1));
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        tTitle = (TextView) findViewById(R.id.t_title);
        imgRight = (ImageView) findViewById(R.id.img_right);
        reContent = (RelativeLayout) findViewById(R.id.re_content);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPage!=0){
                    displayFile(showPage-1);
                }

            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPage!=paths.size()-1){
                    displayFile(showPage+1);
                }
            }
        });
        findViewById(R.id.t_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paths.get(showPage).endsWith(".pdf")){
                    PDFShowActivity.openactivity(view.getContext(),paths.get(showPage));
                }else {
                    Toast.makeText(view.getContext(),"暂支持pdf",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
