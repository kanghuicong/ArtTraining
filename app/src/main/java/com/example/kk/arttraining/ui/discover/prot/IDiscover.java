package com.example.kk.arttraining.ui.discover.prot;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/12/8.
 * QQ邮箱:515849594@qq.com
 */
public interface IDiscover {
    //获取动态列表
    void getDynamicListData(List<Map<String, Object>> mapList);

    //更新动态列表
    void loadDynamicListData(List<Map<String, Object>> mapList);

    //更新动态列表失败
    void OnLoadDynamicFailure(int result);

    void OnDynamicFailure(String error_code);

}
