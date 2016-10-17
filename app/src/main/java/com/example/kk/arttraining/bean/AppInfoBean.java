package com.example.kk.arttraining.bean;

import android.graphics.drawable.Drawable;

/**
 * 作者：wschenyongyin on 2016/10/17 09:51
 * 说明:
 */
public class AppInfoBean {

    private String appname;
    private String pname;
    private String versionName;
    private int versionCode;
    private Drawable icon;

    public AppInfoBean() {
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
