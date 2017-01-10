package com.example.kk.arttraining.ui.webview;

import android.app.Activity;
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
    Activity mContext;
    WebView webViewShow;
    TokenVerfy tokenVerfy;
    String url;
    boolean Flag = false;


    public JavaScriptObject(Activity mContext, WebView webViewShow) {
        this.mContext = mContext;
        this.webViewShow = webViewShow;
    }

    @JavascriptInterface
    public void VoteClick() {
        TokenVerfy.Login(mContext, 2);
        mContext.finish();
    }

    @JavascriptInterface
    public void VoteRepeat() {
        UIUtil.ToastshowShort(mContext,"小主,一天只能投一票哟!");
    }


}
