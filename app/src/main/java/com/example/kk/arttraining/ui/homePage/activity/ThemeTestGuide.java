package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.InnerView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.function.homepage.Shuffling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/1.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTestGuide extends Activity {
    @InjectView(R.id.vp_guide_img)
    InnerView vpGuideImg;
    @InjectView(R.id.lv_test_guide)
    MyListView lvTestGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_test_guide);
        ButterKnife.inject(this);
        Shuffling.initShuffling(vpGuideImg, ThemeTestGuide.this);//轮播


        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", "指导"+i);
            map.put("like", i + "");
            map.put("comment", i + "");
            map.put("browse", i + "");
            mList.add(map);
            SimpleAdapter gv_adapter = new SimpleAdapter(this, mList,
                    R.layout.homepage_test_guide_item, new String[]{"title","like","comment","browse"},
                    new int[]{R.id.tv_guide_title,R.id.tv_guide_like,R.id.tv_guide_comment,R.id.tv_guide_browse});
            lvTestGuide.setAdapter(gv_adapter);
            lvTestGuide.setOnItemClickListener(new TestGuideItemClick());
        }

    }

    private class TestGuideItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ThemeTestGuide.this, ThemeTestGuideContent.class);
            startActivity(intent);
        }
    }
}
