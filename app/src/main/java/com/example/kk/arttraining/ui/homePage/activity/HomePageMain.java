package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.Authority;
import com.example.kk.arttraining.bean.Course;
import com.example.kk.arttraining.bean.Topic;
import com.example.kk.arttraining.ui.homePage.adapter.HomepageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Activity{
    @InjectView(R.id.lv_homepage)
    ListView lvHomepage;

    private Topic topic = new Topic();
    private List<Topic> listTopic = new ArrayList<Topic>();
    private Authority authority = new Authority();
    private List<Authority> listAuthority = new ArrayList<Authority>();
    private List<Course> listCourse = new ArrayList<Course>();
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
        Authority authority1 = new Authority();
        authority1.setName("haha");
        listAuthority.add(authority1);

        for (int i=0; i <75;i++){
            Course course = new Course();
            course.setName(i+"");
            listCourse.add(course);
        }
    }
}
