package com.example.kk.arttraining.ui.homePage.bean;

/**
 * 作者：wschenyongyin on 2016/10/26 14:48
 * 说明:
 */
public class ProvinceBean {
    int pro_id;
    int father_id;
    String name;
    int level;

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFather_id() {
        return father_id;
    }

    public void setFather_id(int father_id) {
        this.father_id = father_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
