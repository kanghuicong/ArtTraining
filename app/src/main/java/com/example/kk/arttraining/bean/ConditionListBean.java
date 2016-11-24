package com.example.kk.arttraining.bean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/23.
 * QQ邮箱:515849594@qq.com
 */
public class ConditionListBean extends NoDataResponseBean {
    List<ConditionBean> conditions;

    public List<ConditionBean> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionBean> conditions) {
        this.conditions = conditions;
    }
}
