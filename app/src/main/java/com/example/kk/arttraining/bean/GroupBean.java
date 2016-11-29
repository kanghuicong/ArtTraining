package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 15:19
 * 说明:
 */
public class GroupBean {
    private int group_id;
    private String name;
    private String introduce;
    private int grade;
    private int users_num;
    private String pic;
    private String order_code;
    private String verify_status;


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
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

    public String getVerify_status() {
        return verify_status;
    }

    public void setVerify_status(String verify_status) {
        this.verify_status = verify_status;
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "grade=" + grade +
                ", group_id=" + group_id +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", users_num=" + users_num +
                ", pic='" + pic + '\'' +
                ", order_code='" + order_code + '\'' +
                ", verify_status='" + verify_status + '\'' +
                '}';
    }
}
