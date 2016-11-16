package com.example.kk.arttraining.ui.school.bean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/26 14:47
 * 说明:院校
 */
public class SchoolBean implements Serializable{

    private int institution_id;
    private String name;
    private String introduction;
    private String admissions_guide;
    private int follow_num;
    private int browse_num;

    public SchoolBean() {
    }

    public String getAdmissions_guide() {
        return admissions_guide;
    }

    public void setAdmissions_guide(String admissions_guide) {
        this.admissions_guide = admissions_guide;
    }

    public int getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(int browse_num) {
        this.browse_num = browse_num;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
