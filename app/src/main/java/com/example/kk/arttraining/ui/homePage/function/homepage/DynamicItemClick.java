package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 * 说明：动态item
 */
public class DynamicItemClick implements AdapterView.OnItemClickListener {
    Activity activity;
    List<Map<String, Object>>mapList;

    public DynamicItemClick(Activity activity,List<Map<String, Object>>mapList) {
        this.activity = activity;
        this.mapList = mapList;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> statusMap = mapList.get(position);
        ParseStatusesBean parseStatusesBean = (ParseStatusesBean) statusMap.get("data");//一条数据

        Intent intent = new Intent(activity, DynamicContent.class);
        intent.putExtra("stus_type", parseStatusesBean.getStus_type());
        intent.putExtra("status_id", String.valueOf(parseStatusesBean.getStus_id()));
        activity.startActivity(intent);
    }
}
