package com.example.kk.arttraining.ui.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/26.
 * QQ邮箱:515849594@qq.com
 */
public class WebActivity extends Activity {
    WebView webViewShow;

    JavaScriptObject javaScriptObject;
    TokenVerfy tokenVerfy;
    String url;
    String title;
    @InjectView(R.id.ll_vote)
    LinearLayout llVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_vote_activity);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        TitleBack.TitleBackActivity(this, title);

        webViewShow = new WebView(this);
        llVote.addView(webViewShow);

        WebSettings wb = webViewShow.getSettings();
        wb.setDefaultTextEncodingName("utf-8");
        wb.setJavaScriptEnabled(true);

        javaScriptObject = new JavaScriptObject(this, webViewShow);
        webViewShow.addJavascriptInterface(javaScriptObject, "JavaScriptObject");

        if (url.indexOf("yhy_vote") != -1) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                webViewShow.loadUrl(url);
            } else {
                tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        webViewShow.loadUrl(url + "?uid=" + Config.UID + "&utype=" + Config.USER_TYPE);
                    }

                    @Override
                    public void TokenFailure(int flag) {
                        webViewShow.loadUrl(url);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        } else {
            webViewShow.loadUrl(url);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        llVote.removeView(webViewShow);
        webViewShow.removeAllViews();
        webViewShow.destroy();
    }

}
