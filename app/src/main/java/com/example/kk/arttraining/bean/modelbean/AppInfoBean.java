package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/10/17 09:51
 * 说明:
 */
public class AppInfoBean extends NoDataResponseBean {

    private int version_no;
    private String update_time;
    private String version_url;
    private String describle;
    private String appName;
    private String version_name;
//    private Drawable icon;
    private String pName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

//    public Drawable getIcon() {
//        return icon;
//    }
//
//    public void setIcon(Drawable icon) {
//        this.icon = icon;
//    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_no() {
        return version_no;
    }

    public void setVersion_no(int version_no) {
        this.version_no = version_no;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "appName='" + appName + '\'' +
                ", version_no=" + version_no +
                ", update_time='" + update_time + '\'' +
                ", version_url='" + version_url + '\'' +
                ", describle='" + describle + '\'' +
                ", version_name='" + version_name + '\'' +
                ", pName='" + pName + '\'' +
                '}';
    }
}
