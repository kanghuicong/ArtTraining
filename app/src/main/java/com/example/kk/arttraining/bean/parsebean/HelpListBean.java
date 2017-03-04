package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.HelpBean;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 16:46
 * 说明:
 */
public class HelpListBean extends NoDataResponseBean {
    List<HelpBean> helps;

    public HelpListBean() {
    }

    public List<HelpBean> getHelps() {
        return helps;
    }

    public void setHelps(List<HelpBean> helps) {
        this.helps = helps;
    }
}
