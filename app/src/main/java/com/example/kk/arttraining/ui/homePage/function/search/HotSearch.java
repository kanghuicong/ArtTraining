package com.example.kk.arttraining.ui.homePage.function.search;

import android.app.Activity;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.ui.homePage.adapter.SearchHotGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class HotSearch {
    //热门搜索
    public static void GetHotSearch(Activity activity, GridView gridView) {
        List<SearchEntity> listSearch = new ArrayList<SearchEntity>();
        for (int i = 0; i < 5; i++) {
            SearchEntity search = new SearchEntity();
            search.setUser_search(i + "");
            listSearch.add(search);
        }
        SearchHotGridAdapter adapter = new SearchHotGridAdapter(activity, listSearch);
        gridView.setAdapter(adapter);
    }
}
