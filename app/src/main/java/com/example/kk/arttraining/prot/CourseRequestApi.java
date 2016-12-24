package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.ConditionListBean;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherContentBean;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherListBean;
import com.example.kk.arttraining.ui.course.bean.ArtTypeListBean;
import com.example.kk.arttraining.ui.course.bean.ChapterBeanList;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.ui.course.bean.CourseBeanList;
import com.example.kk.arttraining.ui.course.bean.CourseVideoListBean;
import com.example.kk.arttraining.ui.course.bean.LesRecourseBeanList;
import com.example.kk.arttraining.ui.course.bean.LessonBeanList;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/12/15 10:04
 * 说明:课程请求
 */
public interface CourseRequestApi {
    //艺术类别
    @POST(Config.URL_COURSE_ART_TYPE_LIST)
    @FormUrlEncoded
    Call<ArtTypeListBean> getArtTypeList(@FieldMap Map<String, Object> map);

    //教师列表
    @POST(Config.URL_COURSE_TEACHER_LIST)
    @FormUrlEncoded
    Call<ArtTeacherListBean> getArtTeacherList(@FieldMap Map<String, Object> map);

    //教师详情
    @POST(Config.URL_COURSE_TEACHER_CONTENT)
    @FormUrlEncoded
    Call<ArtTeacherContentBean> getArtTeacherContent(@FieldMap Map<String, Object> map);

    //课程列表
    @POST(Config.URL_COURSE_COURSE_LIST)
    @FormUrlEncoded
    Call<CourseBeanList> getCourseList(@FieldMap Map<String, Object> map);

    //获取课程详情
    @POST(Config.URL_COURSE_COURSE_INFO)
    @FormUrlEncoded
    Call<CourseBean> getCourseInfo(@FieldMap Map<String, Object> map);
    //课程章节列表
    @POST(Config.URL_COURSE_CHAPTER_LIST)
    @FormUrlEncoded
    Call<ChapterBeanList> getChapterList(@FieldMap Map<String, Object> map);


    //课堂列表
    @POST(Config.URL_LESSON_CHAPTER_LIST)
    @FormUrlEncoded
    Call<LessonBeanList> getLessonList(@FieldMap Map<String, Object> map);

    //课堂资源列表
    @POST(Config.URL_LESSON_SOURCE_PLAY_LIST)
    @FormUrlEncoded
    Call<LesRecourseBeanList> getLesRecourseList(@FieldMap Map<String, Object> map);

    //课堂视频列表
    @POST(Config.URL_LESSON_SOURCE_PLAY_LIST)
    @FormUrlEncoded
    Call<CourseVideoListBean> getCourseVideoList(@FieldMap Map<String, Object> map);

}
