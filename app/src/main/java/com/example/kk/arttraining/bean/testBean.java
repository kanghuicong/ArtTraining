package com.example.kk.arttraining.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/19 20:16
 * 说明:
 */
public class testBean {
    int state;// 状态码
    List<UserBean> datas;
    String fuck;

    public String getFuck() {
        return fuck;
    }

    public void setFuck(String fuck) {
        this.fuck = fuck;
    }

    public List<UserBean> getDatas() {
        return datas;
    }

    public void setDatas(List<UserBean> datas) {
        this.datas = datas;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public class UserBean {
        private String user_name;
        private String user_pwd;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_pwd() {
            return user_pwd;
        }

        public void setUser_pwd(String user_pwd) {
            this.user_pwd = user_pwd;
        }
    }
}
