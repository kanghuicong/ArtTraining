package com.example.kk.arttraining.bean;

import com.example.kk.arttraining.MainActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 15:14
 * 说明:专业bean
 */
public class MajorBean implements Serializable {

    private int major_id;
    private String major_name;
    private int father_id;
    private String father_name;
    private int level;
    private List<MajorBean> son_majors;

    public MajorBean() {
    }

    public int getFather_id() {
        return father_id;
    }

    public void setFather_id(int father_id) {
        this.father_id = father_id;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public List<MajorBean> getSon_majors() {
        return son_majors;
    }

    public void setSon_majors(List<MajorBean> son_majors) {
        this.son_majors = son_majors;
    }
}
