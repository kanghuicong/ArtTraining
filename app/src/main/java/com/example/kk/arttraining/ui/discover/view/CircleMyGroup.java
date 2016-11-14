package com.example.kk.arttraining.ui.discover.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.ui.discover.adapter.CircleMyGroupAdapter;
import com.example.kk.arttraining.ui.discover.function.circle.CircleMyGroupData;
import com.example.kk.arttraining.ui.discover.prot.IMyGroup;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/13.
 * QQ邮箱:515849594@qq.com
 */
public class CircleMyGroup extends Activity implements IMyGroup{
    @InjectView(R.id.lv_circle_my_group)
    ListView lvCircleMyGroup;

    CircleMyGroupAdapter circleMyGroupAdapter;
    CircleMyGroupData circleMyGroupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_circle_my_group);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this,"我的小组");

        circleMyGroupData = new CircleMyGroupData(this);
        circleMyGroupData.getCircleMyGroup();
    }

    @Override
    public void getMyGroupData(List<GroupBean> myGroupBeanList) {
        circleMyGroupAdapter = new CircleMyGroupAdapter(this,myGroupBeanList);
        lvCircleMyGroup.setAdapter(circleMyGroupAdapter);
    }

    @Override
    public void OnMyGroupFailure(String error_code) {

    }
}
