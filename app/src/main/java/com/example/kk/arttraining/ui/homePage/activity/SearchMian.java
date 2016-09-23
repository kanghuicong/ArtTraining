package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.customview.HideKeyboardActivity;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class SearchMian extends HideKeyboardActivity implements TextWatcher{
    @InjectView(R.id.iv_search_title_back)
    ImageView ivSearchTitleBack;
    @InjectView(R.id.ed_search_content)
    EditText edSearchContent;
    @InjectView(R.id.bt_search)
    Button btSearch;
    @InjectView(R.id.gv_search_hot)
    GridView gvSearchHot;
    @InjectView(R.id.ll_search_hot)
    LinearLayout llSearchHot;
    @InjectView(R.id.lv_search_history)
    ListView lvSearchHistory;
    @InjectView(R.id.ll_search_history)
    LinearLayout llSearchHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);
        edSearchContent.addTextChangedListener(this);
        AutomaticKeyboard.GetClick(edSearchContent);
    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back:
                finish();
                break;
            case R.id.bt_search:
                break;
        }
    }

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
}
