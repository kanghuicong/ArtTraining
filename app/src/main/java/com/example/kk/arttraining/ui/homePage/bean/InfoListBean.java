package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.InfoBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/6.
 * QQ邮箱:515849594@qq.com
 */
public class InfoListBean extends NoDataResponseBean {
    List<InfoBean> informations;

    public List<InfoBean> getInformations() {
        return informations;
    }

    public void setInformations(List<InfoBean> informations) {
        this.informations = informations;
    }
}
