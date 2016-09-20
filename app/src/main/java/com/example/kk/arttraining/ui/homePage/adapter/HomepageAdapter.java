package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.bean.Authority;
import com.example.kk.arttraining.bean.Course;
import com.example.kk.arttraining.bean.Topic;
import com.example.kk.arttraining.R;

import java.util.List;

/**
 * Created by kanghuicong on 2016/9/20.
 * QQ邮箱:515849594@qq.com
 */
public class HomepageAdapter extends BaseAdapter{
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_TOPIC = 1;
    public static final int TYPE_AUTHORITY = 2;
    public static final int TYPE_COURSE = 3;
    Activity activity;
    List<Topic> listTopic;
    List<Authority> listAuthority;
    List<Course> listCourse;


    public HomepageAdapter(Activity activity,List<Topic> listTopic,List<Authority> listAuthority,List<Course> listCourse){
        this.activity = activity;
        this.listAuthority = listAuthority;
        this.listCourse = listCourse;
        this.listTopic = listTopic;
    }

    @Override
    public int getCount() {
        return listTopic.size()+listAuthority.size()+listCourse.size()+3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        int ret = 0;
        if (position == 0 || position == listTopic.size()+1 || position == listTopic.size()+listAuthority.size()+2){
            ret = TYPE_TITLE ;
        } else if (position <= listTopic.size()) {
            ret = TYPE_TOPIC ;
        } else if (position <= listTopic.size()+listAuthority.size()+1) {
            ret = TYPE_AUTHORITY;
        } else if (position <= listTopic.size()+listAuthority.size()+listCourse.size()+2) {
            ret = TYPE_COURSE ;
        }
        return ret;
    }
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("position1",position+"-----");
        int viewtype = getItemViewType(position);
        switch (viewtype){
            case TYPE_TITLE:
                Log.i("position2",position+"-----");
                convertView = View.inflate(activity, R.layout.homepage_title,null);
                TextView tv_title = (TextView)convertView.findViewById(R.id.tv_homepage_title);
                if (position == 0){
                    tv_title.setText("精选专题");
                }else if (position == listTopic.size()+1){
                    tv_title.setText("测评权威");
                }else if(position == listTopic.size()+listAuthority.size()+2){
                    tv_title.setText("名师课程");
                }
                break;
            case TYPE_TOPIC:
                Log.i("position3",position+"-----");
                TopicHolder topicHolder = new TopicHolder();
                Topic topic = listTopic.get(position-1);
                if(convertView == null ) {
                    convertView = View.inflate(activity, R.layout.homepage_selected_topics,null);
                    topicHolder.tv_topic1 = (TextView)convertView.findViewById(R.id.tv_homepage_topic1);
                    topicHolder.tv_topic2 = (TextView)convertView.findViewById(R.id.tv_homepage_topic2);
                    topicHolder.tv_topic3 = (TextView)convertView.findViewById(R.id.tv_homepage_topic3);
                    convertView.setTag(topicHolder);
                }else{
                    topicHolder = (TopicHolder) convertView.getTag();
                }
                topicHolder.tv_topic1.setText(topic.getTopic1());
                topicHolder.tv_topic2.setText(topic.getTopic2());
                topicHolder.tv_topic3.setText(topic.getTopic3());
                break;

            case TYPE_AUTHORITY:
                Log.i("position4",position+"-----");
                AuthorityHolder authorityHolder = new AuthorityHolder();
                Authority authority = listAuthority.get(0);
                if(convertView == null ) {
                    convertView = View.inflate(activity, R.layout.homepage_assessment_authority,null);
                    authorityHolder.iv_header = (ImageView) convertView.findViewById(R.id.iv_authority_header);
                    authorityHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_authority_name);
                    convertView.setTag(authorityHolder);
                }else{
                    authorityHolder = (AuthorityHolder) convertView.getTag();
                }
                authorityHolder.tv_name.setText(authority.getName());
                break;

            case TYPE_COURSE:
                Log.i("position5",position+"-----");
                CourseHolder courseHolder = new CourseHolder();
                Course course = listCourse.get(0);
                if(convertView == null ) {
                    convertView = View.inflate(activity, R.layout.homepage_masters_course,null);
                    courseHolder.tv_course = (TextView) convertView.findViewById(R.id.course);
                    convertView.setTag(courseHolder);
                }else{
                    courseHolder = (CourseHolder) convertView.getTag();
                }
                courseHolder.tv_course.setText(course.getName());
                break;
        }
        return convertView;
    }

    class TopicHolder{
        TextView tv_topic1;
        TextView tv_topic2;
        TextView tv_topic3;
    }
    class AuthorityHolder{
        ImageView iv_header;
        TextView tv_name;
    }
    class CourseHolder{
        TextView tv_course;
    }
}
