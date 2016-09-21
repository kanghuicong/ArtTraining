package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.bean.Authority;
import com.example.kk.arttraining.bean.Course;
import com.example.kk.arttraining.bean.Topic;
import com.example.kk.arttraining.ui.homePage.adapter.HomepageAdapter;
import com.example.kk.arttraining.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class HomePage_Main extends Activity {
    @InjectView(R.id.lv_homepage)
    ListView lvHomepage;

    private Topic topic = new Topic();
    private List<Topic> listTopic = new ArrayList<Topic>();
    private Authority authority = new Authority();
    private List<Authority> listAuthority = new ArrayList<Authority>();
    private Course course = new Course();
    private List<Course> listCourse = new ArrayList<Course>();
    HomepageAdapter homepageAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);
        ButterKnife.inject(this);
        setData();
        homepageAdapter = new HomepageAdapter(this,listTopic,listAuthority,listCourse);
        lvHomepage.setAdapter(homepageAdapter);
    }

    private void setData() {
        topic.setTopic1("专题1");
        topic.setTopic2("专题2");
        topic.setTopic3("专题3");
        listTopic.add(topic);

        authority.setName("hehe");
        listAuthority.add(authority);
        Authority authority1 = new Authority();
        authority1.setName("haha");
        listAuthority.add(authority1);

        course.setName("111");
        listCourse.add(course);
        Course course1 = new Course();
        course1.setName("222");
        listCourse.add(course1);
    }


}
