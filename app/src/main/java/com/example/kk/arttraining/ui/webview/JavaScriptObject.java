package com.example.kk.arttraining.ui.webview;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.ui.homePage.bean.WorkComment;
import com.example.kk.arttraining.ui.homePage.function.homepage.ReadTecComment;
import com.example.kk.arttraining.ui.homePage.function.homepage.TokenVerfy;
import com.example.kk.arttraining.ui.homePage.prot.ITokenVerfy;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/12/26.
 * QQ邮箱:515849594@qq.com
 */
public class JavaScriptObject {
    Context mContext;
    WebView webViewShow;
    TokenVerfy tokenVerfy;
    String url;
    boolean Flag = false;


    public JavaScriptObject(Context mContext, WebView webViewShow) {
        this.mContext = mContext;
        this.webViewShow = webViewShow;
//        this.url = url;
    }

    @JavascriptInterface
    public void VoteClick() {

        if (!Flag) {
            if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                TokenVerfy.Login(mContext, 2);
            } else {
                tokenVerfy = new TokenVerfy(new ITokenVerfy() {
                    @Override
                    public void TokenSuccess() {
                        webViewShow.post(new Runnable() {
                            @Override
                            public void run() {
                                webViewShow.loadUrl("http://192.168.188.152:8020/vote/yhy_vote.html" + "?uid=" + Config.UID + "&utype=" + Config.USER_TYPE);
                                webViewShow.loadUrl("javascript:VoteSuccess()");
                                Flag = true;
                            }
                        });
                    }

                    @Override
                    public void TokenFailure(int flag) {
                        TokenVerfy.Login(mContext, flag);
                    }
                });
                tokenVerfy.getTokenVerfy();
            }
        } else {
            webViewShow.post(new Runnable() {
                @Override
                public void run() {
                    webViewShow.loadUrl("javascript:VoteSuccess()");
                }
            });
        }
    }

    @JavascriptInterface
    public void VoteOnClick(int uid) {
        UIUtil.ToastshowLong(mContext, uid+"--");
    }


}
