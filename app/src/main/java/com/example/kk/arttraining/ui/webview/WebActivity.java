package com.example.kk.arttraining.ui.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/26.
 * QQ邮箱:515849594@qq.com
 */
public class WebActivity extends Activity {
    @InjectView(R.id.web_view_show)
    WebView webViewShow;

    JavaScriptObject javaScriptObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_show_activity);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        TitleBack.TitleBackActivity(this, title);

        WebSettings wb = webViewShow.getSettings();
        wb.setDefaultTextEncodingName("utf-8");
        wb.setJavaScriptEnabled(true);

        javaScriptObject = new JavaScriptObject(this, webViewShow);
        webViewShow.addJavascriptInterface(javaScriptObject,"JavaScriptObject");

        UIUtil.showLog("webViewShow","http://192.168.188.152:8020/vote/yhy_vote.html" + "?uid=" + Config.UID + "&utype=" + Config.USER_TYPE);
        webViewShow.loadUrl(url + "?uid=" + Config.UID + "&utype=" + Config.USER_TYPE);

    }

}
