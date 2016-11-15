package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.ui.homePage.bean.Course;
import com.example.kk.arttraining.ui.homePage.bean.EvaluateOrg;
import com.example.kk.arttraining.ui.homePage.bean.Teachers;
import com.example.kk.arttraining.ui.homePage.bean.Trainees;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 10:41
 * 说明:根据机构ID获取机构详情信息
 */
public class OrgShowBean {
    private String error_code;
    private String error_msg;
    private int id;
    private String name;
    private List<pic> pic;
    private int comment;
    private int fans_num;
    private String auth;
    private int sign_up;
    private int browse_num;
    private String introduction;
    private String remarks;
    private String city;
    private String province;
    private String skill;
    private String head_pic;
    private List<Tags> tags;
    private List<Teachers> teachers;
    private List<Course> course;
    private List<Trainees> trainees;
    private List<Contact> contact;
    private String contact_phone;
    private String contact_address;

    private EvaluateOrg evaluate;

    public OrgShowBean() {
    }

    public String getAuth() {
        return auth;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }


    public EvaluateOrg getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateOrg evaluate) {
        this.evaluate = evaluate;
    }


    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(int browse_num) {
        this.browse_num = browse_num;
    }

    public int getSign_up() {
        return sign_up;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public List<Teachers> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teachers> teachers) {
        this.teachers = teachers;
    }

    public List<Trainees> getTrainees() {
        return trainees;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public List<OrgShowBean.pic> getPic() {
        return pic;
    }

    public void setPic(List<OrgShowBean.pic> pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public void setTrainees(List<Trainees> trainees) {
        this.trainees = trainees;
    }


    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    //机构图片列表
    public static class pic {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    //机构标签
    public static class Tags {
        String tag_name;

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }

    //机构联系方式
    public static class Contact {
        String contact_phone;
        String contact_address;

        public Contact() {
        }

        public String getContact_address() {
            return contact_address;
        }

        public void setContact_address(String contact_address) {
            this.contact_address = contact_address;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }
    }

    public void setSign_up(int sign_up) {
        this.sign_up = sign_up;
    }
}
