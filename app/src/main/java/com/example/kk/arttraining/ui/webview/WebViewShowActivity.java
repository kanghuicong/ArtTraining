package com.example.kk.arttraining.ui.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/17 16:47
 * 说明:用显示web页面
 */
public class WebViewShowActivity extends BaseActivity {
    @InjectView(R.id.web_view_show)
    WebView webViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_show_activity);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        TitleBack.TitleBackActivity(this, title);
        WebSettings wb = webViewShow.getSettings();
        wb.setDefaultTextEncodingName("utf-8");
        webViewShow.loadUrl(url);
    }

    @Override
    public void onClick(View v) {

    }
}
