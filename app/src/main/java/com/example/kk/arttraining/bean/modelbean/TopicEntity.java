package com.example.kk.arttraining.bean.modelbean;

/**
 * Created by kanghuicong on 2016/9/20.
 * QQ邮箱:515849594@qq.com
 * 说明:homepage的精选专题
 */
public class TopicEntity {
    String pic;
    String title;
    int id;
    int num;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
