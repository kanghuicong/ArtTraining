package com.example.kk.arttraining.ui.homePage.bean;

/**
 * 作者：wschenyongyin on 2016/11/8 11:27
 * 说明:
 */
public class EvaBean {
    double total_score;
    double price_score;
    double result_score;
    double teachers_score;
    double environment_score;
    int uid;
    String name;
    String pic;
    String content;

    public EvaBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getEnvironment_score() {
        return environment_score;
    }

    public void setEnvironment_score(double environment_score) {
        this.environment_score = environment_score;
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

    public double getPrice_score() {
        return price_score;
    }

    public void setPrice_score(double price_score) {
        this.price_score = price_score;
    }

    public double getResult_score() {
        return result_score;
    }

    public void setResult_score(double result_score) {
        this.result_score = result_score;
    }

    public double getTeachers_score() {
        return teachers_score;
    }

    public void setTeachers_score(double teachers_score) {
        this.teachers_score = teachers_score;
    }

    public double getTotal_score() {
        return total_score;
    }

    public void setTotal_score(double total_score) {
        this.total_score = total_score;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
