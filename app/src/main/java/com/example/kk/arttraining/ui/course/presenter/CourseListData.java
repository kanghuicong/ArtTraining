package com.example.kk.arttraining.ui.course.presenter;

import com.example.kk.arttraining.ui.course.bean.CourseBeanList;
import com.example.kk.arttraining.ui.course.view.ICourseMainView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/12/17.
 * QQ邮箱:515849594@qq.com
 */
public class CourseListData {

    ICourseMainView iCourse;

    public CourseListData(ICourseMainView iCourse) {
        this.iCourse = iCourse;
    }

    public void getCourseListData(String keyword, String area,int start_index, String level) {
        Map<String, Object> map = new HashMap<String, Object>();
//        if(Config.User_Id.equals("0"))
        map.put("user_name", "18979756587");
        if(Config.ACCESS_TOKEN!=null)
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("keyword", keyword);//关键字
        if (area.equals("音乐")) {
            map.put("area_id", 1);
        }
//        map.put("art_type_id", "");
        map.put("start_index", start_index);
        map.put("get_count", 20);
        if (level.equals("初级")) {
            map.put("level_from", 1);
            map.put("level_end", 1);
        } else if (level.equals("中级")) {
            map.put("level_from", 2);
            map.put("level_end", 2);
        }else if (level.equals("高级")) {
            map.put("level_from", 3);
            map.put("level_end", 3);
        }

        Callback<CourseBeanList> callback = new Callback<CourseBeanList>() {
            @Override
            public void onResponse(Call<CourseBeanList> call, Response<CourseBeanList> response) {
                CourseBeanList courseBeanList = response.body();

                if (response.body() != null) {
                    if (courseBeanList.getCode() == 0) {
                        iCourse.getCourseList(courseBeanList.getCourse_list());
                    } else {
                        iCourse.OnCourseFailure();
                    }
                } else {
                    iCourse.OnCourseFailure();
                }
            }

            @Override
            public void onFailure(Call<CourseBeanList> call, Throwable t) {
                iCourse.OnCourseFailure();
            }
        };

        Call<CourseBeanList> call = HttpRequest.getCourseApi().getCourseList(map);
        call.enqueue(callback);
    }

    public void loadCourseListData(String keyword, String area,int start_index, String level) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.User_Id);
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("keyword", keyword);//关键字
        if (area.equals("音乐")) {
            map.put("area_id", 1);
        }
//        map.put("art_type_id", "");
        map.put("start_index", start_index);
        map.put("get_count", 20);
        if (level.equals("初级")) {
            map.put("level_from", 1);
            map.put("level_end", 1);
        } else if (level.equals("中级")) {
            map.put("level_from", 2);
            map.put("level_end", 2);
        }else if (level.equals("高级")) {
            map.put("level_from", 3);
            map.put("level_end", 3);
        }

        Callback<CourseBeanList> callback = new Callback<CourseBeanList>() {
            @Override
            public void onResponse(Call<CourseBeanList> call, Response<CourseBeanList> response) {
                CourseBeanList courseBeanList = response.body();

                if (response.body() != null) {
                    if (courseBeanList.getCode() == 0) {
                        iCourse.loadCourseList(courseBeanList.getCourse_list());
                    } else {
                        iCourse.OnLoadCourseListFailure(courseBeanList.getCode());
                    }
                } else {
                    iCourse.OnLoadCourseListFailure(404);
                }
            }

            @Override
            public void onFailure(Call<CourseBeanList> call, Throwable t) {
                iCourse.OnLoadCourseListFailure(404);
            }
        };

        Call<CourseBeanList> call = HttpRequest.getCourseApi().getCourseList(map);
        call.enqueue(callback);
    }

}
