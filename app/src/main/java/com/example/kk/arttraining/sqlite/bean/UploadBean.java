package com.example.kk.arttraining.sqlite.bean;

/**
 * 作者：wschenyongyin on 2016/11/2 16:34
 * 说明:
 */
public class UploadBean {
    //订单id
    String order_id;
    //文件路径
    String file_path;
    //创建时间
    String create_time;
    //标题
    String order_title;
    //进度
    int progress;
    //类型（上传中，上传完成）
    String type;

    //图片
    String order_pic;

    public UploadBean() {
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_pic() {
        return order_pic;
    }

    public void setOrder_pic(String order_pic) {
        this.order_pic = order_pic;
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "create_time='" + create_time + '\'' +
                ", order_id='" + order_id + '\'' +
                ", file_path='" + file_path + '\'' +
                ", order_title='" + order_title + '\'' +
                ", progress=" + progress +
                ", type='" + type + '\'' +
                ", order_pic='" + order_pic + '\'' +
                '}';
    }
}
