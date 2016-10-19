package com.example.kk.arttraining.ui.homePage.function.search;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class SearchTextChangedListener {
    public static void SearchTextListener(final EditText edSearchContent, final Button btSearch){
        edSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edSearchContent.getText().toString())) {
                    btSearch.setVisibility(View.VISIBLE);
                } else {
                    btSearch.setVisibility(View.GONE);
                }
            }
        });
    }
}
