package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AuthorityEntity;
import com.example.kk.arttraining.bean.CourseEntity;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.ui.homePage.adapter.HomepageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Activity {
    @InjectView(R.id.lv_homepage)
    ListView lvHomepage;
    @InjectView(R.id.ll_hompage_search)
    LinearLayout llHompageSearch;

    private TopicEntity topic = new TopicEntity();
    private List<TopicEntity> listTopic = new ArrayList<TopicEntity>();
    private AuthorityEntity authority = new AuthorityEntity();
    private List<AuthorityEntity> listAuthority = new ArrayList<AuthorityEntity>();
    private List<CourseEntity> listCourse = new ArrayList<CourseEntity>();
    HomepageAdapter homepageAdapter;
    View view_header;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        view_header = View.inflate(this, R.layout.homepage_main_header, null);
        ButterKnife.inject(this);
        setData();
        homepageAdapter = new HomepageAdapter(this, lvHomepage, listTopic, listAuthority, listCourse);
        lvHomepage.addHeaderView(view_header);
        lvHomepage.setAdapter(homepageAdapter);
    }

    private void setData() {
        topic.setTopic1("专题1");
        topic.setTopic2("专题2");
        topic.setTopic3("专题3");
        listTopic.add(topic);

        authority.setName("hehe");
        listAuthority.add(authority);
        AuthorityEntity authority1 = new AuthorityEntity();
        authority1.setName("haha");
        listAuthority.add(authority1);

        for (int i = 0; i < 75; i++) {
            CourseEntity course = new CourseEntity();
            course.setName(i + "");
            listCourse.add(course);
        }
    }

    @OnClick(R.id.ll_hompage_search)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_hompage_search:
                Intent intent = new Intent(this,SearchMian.class);
                startActivity(intent);
                break;
        }
    }
}
