package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationGridViewAdapter;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class ValuationChooseTeacher extends Activity {

    ValuationListViewAdapter teacherListViewAdapter;
    int tag;//判断是不是第一次进入该Activity，区别 Adapter new和 notifyDataSetChanged
    int isClickNum = 0;//isClick的数目
    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();//list列表数据
    List<TecInfoBean> listInfo = new ArrayList<TecInfoBean>();//grid数据
    ValuationGridViewAdapter teacherGridViewAdapter;

    @InjectView(R.id.lv_valuation_teacher)
    MyListView lvValuationTeacher;
    @InjectView(R.id.gv_teacher)
    MyGridView gvTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_choose_teacher);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "选择名师");

        for (int i = 0; i < 10; i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            tecInfoBean.setName("小灰灰" + i);
            tecInfoBean.setTec_id(10 + i);
            tecInfoBean.setClick(false);
            tecInfoBeanList.add(tecInfoBean);
        }

        //获取从测评页已选择的老师信息
        listInfo = (List) getIntent().getStringArrayListExtra("teacher_list");
        if (listInfo.size() == 0) {
            tag = 0;
        } else {
            tag = 1;
            isClickNum = listInfo.size();
            for (int i = 0; i < listInfo.size(); i++) {
                TecInfoBean tecInfoBean = listInfo.get(i);
                for (int n = 0; n < tecInfoBeanList.size(); n++) {
                    TecInfoBean tecInfoBean1 = tecInfoBeanList.get(n);
                    if (tecInfoBean.getTec_id() == tecInfoBean1.getTec_id()) {
                        tecInfoBeanList.set(n, tecInfoBean);
                        break;
                    }
                }
            }
            teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
            gvTeacher.setAdapter(teacherGridViewAdapter);
        }

        teacherListViewAdapter = new ValuationListViewAdapter(this, tecInfoBeanList, isClickNum, "valuation", new ValuationListViewAdapter.CallBack() {
            @Override
            public void callbackAdd(int misClickNum,TecInfoBean tecInfoBean) {
                listInfo.add(tecInfoBean);
                isClickNum = misClickNum;
                if (listInfo.size() == 1 || tag == 1) {
                    teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
                    gvTeacher.setAdapter(teacherGridViewAdapter);
                } else {
                    teacherGridViewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void callbackSub(int misClickNum, TecInfoBean tecInfoBean) {
                isClickNum = misClickNum;
                for (int i = 0; i < listInfo.size(); i++) {
                    if (listInfo.get(i).getTec_id() == tecInfoBean.getTec_id()) {
                        listInfo.remove(i);
                        break;
                    }
                }
                teacherGridViewAdapter.notifyDataSetChanged();
            }
        });
        gvTeacher.setOnItemClickListener(new TeacherGridItemClick());
        lvValuationTeacher.setAdapter(teacherListViewAdapter);
        lvValuationTeacher.setOnItemClickListener(new TeacherListItemClick());
    }

    @OnClick(R.id.bt_teacher_valuation)
    public void onClick() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("teacher_list", (ArrayList) listInfo);
        setResult(ValuationMain.CHOSE_TEACHER, intent);
        finish();
    }

    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ValuationChooseTeacher.this, ThemeTeacherContent.class);
            startActivity(intent);
        }
    }

    private class TeacherGridItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TecInfoBean tecInfoBean = listInfo.get(position);
            for (int n = 0; n < tecInfoBeanList.size(); n++) {
                TecInfoBean tecInfoBean1 = tecInfoBeanList.get(n);
                if (tecInfoBean.getTec_id() == tecInfoBean1.getTec_id()) {
                    tecInfoBeanList.get(n).setClick(false);
                    break;
                }
            }
            ValuationListViewAdapter.Count(isClickNum - 1);
            listInfo.remove(position);
            teacherGridViewAdapter.notifyDataSetChanged();
            teacherListViewAdapter.notifyDataSetChanged();
            isClickNum--;
        }
    }
}
