package com.example.kk.arttraining.ui.homePage.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.example.kk.arttraining.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.adapter.SearchHotGridAdapter;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
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
public class SearchMain extends HideKeyboardActivity implements TextWatcher {
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
    @InjectView(R.id.spinner_search)
    Spinner spinnerSearch;

    String search_content;
    SimpleAdapter adapter;
    List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();
    List<SearchEntity> search_list = new ArrayList<SearchEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);
        edSearchContent.addTextChangedListener(this);
        GetHotSearch();
        GetHistorySearch();
        KeySearch();
        AutomaticKeyboard.GetClick(this, edSearchContent);

    }

    private void KeySearch() {
        edSearchContent.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    // ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    // getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    KeyBoardUtils.closeKeybord(edSearchContent,SearchMain.this);
                    doSearch();
                }
                return false;
            }
        });
    }

    private void GetHotSearch() {
        List<SearchEntity> listSearch = new ArrayList<SearchEntity>();
        for (int i = 0; i < 5; i++) {
            SearchEntity search = new SearchEntity();
            search.setUser_search(i + "");
            listSearch.add(search);
        }
        SearchHotGridAdapter adapter = new SearchHotGridAdapter(this, listSearch);
        gvSearchHot.setAdapter(adapter);
    }

    private void GetHistorySearch() {
        SearchDao dao = new SearchDao(this);
        search_list = dao.findData(Config.User_Id);
        if (search_list.size() != 0) {
            for (int i = 0; i < search_list.size(); i++) {
                SearchEntity modler = search_list.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("content", modler.getUser_search());
                mlist.add(map);
            }
            adapter = new SimpleAdapter(this, mlist,
                    R.layout.homepage_search_history_listview, new String[]{"content"},
                    new int[]{R.id.tv_search_history});
            lvSearchHistory.setAdapter(adapter);
            lvSearchHistory.setOnItemClickListener(new HistorySearchItemLongClickListener());
        }
    }

    private void doSearch() {
        search_content = edSearchContent.getText().toString();
        if (Config.User_Id != null) {
            if (search_content.equals("")) {
                UIUtil.ToastshowShort(this, "请输入搜索内容");
            } else {
                SearchDao dao = new SearchDao(this);
                int result = dao.contrastData(Config.User_Id, search_content);
                if (result == 0) {
                    dao.addData(Config.User_Id, search_content);
                }
                UIUtil.ToastshowShort(this, search_content);
                edSearchContent.setText("");
            }
        } else {
            UIUtil.ToastshowShort(this, "请先登录");
        }
    }

    //历史搜索点击事件
    public class HistorySearchItemLongClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchEntity modler = search_list.get(position);
            UIUtil.showLog("SearchEntity", modler.getUser_search());
        }
    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search, R.id.ll_search_clear_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back:
                finish();
                break;
            case R.id.bt_search:
                doSearch();
                break;
            case R.id.ll_search_clear_history:
                SearchDao dao = new SearchDao(this);
                dao.deleteData(Config.User_Id);
                if (mlist.size() > 0) {
                    mlist.clear();
                    adapter.notifyDataSetChanged();
                }
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
