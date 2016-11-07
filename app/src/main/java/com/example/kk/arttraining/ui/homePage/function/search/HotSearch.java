package com.example.kk.arttraining.ui.homePage.function.search;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/7.
 * QQ邮箱:515849594@qq.com
 */
public class HotSearch {
    public static Context context;

    //热门搜索
    public static void GetHotSearch(Context mContext, GridView gvSearchHot) {
        context = mContext;
        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();

        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", i + "");
            mList.add(map);
            SimpleAdapter gv_adapter = new SimpleAdapter(context, mList,
                    R.layout.homepage_province_grid_item, new String[]{"content"},
                    new int[]{R.id.tv_province_hot});
            gvSearchHot.setAdapter(gv_adapter);
            gvSearchHot.setOnItemClickListener(new HotSearchItemClick());
        }
    }

    //热门搜索点击事件
    public static class HotSearchItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(context, position + "");
        }
    }
}
