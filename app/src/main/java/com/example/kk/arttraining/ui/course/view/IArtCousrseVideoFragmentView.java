package com.example.kk.arttraining.ui.course.view;

import com.example.kk.arttraining.ui.course.bean.ChapterBean;
import com.example.kk.arttraining.ui.course.bean.LessonBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/24 11:02
 * 说明:
 */
public interface IArtCousrseVideoFragmentView {

    void getChapterData();

    void getLessonData();

    void SuccessChapter(List<ChapterBean> chapterBeanList,int total_num);

    void SuccessLesson(List<LessonBean> lessonBeanList);

    void FailureChapter(int error_code,String error_msg);

    void FailureLesson(int error_code,String error_msg);
}
