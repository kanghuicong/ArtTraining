package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/11/6 15:31
 * 说明:轮播bean
 */
public class BannerBean extends NoDataResponseBean {

    private int banner_id;
    private String title;
    private String pic;
    private String url;
    String create_time;
    String content;

    public BannerBean() {
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

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        return "BannerBean{" +
                "banner_id=" + banner_id +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", create_time='" + create_time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
