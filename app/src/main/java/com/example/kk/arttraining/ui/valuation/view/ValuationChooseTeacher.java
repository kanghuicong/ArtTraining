package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationGridViewAdapter;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class ValuationChooseTeacher extends Activity {

    ValuationListViewAdapter teacherListViewAdapter;
    SimpleAdapter simpleAdapter;
    List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
    List<Boolean> isClick = new LinkedList<Boolean>();
    int isClickNum = 0;
    Boolean state_school = false;
    Boolean state_regional = false;
    List<TecInfoBean> listInfo = new ArrayList<TecInfoBean>();
    ValuationGridViewAdapter teacherGridViewAdapter;

    @InjectView(R.id.lv_teacher)
    MyListView lvTeacher;
    @InjectView(R.id.lv_teacher_theme)
    MyListView lvTeacherTheme;
    @InjectView(R.id.ll_teacher_pay)
    LinearLayout llTeacherPay;
    @InjectView(R.id.gv_teacher)
    MyGridView gvTeacher;

    String type;
    int tag;
    @InjectView(R.id.rb_teacher_profession)
    RadioButton rbTeacherProfession;
    @InjectView(R.id.ll_valuation_search)
    LinearLayout llValuationSearch;
    @InjectView(R.id.bt_teacher_valuation)
    Button btTeacherValuation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "选择名师");
        rbTeacherProfession.setVisibility(View.GONE);
        llValuationSearch.setVisibility(View.VISIBLE);
        btTeacherValuation.setText("完成");

        List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
        for (int i = 0; i < 10; i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            tecInfoBean.setName("小灰灰" + i);
            tecInfoBean.setTec_id(i);
            tecInfoBeanList.add(tecInfoBean);
        }

        for (int i=0;i<tecInfoBeanList.size();i++) {
            TecInfoBean tecInfoBean = new TecInfoBean();
            isClick.add(tecInfoBean.getTec_id(), false);
        }

        listInfo = (List) getIntent().getStringArrayListExtra("teacher_list");
        if (listInfo.size() == 0) {
            tag = 0;
        }else {
            tag = 1;
            isClickNum = listInfo.size();
            for (int i=0;i<listInfo.size();i++) {
                isClick.add(listInfo.get(i).getTec_id(), true);
            }
            teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
            gvTeacher.setAdapter(teacherGridViewAdapter);
        }

        teacherListViewAdapter = new ValuationListViewAdapter(this, tecInfoBeanList, isClick, isClickNum, "valuation",new ValuationListViewAdapter.CallBack() {
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
                    if (listInfo.get(i).getTec_id()==(id)) {
                        listInfo.remove(i);
                        break;
                    }
                }
                teacherGridViewAdapter.notifyDataSetChanged();
                isClick.set(id, false);
            }
        });

        gvTeacher.setOnItemClickListener(new TeacherGridItemClick());
        lvTeacher.setAdapter(teacherListViewAdapter);
        lvTeacher.setOnItemClickListener(new TeacherListItemClick());
    }

    @OnClick({R.id.rb_teacher_school, R.id.rb_teacher_regional,R.id.bt_teacher_valuation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_teacher_school:
                initSate("school", state_school, state_regional);
                break;
            case R.id.rb_teacher_regional:
                initSate("regional", state_regional, state_school);
                break;
            case R.id.bt_teacher_valuation:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("teacher_list",(ArrayList)listInfo);
                setResult(ValuationMain.CHOSE_TEACHER, intent);
                finish();
                break;
        }
    }

    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ValuationChooseTeacher.this, ThemeTeacherContent.class);
            startActivity(intent);
        }
    }

    public void initSate(String state, Boolean state_school, Boolean state_regional) {
        if (!state_regional && !state_school) {
            yesState(state);
            lvTeacherTheme.setVisibility(View.VISIBLE);
            lvTeacher.setVisibility(View.GONE);
            llTeacherPay.setVisibility(View.GONE);
            initThemeListView(state);
        } else if (!state_school && state_regional) {
            yesState(state);
            getDate(state);
            simpleAdapter.notifyDataSetChanged();
        } else if (state_school) {
            noState();
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
            llTeacherPay.setVisibility(View.VISIBLE);
        }
    }

    private void initThemeListView(String state) {
        getDate(state);

        simpleAdapter = new SimpleAdapter(this, mList,
                R.layout.homepage_search_history_listview, new String[]{"content"},
                new int[]{R.id.tv_search_history});
        lvTeacherTheme.setAdapter(simpleAdapter);
        lvTeacherTheme.setOnItemClickListener(new ThemeItemClick());
    }

    public void noState() {
        state_regional = false;
        state_school = false;
    }

    public void yesState(String state) {
        switch (state) {
            case "school":
                state_regional = false;
                state_school = true;
                break;
            case "regional":
                state_regional = true;
                state_school = false;
                break;
        }
    }

    public void getDate(String state) {
        mList.clear();
        switch (state) {
            case "school":
                for (int i = 0; i < 30; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("content", i + 10 + "");
                    mList.add(map);
                }
                break;
            case "regional":
                for (int i = 0; i < 30; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("content", i + 100 + "");
                    mList.add(map);
                }
                break;
        }
    }

    private class ThemeItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UIUtil.ToastshowShort(ValuationChooseTeacher.this, position + "");
            noState();
            mList.clear();
            lvTeacherTheme.setVisibility(View.GONE);
            lvTeacher.setVisibility(View.VISIBLE);
            llTeacherPay.setVisibility(View.VISIBLE);
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
