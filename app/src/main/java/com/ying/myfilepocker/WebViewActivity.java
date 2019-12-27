package com.ying.myfilepocker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lzy.imagepicker.view.X5WebView;

public class WebViewActivity extends AppCompatActivity {

    private X5WebView web_x5;
    private EditText ed_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_web_view);
        ed_info = findViewById(R.id.ed_info);
        ed_info.setText("http://192.168.1.60:2001");
        web_x5 = findViewById(R.id.web_x5);
        web_x5.loadUrl("http://192.168.1.60:2001");
       findViewById(R.id.t_btn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               web_x5.loadUrl(ed_info.getText().toString().trim());
           }
       });
    }
}
