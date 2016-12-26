package com.example.kk.arttraining.ui.course.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/12/23 16:17
 * 说明:爱艺术课程简介
 */
public class ArtIntroductionFragment extends Fragment {


    @InjectView(R.id.course_name)
    TextView courseName;
    @InjectView(R.id.tec_name)
    TextView tecName;
    @InjectView(R.id.course_style)
    TextView courseStyle;
    @InjectView(R.id.course_position)
    TextView coursePosition;
    @InjectView(R.id.course_describe)
    JustifyText courseDescribe;
    private View view;
    private Context context;

    private CourseBean courseBean;
    private String course_id;

    private String level_min="";
    private String getLevel_max="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        if (view == null) {
            view = View.inflate(context, R.layout.course_iart_introduction_fragment, null);
            ButterKnife.inject(this, view);
            init();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        ButterKnife.inject(this, view);
        return view;
    }

    //初始化数据
    void init() {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        course_id = bundle.getString("course_id");
        getInfoData(course_id);
    }

    //获取课程详细信息
    void getInfoData(String course_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.IART_USER_NAME);
        map.put("course_id", course_id);
        Callback<CourseBean> callback = new Callback<CourseBean>() {
            @Override
            public void onResponse(Call<CourseBean> call, Response<CourseBean> response) {
                CourseBean courseBean = response.body();

                if (courseBean != null) {
                    UIUtil.showLog("courseBean", courseBean.toString() + "");
                    if (courseBean.getCode() == 0) {
                        Message msg = new Message();
                        msg.obj = courseBean;
                        mHandler.sendMessage(msg);
                    } else {
                        UIUtil.ToastshowShort(context, "获取课程信息失败");
                    }
                } else {
                    UIUtil.ToastshowShort(context, "获取课程信息失败");
                }
            }

            @Override
            public void onFailure(Call<CourseBean> call, Throwable t) {
                UIUtil.ToastshowShort(context, "获取课程信息失败");
            }
        };
        Call<CourseBean> call = HttpRequest.getCourseApi().getCourseInfo(map);
        call.enqueue(callback);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            courseBean = (CourseBean) msg.obj;
            courseName.setText(courseBean.getName());
            tecName.setText(courseBean.getTeacher_name());
            courseStyle.setText("风格:" + courseBean.getStyle_name());
            courseDescribe.setText(courseBean.getProfile());
            switch (courseBean.getLevel_min()) {
                case "1":
                    level_min="初级";
                    break;
                case "2":
                    level_min="中级";
                    break;
                case "3":
                    level_min="高级";
                    break;
            }

            switch (courseBean.getLevel_max()){
                case "1":
                    level_min="初级";
                    break;
                case "2":
                    level_min="中级";
                    break;
                case "3":
                    level_min="高级";
                    break;
            }

            if(level_min.equals(getLevel_max)){
                coursePosition.setText("定位:" + level_min);
            }else {
                coursePosition.setText("定位:" + level_min + "-" + getLevel_max);
            }

            ((ArtCourseActivity) context).SuccessGetCourseInfo(courseBean);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
