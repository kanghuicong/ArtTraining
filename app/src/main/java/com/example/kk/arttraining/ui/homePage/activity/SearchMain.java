package com.example.kk.arttraining.ui.homePage.activity;

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
import com.example.kk.arttraining.customview.HideKeyboardActivity;
import com.example.kk.arttraining.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.function.search.DoSearch;
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
public class SearchMain extends HideKeyboardActivity{
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
    static List<SearchEntity> search_list = new ArrayList<SearchEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);

        SearchTextChangedListener.SearchTextListener(edSearchContent,btSearch);//监听搜索内容变化

        HotSearch.GetHotSearch(this,gvSearchHot);//热门搜索

        GetHistorySearch();//历史搜索

        DoSearch.KeySearch(SearchMain.this,edSearchContent);//修改键盘搜索键及该搜索键点击事件

        AutomaticKeyboard.GetClick(this, edSearchContent);//自动弹出键盘

    }

    //历史搜索
    public void GetHistorySearch() {
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
            lvSearchHistory.setOnItemClickListener(new HistorySearchItemClickListener());
        }
    }

    //历史搜索点击事件
    public class HistorySearchItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchEntity molder = search_list.get(position);
            UIUtil.ToastshowShort(SearchMain.this,molder.getUser_search());
        }
    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search, R.id.ll_search_clear_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back://返回
                finish();
                break;
            case R.id.bt_search://搜索按钮
                DoSearch.doSearch(SearchMain.this,edSearchContent);
                break;
            case R.id.ll_search_clear_history://清除历史记录
                SearchDao dao = new SearchDao(this);
                dao.deleteData(Config.User_Id);
                if (mlist.size() > 0) {
                    mlist.clear();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
