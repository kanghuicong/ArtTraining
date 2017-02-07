package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.bean.ChapterBean;
import com.example.kk.arttraining.ui.live.bean.TimeTableBean;
import com.example.kk.arttraining.ui.live.view.PlayCallBackVideo;

import java.util.List;
import java.util.Map;


/**
 * 作者：wschenyongyin on 2017/1/21 15:30
 * 说明:
 */
public class CourseAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<TimeTableBean> timeTableBeanList;
    private Map<String, List<ChapterBean>> mapChapterBeanList;

    ChildHolder childHolder;
    GroupHolder groupHolder;

    ChapterBean chapterBean;

    private String course_name;

    public CourseAdapter(Context context, List<TimeTableBean> timeTableBeanList, Map<String, List<ChapterBean>> mapChapterBeanList) {
        this.context = context;
        this.timeTableBeanList = timeTableBeanList;
        this.mapChapterBeanList = mapChapterBeanList;
    }

    @Override
    public int getGroupCount() {
        return timeTableBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChapterBeanList.get(timeTableBeanList.get(groupPosition).getName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return timeTableBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapChapterBeanList.get(timeTableBeanList.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    //父布局
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_timetable, null);
            groupHolder = new GroupHolder();
            groupHolder.courseName = (TextView) convertView.findViewById(R.id.course_name);
            groupHolder.ivCourseArrow = (ImageView) convertView.findViewById(R.id.iv_course_arrow);
        }
        course_name = timeTableBeanList.get(groupPosition).getName();
        if (course_name != null && !course_name.equals(""))
            groupHolder.courseName.setText(course_name);
        if (!isExpanded) {
            groupHolder.ivCourseArrow.setImageResource(R.mipmap.icon_course_unfold);
        } else {
            groupHolder.ivCourseArrow.setImageResource(R.mipmap.icon_course_fold);
        }

        return convertView;
    }

    //子布局
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_course_chapter, null);
            childHolder = new ChildHolder();
            childHolder.chapterName = (TextView) convertView.findViewById(R.id.chapter_name);
            childHolder.courseStart = (TextView) convertView.findViewById(R.id.course_start);
            childHolder.courseEnd = (TextView) convertView.findViewById(R.id.course_end);
            childHolder.courseLivePrice = (TextView) convertView.findViewById(R.id.course_live_price);
            childHolder.courseRecordPrice = (TextView) convertView.findViewById(R.id.course_record_price);
            childHolder.btnCourseChapter = (Button) convertView.findViewById(R.id.btn_course_chapter);

            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        chapterBean = mapChapterBeanList.get(timeTableBeanList.get(groupPosition).getName()).get(childPosition);
        //课程名称
        if (chapterBean.getChapter_name() != null && !chapterBean.getChapter_name().equals(""))
            childHolder.chapterName.setText(chapterBean.getChapter_name());
        //开始时间
        if (chapterBean.getStart_time() != null && !chapterBean.getStart_time().equals(""))
            childHolder.courseStart.setText(chapterBean.getStart_time());
        //结束时间
        if (chapterBean.getEnd_time() != null && !chapterBean.getEnd_time().equals(""))
            childHolder.courseEnd.setText(chapterBean.getEnd_time());
        childHolder.courseLivePrice.setText("直播￥" + chapterBean.getLive_price());
        childHolder.courseRecordPrice.setText("重播￥" + chapterBean.getRecord_price());
        //课程是否购买
        if (chapterBean.getOrder_status() == 1) {
            if (chapterBean.getLive_status() == 0) {
                childHolder.btnCourseChapter.setEnabled(false);
                childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.grey));
                childHolder.btnCourseChapter.setText("未开播");
            } else {
                childHolder.btnCourseChapter.setEnabled(true);
                childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.blue_overlay));
                childHolder.btnCourseChapter.setText("回放");
                childHolder.btnCourseChapter.setOnClickListener(new Onclik(chapterBean.getRecord_url()));
            }
        } else {
            childHolder.btnCourseChapter.setEnabled(true);
            childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.blue_overlay));
            childHolder.btnCourseChapter.setText("购买");
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private class Onclik implements View.OnClickListener {
        String record_url;

        public Onclik(String record_url) {
            this.record_url = record_url;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PlayCallBackVideo.class);
            intent.putExtra("path", record_url);
            context.startActivity(intent);
        }
    }

    //父类holder
    static class GroupHolder {
        TextView courseName;
        ImageView ivCourseArrow;
    }

    //子类holder
    static class ChildHolder {
        TextView chapterName;
        TextView courseStart;
        TextView courseEnd;
        TextView courseLivePrice;
        TextView courseRecordPrice;
        Button btnCourseChapter;
    }

}
