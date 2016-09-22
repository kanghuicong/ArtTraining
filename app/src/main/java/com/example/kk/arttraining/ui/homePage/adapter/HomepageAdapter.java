package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.bean.Authority;
import com.example.kk.arttraining.bean.Course;
import com.example.kk.arttraining.bean.Topic;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.customview.MyGridView;

import java.util.ArrayList;
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
    ListView listview;
    List<Topic> listTopic;
    List<Authority> listAuthority;
    List<Course> listCourse;
    int coursen_item_number;
    int m = 0;
    int course_position = 0;

    public HomepageAdapter(Activity activity, ListView listview,List<Topic> listTopic, List<Authority> listAuthority, List<Course> listCourse){
        this.activity = activity;
        this.listview = listview;
        this.listAuthority = listAuthority;
        this.listCourse = listCourse;
        this.listTopic = listTopic;
    }

    @Override
    public int getCount() {
        if (listCourse.size()%10 == 0){
            coursen_item_number = listCourse.size()/10;
        }else {
            coursen_item_number = listCourse.size()/10+1;
        }
        return listTopic.size()+listAuthority.size()+coursen_item_number+3;
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

        int viewtype = getItemViewType(position);
        switch (viewtype){
            case TYPE_TITLE:

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
                AuthorityHolder authorityHolder = new AuthorityHolder();
                Authority authority = listAuthority.get(position-listTopic.size()-2);
                if(convertView == null ) {
                    convertView = View.inflate(activity, R.layout.homepage_assessment_authority,null);
                    authorityHolder.iv_header = (ImageView) convertView.findViewById(R.id.iv_authority_header);
                    authorityHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_authority_name);
                    authorityHolder.view_splitter = (View)convertView.findViewById(R.id.view_splitter);
                    convertView.setTag(authorityHolder);
                }else{
                    authorityHolder = (AuthorityHolder) convertView.getTag();
                }
                if (position ==listTopic.size()+listAuthority.size()+2){
                    authorityHolder.view_splitter.setVisibility(View.GONE);
                }
                authorityHolder.tv_name.setText(authority.getName());
                break;

            case TYPE_COURSE:
                CourseHolder courseHolder = new CourseHolder();

                if(convertView == null ) {
                    convertView = View.inflate(activity, R.layout.homepage_masters_course,null);
                    courseHolder.gv_course = (MyGridView)convertView.findViewById(R.id.gv_course);
                    convertView.setTag(courseHolder);
                }else{
                    courseHolder = (CourseHolder) convertView.getTag();
                }

                List<Course> mlistCourse = new ArrayList<Course>();
                for (int i = course_position;i <=coursen_item_number;i++){
                    if (position == listTopic.size()+listAuthority.size()+2+i){
                        mlistCourse.clear();
                        int m ;
                        if (listCourse.size()<= i*10){
                            m = listCourse.size()-10*(i-1);
                        }else {
                            m = 10;
                        }
                        for (int x=0;x<m;x++) {
                            mlistCourse.add(listCourse.get(x+(i-1)*10));
                        }
                        CourseGridAdapter adapter = new CourseGridAdapter(activity,mlistCourse);
                        courseHolder.gv_course.setAdapter(adapter);
                    }
                }
                course_position = position-listTopic.size()-listAuthority.size()-4;
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
        View view_splitter;
    }
    class CourseHolder{
        MyGridView gv_course;
    }
}
