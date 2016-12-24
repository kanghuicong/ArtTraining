package com.example.kk.arttraining.ui.course.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.course.bean.ChapterBean;
import com.example.kk.arttraining.ui.course.bean.LessonBean;
import com.example.kk.arttraining.utils.UIUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/12/24 11:32
 * 说明:
 */
public class ArtCourseVideoListAdapter extends BaseExpandableListAdapter {

    private Map<String, List<LessonBean>> dataMap;
    private List<String> parentData;
    Context context;
    private LinearLayout ll_chapter;
    private LinearLayout ll_lesson;
    private TextView tv_chapter;
    private TextView tv_lesson;
    private ChapterHolder chapterHolder;
    private LessonHolder lessonHolder;

    List<ChapterBean> chapterBeanList;

    public ArtCourseVideoListAdapter(Context context, List<String> parentData,List<ChapterBean> chapterBeanList, Map<String, List<LessonBean>> dataMap) {
        this.dataMap = dataMap;
        this.parentData = parentData;
        this.context = context;
        this.chapterBeanList=chapterBeanList;

//        String key=parentData.get(2);
//        List<LessonBean> lessonBeanList = dataMap.get(key);
//        UIUtil.showLog("getChildrenCount------>",lessonBeanList.size()+"");

    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return parentData.size();
    }

    //  获得子项的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        String key=parentData.get(groupPosition);
        List<LessonBean> lessonBeanList = dataMap.get(key);
        UIUtil.showLog("getChildrenCount------>",lessonBeanList.size()+"");
        return lessonBeanList.size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return chapterBeanList.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataMap.get(parentData.get(groupPosition)).get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            chapterHolder = new ChapterHolder();
            convertView = View.inflate(context, R.layout.item_course_art_chapter, null);
            chapterHolder.tv_chapter = (TextView) convertView.findViewById(R.id.chapter_title);
            chapterHolder.ll_chapter = (LinearLayout) convertView.findViewById(R.id.ll_chapter);
            convertView.setTag(chapterHolder);
        } else {
            chapterHolder = (ChapterHolder) convertView.getTag();
        }
        chapterHolder.tv_chapter.setText(parentData.get(groupPosition));
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            lessonHolder = new LessonHolder();
            convertView = View.inflate(context, R.layout.item_course_art_lesson, null);
            lessonHolder.tv_lesson = (TextView) convertView.findViewById(R.id.lesson_title);
            lessonHolder.ll_lesson = (LinearLayout) convertView.findViewById(R.id.ll_art_lesson);
            convertView.setTag(lessonHolder);
        } else {
            lessonHolder = (LessonHolder) convertView.getTag();
        }
        lessonHolder.tv_lesson.setText(dataMap.get(parentData.get(groupPosition)).get(childPosition).getName());
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    //章节
    class ChapterHolder {
        private TextView tv_chapter;
        private LinearLayout ll_chapter;

    }

    //课堂
    class LessonHolder {
        private TextView tv_lesson;
        private LinearLayout ll_lesson;
    }
}
