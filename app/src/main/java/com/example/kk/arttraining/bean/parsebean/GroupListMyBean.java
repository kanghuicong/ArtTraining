package com.example.kk.arttraining.bean.parsebean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/19 17:34
 * 说明:用于接收小组信息的bean
 */
public class GroupListMyBean {
    private String error_code;
    private String error_msg;
    private List<ListMyBean> groups;

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

    public List<ListMyBean> getGroups() {
        return groups;
    }

    public void setGroups(List<ListMyBean> groups) {
        this.groups = groups;
    }

    public class ListMyBean {
        private int id;
        private String name;
        private String introduce;
        private int grade;
        private int users_num;
        private int verify_status;
        private String pic;

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getUsers_num() {
            return users_num;
        }

        public void setUsers_num(int users_num) {
            this.users_num = users_num;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }
    }
}
