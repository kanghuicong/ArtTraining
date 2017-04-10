package com.example.kk.arttraining.ui.homePage.function.refresh;

import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.ui.me.bean.CloudContentBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/3/27.
 * QQ邮箱:515849594@qq.com
 */
public interface IRefresh<T> {

    void refreshSuccess(List<T> list);

    void loadSuccess(List<T> list);

    void onFailure(String error_code, String error_msg);
}
