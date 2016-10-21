package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 19:22
 * 说明:话题列表
 */
public class ThemesBean {
    private String pic;
    private String title;
    private int thm_id;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getThm_id() {
        return thm_id;
    }

    public void setThm_id(int thm_id) {
        this.thm_id = thm_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
