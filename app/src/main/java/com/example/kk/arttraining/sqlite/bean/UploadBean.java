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
}
