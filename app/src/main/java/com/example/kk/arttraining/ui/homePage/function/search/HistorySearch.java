package com.example.kk.arttraining.ui.homePage.function.search;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.sqlite.dao.SearchDao;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/7.
 * QQ邮箱:515849594@qq.com
 */
public class HistorySearch {

    public static SimpleAdapter historyAdapter;
    public static List<Map<String, String>> historyList = new ArrayList<Map<String, String>>();
    public static List<SearchEntity> search_list;
    public static Context context;
    //历史搜索
    public static void GetHistorySearch(Context mContext, ListView lvSearchHistory, LinearLayout llSearchClearHistory) {
        context = mContext;
        SearchDao dao = new SearchDao(context);
        search_list = dao.findData(Config.User_Id);
        if (search_list.size() != 0) {
            for (int i = 0; i < search_list.size(); i++) {
                SearchEntity modler = search_list.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("content", modler.getUser_search());
                historyList.add(map);
            }
            historyAdapter = new SimpleAdapter(context, historyList,
                    R.layout.homepage_search_history_listview, new String[]{"content"},
                    new int[]{R.id.tv_search_history});
            lvSearchHistory.setAdapter(historyAdapter);
            lvSearchHistory.setOnItemClickListener(new HistorySearchItemClickListener());
        }

        llSearchClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDao dao = new SearchDao(context);
                dao.deleteData(Config.User_Id);
                if (historyList.size() > 0) {
                    historyList.clear();
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    //历史搜索点击事件
    public static class HistorySearchItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchEntity molder = search_list.get(position);
            UIUtil.ToastshowShort(context, molder.getUser_search());
        }
    }
}
