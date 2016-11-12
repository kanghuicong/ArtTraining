package com.example.kk.arttraining.ui.discover.prot;

import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.parsebean.GroupListMyBean;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/10.
 * QQ邮箱:515849594@qq.com
 */
public interface IGroup {

    //我的小组
    void getMyGroupData(List<GroupBean> myGroupBeanList);
    //推荐小组
    void getRecommendCircleGroupData(List<GroupBean> recommendGroupBeanList);
    //小组动态列表
    void getGroupDynamic(List<Map<String, Object>> mapList);
    void OnFailure(String error_code);
}
