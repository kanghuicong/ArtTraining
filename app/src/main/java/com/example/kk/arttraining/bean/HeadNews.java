package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 17:04
 * 说明:艺赔头条
 */
public class HeadNews extends NoDataResponseBean{
    private int info_id;
    private String title;
    private String content;
    private String pic;
    private String url;
    private String priority;
    private int is_publish;
    private int is_deleted;
    private String create_time;
    private String order_code;
    private String delete_time;
    private String remarks;
    private String attachment;

    public HeadNews() {
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getIs_publish() {
        return is_publish;
    }

    public void setIs_publish(int is_publish) {
        this.is_publish = is_publish;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HeadNews{" +
                "attachment='" + attachment + '\'' +
                ", info_id=" + info_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", priority='" + priority + '\'' +
                ", is_publish=" + is_publish +
                ", is_deleted=" + is_deleted +
                ", create_time='" + create_time + '\'' +
                ", order_code='" + order_code + '\'' +
                ", delete_time='" + delete_time + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
