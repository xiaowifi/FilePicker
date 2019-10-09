package com.ying.myfilepocker;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DialogFragmentActivity extends AppCompatActivity {

    Handler handler=new Handler();

    String TAG="DialogFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dialog_fragment);
        findViewById(R.id.t_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog dialog=new LoadingDialog();
                dialog.show(getSupportFragmentManager(),LoadingDialog.class.getName());
                handler.postDelayed(runnable,10000);
            }
        });
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
           Fragment fragment= getSupportFragmentManager().findFragmentByTag(LoadingDialog.class.getName());
            Log.e(TAG, "run: "+(fragment==null) );
        }
    };
}
