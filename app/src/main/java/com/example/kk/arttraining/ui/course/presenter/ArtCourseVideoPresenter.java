package com.example.kk.arttraining.ui.course.presenter;

import com.example.kk.arttraining.ui.course.bean.ChapterBeanList;
import com.example.kk.arttraining.ui.course.bean.LessonBeanList;
import com.example.kk.arttraining.ui.course.view.IArtCousrseVideoFragmentView;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/12/24 11:05
 * 说明:课程章节列表业务逻辑处理类
 */
public class ArtCourseVideoPresenter {
    IArtCousrseVideoFragmentView iArtCousrseVideoFragmentView;

    public ArtCourseVideoPresenter(IArtCousrseVideoFragmentView iArtCousrseVideoFragmentView) {
        this.iArtCousrseVideoFragmentView = iArtCousrseVideoFragmentView;
    }


    //获取章节列表
    public void getChapterData(Map<String, Object> map) {

        Callback<ChapterBeanList> callback = new Callback<ChapterBeanList>() {
            @Override
            public void onResponse(Call<ChapterBeanList> call, Response<ChapterBeanList> response) {
                ChapterBeanList chapterBeanList = response.body();

                if (chapterBeanList != null) {
                    UIUtil.showLog("chapterList--->",chapterBeanList.toString());
                    if (chapterBeanList.getCode() == 0) {
                        iArtCousrseVideoFragmentView.SuccessChapter(chapterBeanList.getChapter_list(),chapterBeanList.getTotal_code());
                    } else {
                        iArtCousrseVideoFragmentView.FailureChapter(chapterBeanList.getCode(),"获取数据失败");
                    }
                } else {
                    iArtCousrseVideoFragmentView.FailureChapter(chapterBeanList.getCode(),"获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<ChapterBeanList> call, Throwable t) {
                iArtCousrseVideoFragmentView.FailureChapter(400,"获取数据失败");
            }
        };
        Call<ChapterBeanList> call = HttpRequest.getCourseApi().getChapterList(map);
        call.enqueue(callback);

    }

    //获取课堂列表
    public void getLessonData(Map<String, Object> map) {

        Callback<LessonBeanList> callback = new Callback<LessonBeanList>() {
            @Override
            public void onResponse(Call<LessonBeanList> call, Response<LessonBeanList> response) {
                LessonBeanList lessonBeanList=response.body();
                if(lessonBeanList!=null){
                    if(lessonBeanList.getCode()==0){
                        iArtCousrseVideoFragmentView.SuccessLesson(lessonBeanList.getLessonBeen());
                    }else {
                        iArtCousrseVideoFragmentView.FailureLesson(lessonBeanList.getCode(),"获取课堂列表失败");
                    }
                }else {
                    iArtCousrseVideoFragmentView.FailureLesson(400,"获取课堂列表失败");
                }
            }
            @Override
            public void onFailure(Call<LessonBeanList> call, Throwable t) {
                iArtCousrseVideoFragmentView.FailureLesson(400,"获取课堂列表失败");
            }
        };

        Call<LessonBeanList> call = HttpRequest.getCourseApi().getLessonList(map);
        call.enqueue(callback);

    }
}
