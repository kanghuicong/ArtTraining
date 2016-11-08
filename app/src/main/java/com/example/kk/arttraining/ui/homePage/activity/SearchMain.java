package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.sqlite.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.function.search.DoSearch;
import com.example.kk.arttraining.ui.homePage.function.search.HistorySearch;
import com.example.kk.arttraining.ui.homePage.function.search.HotSearch;
import com.example.kk.arttraining.ui.homePage.function.search.SearchTextChangedListener;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class SearchMain extends HideKeyboardActivity {
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
    @InjectView(R.id.ll_search_clear_history)
    LinearLayout llSearchClearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        SearchTextChangedListener.SearchTextListener(edSearchContent, btSearch);//监听搜索内容变化

        HotSearch.GetHotSearch(this,gvSearchHot);//热门搜索

        HistorySearch.GetHistorySearch(this,lvSearchHistory,llSearchClearHistory);//历史搜索

        DoSearch.KeySearch(this, edSearchContent);//修改键盘搜索键及该搜索键点击事件

        AutomaticKeyboard.GetClick(this, edSearchContent);//自动弹出键盘

    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back://返回
                finish();
                break;
            case R.id.bt_search://搜索按钮
                DoSearch.doSearch(SearchMain.this, edSearchContent);
                break;
        }
    }
}
