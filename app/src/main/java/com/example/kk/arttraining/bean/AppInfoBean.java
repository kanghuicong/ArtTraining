package com.example.kk.arttraining.bean;

import android.graphics.drawable.Drawable;

/**
 * 作者：wschenyongyin on 2016/10/17 09:51
 * 说明:
 */
public class AppInfoBean extends NoDataResponseBean {

    private String version_no;
    private String update_time;
    private String version_url;
    private String describle;
    private String appName;
    private String versionName;
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

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "describle='" + describle + '\'' +
                ", appName='" + appName + '\'' +
                ", pName='" + pName + '\'' +
                ", update_time='" + update_time + '\'' +
                ", version_no='" + version_no + '\'' +
                ", version_url='" + version_url + '\'' +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}
