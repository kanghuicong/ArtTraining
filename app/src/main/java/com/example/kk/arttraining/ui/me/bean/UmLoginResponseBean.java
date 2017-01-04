package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.UserLoginBean;

/**
 * 作者：wschenyongyin on 2017/1/4 10:39
 * 说明:第三方登录返回bean
 */
public class UmLoginResponseBean extends UserLoginBean {
    String is_bind;

    public UmLoginResponseBean() {
    }

    public String getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(String is_bind) {
        this.is_bind = is_bind;
    }

    @Override
    public String toString() {
        return "UmLoginResponseBean{" +
                "is_bind='" + is_bind + '\'' +
                '}';
    }
}
