package com.example.kk.arttraining.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.MyWebView;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/26.
 * QQ邮箱:515849594@qq.com
 */
public class WebActivity extends Activity {
    WebView webViewShow;
    WebSettings wb;

    JavaScriptObject javaScriptObject;
    TokenVerfy tokenVerfy;
    String url;
    String title;
    String type = "url";
    int info_id;
    LoadingDialog loadingDialog;

    @InjectView(R.id.ll_vote)
    LinearLayout llVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setConfigCallback((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        setContentView(R.layout.webview_vote_activity);
        ButterKnife.inject(this);
        loadingDialog = new LoadingDialog(WebActivity.this);
        loadingDialog.setTitle("页面加载中，请稍后...");
        loadingDialog.show();

        init();
    }

    public void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        if (intent.getStringExtra("info_id") != null) {
            //info_id目前只用于"info_id"
            info_id = Integer.valueOf(intent.getStringExtra("info_id"));
            type = "info";
        }

        TitleBack.TitleBackActivity(this, title);

        webViewShow = new WebView(getApplicationContext());
        llVote.addView(webViewShow);

        wb = webViewShow.getSettings();
        wb.setDefaultTextEncodingName("utf-8");
        wb.setJavaScriptEnabled(true);
        //去掉缩放按钮
        wb.setBuiltInZoomControls(true);
        wb.setDisplayZoomControls(false);

        webViewShow.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view,String url)
            {
                loadingDialog.dismiss();
            }
        });

        UIUtil.showLog("yhy_vote",url);
        if (url.indexOf("yhy_vote") != -1) {
            javaScriptObject = new JavaScriptObject(this, webViewShow);
            webViewShow.addJavascriptInterface(javaScriptObject, "JavaScriptObject");

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
        } else if (type.equals("info")) {
            webViewShow.loadUrl(url + "?info_id=" + info_id);
        } else {
            webViewShow.loadUrl(url);
        }



    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (llVote != null) {
                setConfigCallback(null);
                llVote.removeView(webViewShow);
                webViewShow.removeAllViews();
                webViewShow.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Override
//    protected void onStop() {
//        super.onStop();
//        wb.setJavaScriptEnabled(false);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        wb.setJavaScriptEnabled(true);
//    }

    public void setConfigCallback(WindowManager windowManager) {
        try {
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);

            if (null == configCallback) {
                return;
            }


            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e) {
        }
    }

}
