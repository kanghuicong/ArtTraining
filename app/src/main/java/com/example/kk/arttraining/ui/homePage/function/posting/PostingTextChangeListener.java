package com.example.kk.arttraining.ui.homePage.function.posting;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class PostingTextChangeListener {
    public static void getTextChangeListener(final Context context, final EditText edPostingContent, final int content_number){
        edPostingContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (edPostingContent.length() > content_number) {
                    UIUtil.ToastshowShort(context, "内容太长，无法发表...");
                }
            }
        });
    }
}
