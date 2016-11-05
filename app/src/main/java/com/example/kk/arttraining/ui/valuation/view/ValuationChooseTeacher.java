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
    List<Boolean> isClick = new LinkedList<Boolean>();
    int isClickNum = 0;
    List<TecInfoBean> listInfo = new ArrayList<TecInfoBean>();
    ValuationGridViewAdapter teacherGridViewAdapter;
    int tag;
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

        List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
        for (int i = 0; i < 10; i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            tecInfoBean.setName("小灰灰" + i);
            tecInfoBean.setTec_id(i);
            tecInfoBeanList.add(tecInfoBean);
        }

        for (int i = 0; i < tecInfoBeanList.size(); i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            isClick.add(tecInfoBean.getTec_id(), false);
        }

        listInfo = (List) getIntent().getStringArrayListExtra("teacher_list");
        if (listInfo.size() == 0) {
            tag = 0;
        } else {
            tag = 1;
            isClickNum = listInfo.size();
            for (int i = 0; i < listInfo.size(); i++) {
                isClick.add(listInfo.get(i).getTec_id(), true);
            }
            teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
            gvTeacher.setAdapter(teacherGridViewAdapter);
        }

        teacherListViewAdapter = new ValuationListViewAdapter(this, tecInfoBeanList, isClick, isClickNum, "valuation", new ValuationListViewAdapter.CallBack() {
            @Override
            public void callbackAdd(int misClickNum, int id, String name) {
                TecInfoBean tecInfoBean = new TecInfoBean();
                tecInfoBean.setName(name);
                tecInfoBean.setTec_id(id);
                listInfo.add(tecInfoBean);
                isClickNum = misClickNum;
                if (listInfo.size() == 1 || tag == 1) {
                    teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
                    gvTeacher.setAdapter(teacherGridViewAdapter);
                } else {
                    teacherGridViewAdapter.notifyDataSetChanged();
                }
                isClick.set(id, true);
            }

            @Override
            public void callbackSub(int misClickNum, int id, String name) {
                isClickNum = misClickNum;
                for (int i = 0; i < listInfo.size(); i++) {
                    if (listInfo.get(i).getTec_id() == (id)) {
                        listInfo.remove(i);
                        break;
                    }
                }
                teacherGridViewAdapter.notifyDataSetChanged();
                isClick.set(id, false);
            }
        });

        gvTeacher.setOnItemClickListener(new TeacherGridItemClick());
        lvValuationTeacher.setAdapter(teacherListViewAdapter);
        lvValuationTeacher.setOnItemClickListener(new TeacherListItemClick());
    }

    @OnClick(R.id.bt_teacher_valuation)
    public void onClick() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("teacher_list",(ArrayList)listInfo);
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
            isClick.set(Integer.valueOf(listInfo.get(position).getTec_id()), false);
            ValuationListViewAdapter.Count(isClickNum - 1);
            listInfo.remove(position);
            teacherGridViewAdapter.notifyDataSetChanged();
            teacherListViewAdapter.notifyDataSetChanged();
            isClickNum--;
        }
    }
}
