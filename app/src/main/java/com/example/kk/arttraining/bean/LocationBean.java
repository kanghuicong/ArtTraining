package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 15:32
 * 说明:
 */
public class LocationBean {
    private int city_id;
    private int father_id;
    private String father_name;
    private String name;
    private int level;

    public LocationBean() {
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "city_id=" + city_id +
                ", father_id=" + father_id +
                ", father_name='" + father_name + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
