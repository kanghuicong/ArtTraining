package com.example.kk.arttraining.bean.parsebean;

/**
 * 作者：wschenyongyin on 2016/10/19 19:14
 * 说明:用户加入小组返回的数据或退出小组bean
 */
public class GroupJoinExitBean {
    private String error_code;
    private String error_msg;
    private int id;
    private int group_id;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
