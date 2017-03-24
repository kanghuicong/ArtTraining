package com.example.kk.arttraining.ui.live.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.live.bean.ChapterBean;
import com.example.kk.arttraining.ui.live.bean.TimeTableBean;
import com.example.kk.arttraining.ui.live.view.LivePayActivity;
import com.example.kk.arttraining.ui.live.view.PlayCallBackVideo;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * 作者：wschenyongyin on 2017/1/21 15:30
 * 说明:课程列表
 */
public class CourseAdapter extends BaseExpandableListAdapter {


    private Context context;
    private Activity activity;
    private List<TimeTableBean> timeTableBeanList;
    private Map<String, List<ChapterBean>> mapChapterBeanList;

    ChildHolder childHolder;
    GroupHolder groupHolder;

    ChapterBean chapterBean;

    private String course_name;

    int room_id;
    double chapterPrice = 0.00;

    public CourseAdapter(Context context, List<TimeTableBean> timeTableBeanList, Map<String, List<ChapterBean>> mapChapterBeanList,int room_id) {
        this.context = context;
        this.timeTableBeanList = timeTableBeanList;
        this.mapChapterBeanList = mapChapterBeanList;
        this.room_id = room_id;
        activity = (Activity) context;
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
            childHolder.courseNum = (TextView) convertView.findViewById(R.id.course_num);
            childHolder.courseLivePrice = (TextView) convertView.findViewById(R.id.course_live_price);
            childHolder.courseLivePriceTitle = (TextView) convertView.findViewById(R.id.course_live_price_title);
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
            childHolder.courseEnd.setText(chapterBean.getEnd_time().substring(11,chapterBean.getEnd_time().length()));
        //购买人数
        childHolder.courseNum.setText(chapterBean.getBuy_number()+"人");
        //价钱
        String buy_type;
        switch (chapterBean.getLive_status()){
            case 2:
                childHolder.courseLivePriceTitle.setText("重播￥");
                childHolder.courseLivePrice.setText(StringUtils.getDouble(chapterBean.getRecord_price())+"");
                buy_type = "record";
                chapterPrice = StringUtils.getDouble(chapterBean.getRecord_price());
                break;
            default:
                childHolder.courseLivePriceTitle.setText("直播￥");
                childHolder.courseLivePrice.setText(StringUtils.getDouble(chapterBean.getLive_price())+"");
                buy_type = "live";
                chapterPrice = StringUtils.getDouble(chapterBean.getLive_price());
                break;
        }

        //课程是否购买
        switch (chapterBean.getIs_free()) {
            case 0://收费
                switch (chapterBean.getOrder_status()) {
                    case 0://未购买
                        childHolder.btnCourseChapter.setEnabled(true);
                        childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.blue_overlay));
                        childHolder.btnCourseChapter.setText("购买");
                        childHolder.btnCourseChapter.setOnClickListener(new clickBuy(chapterBean.getChapter_name(),chapterPrice,buy_type,chapterBean.getChapter_id()));
                        break;
                    case 1://已购买
                        getChapter();
                }
                break;
            case 1://免费:
                getChapter();
                break;
        }
        return convertView;
    }

    public void getChapter() {
        switch (chapterBean.getLive_status()) {
            case 0://未开播
                childHolder.btnCourseChapter.setEnabled(false);
                childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.grey));
                childHolder.btnCourseChapter.setText("未开播");
                break;
            case 1://直播中
                childHolder.btnCourseChapter.setEnabled(true);
                childHolder.btnCourseChapter.setText("观看");
                childHolder.btnCourseChapter.setOnClickListener(new clickSeeNow());
                break;
            case 2://直播结束
                childHolder.btnCourseChapter.setEnabled(true);
                childHolder.btnCourseChapter.setBackgroundColor(context.getResources().getColor(R.color.blue_overlay));
                childHolder.btnCourseChapter.setText("回放");
                childHolder.btnCourseChapter.setOnClickListener(new clickSeeBack(chapterBean.getRecord_url()));
                break;
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class clickSeeBack implements View.OnClickListener {
        String record_url;

        public clickSeeBack(String record_url) {
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
        TextView courseNum;
        TextView courseLivePrice;
        TextView courseLivePriceTitle;
        Button btnCourseChapter;
    }

    private class clickSeeNow implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            activity.finish();
        }
    }

    private class clickBuy implements View.OnClickListener {
        CommitOrderBean commitOrderBean;
        String buy_type;
        int chapter_id;
        String chapter_name;
        double live_price;
        public clickBuy(String chapter_name,double live_price, String buy_type,int chapter_id) {
            this.chapter_name = chapter_name;
            this.live_price = live_price;
            this.buy_type = buy_type;
            this.chapter_id = chapter_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, LivePayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("chapter_name", chapter_name);
            bundle.putDouble("live_price", live_price);
            bundle.putString("buy_type", buy_type);
            bundle.putInt("room_id",room_id);
            bundle.putInt("chapter_id",chapter_id);
            intent.putExtras(bundle);
            intent.putExtra("liveType", "liveCourse");
            activity.startActivity(intent);
        }
    }
}
