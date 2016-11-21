package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationGridViewAdapter;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.ui.valuation.presenter.ChoserTeacherPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class ValuationChooseTeacher extends BaseActivity implements IValuationChooseTeacher, BottomPullSwipeRefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private ValuationListViewAdapter teacherListViewAdapter;
    private int tag;//判断是不是第一次进入该Activity，区别 Adapter new和 notifyDataSetChanged
    private int isClickNum = 0;//isClick的数目
    private List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();//list列表数据
    private List<TecInfoBean> listInfo = new ArrayList<TecInfoBean>();//grid数据
    private ValuationGridViewAdapter teacherGridViewAdapter;
    private String search_key;

    @InjectView(R.id.lv_valuation_teacher)
    ListView lvValuationTeacher;
    @InjectView(R.id.gv_teacher)
    MyGridView gvTeacher;
    @InjectView(R.id.im_search_teacher)
    ImageView im_search_teacher;
    @InjectView(R.id.et_search_teacher)
    EditText et_search_teacher;


    private ChoserTeacherPresenter presenter;
    private String spec = "声乐";
    private int self_id;
    private Boolean SEARCH_FLAG = true;


    private BottomPullSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_choose_teacher);
        ButterKnife.inject(this);

        TitleBack.TitleBackActivity(this, "选择名师");
        init();

    }

    @Override
    public void init() {
        Intent intent = getIntent();
        spec = intent.getStringExtra("spec");
        presenter = new ChoserTeacherPresenter(this);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(ValuationChooseTeacher.this);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.chose_teacher_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        //自动刷新
        swipeRefreshLayout.autoRefresh();



    }

    @OnClick({R.id.bt_teacher_valuation, R.id.im_search_teacher})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.im_search_teacher:
                SEARCH_FLAG = false;
                search_key = et_search_teacher.getText().toString();
                SearchChoser();
                break;
            case R.id.bt_teacher_valuation:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("teacher_list", (ArrayList) listInfo);
                setResult(ValuationMain.CHOSE_TEACHER, intent);
                finish();
                break;
        }

    }


    @Override
    public void SearchChoser() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("key", search_key);
        presenter.SearchTeacher(map);
    }

    @Override
    public void RefreshData() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", "声乐");
        presenter.RefreshData(map);
    }

    @Override
    public void LoadData() {
        self_id = teacherListViewAdapter.self_id();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("self", self_id);
        //上拉加载
        if (SEARCH_FLAG) {
            map.put("spec", spec);
        }
        //搜索名师加载
        else {
            map.put("key", search_key);
        }
        presenter.LoadData(map);
    }

    //搜索成功
    @Override
    public void SuccessSearch(List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        teacherListViewAdapter.Refresh(tecInfoBeanList.size());
        teacherListViewAdapter.notifyDataSetChanged();

    }

    //刷新成功
    @Override
    public void SuccessRefresh(List<TecInfoBean> tecInfoBeanList) {
        swipeRefreshLayout.setRefreshing(false);
        this.tecInfoBeanList = tecInfoBeanList;
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
            public void callbackAdd(int misClickNum, TecInfoBean tecInfoBean) {
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

    //上拉加载成功
    @Override
    public void SuccessLoad(List<TecInfoBean> tecBeanList) {
        tecInfoBeanList.addAll(tecBeanList);
        teacherListViewAdapter.Refresh(tecInfoBeanList.size());
        teacherListViewAdapter.notifyDataSetChanged();
    }

    //搜索失败
    @Override
    public void FailureSearch(String error_msg) {

    }

    //刷新失败
    @Override
    public void FailureRefresh(String error_msg) {
        swipeRefreshLayout.setRefreshing(false);

    }

    //加载失败
    @Override
    public void FailureLoad(String error_msg) {

    }

    //加载
    @Override
    public void onLoad() {
        LoadData();
    }

    //刷新
    @Override
    public void onRefresh() {
        RefreshData();
    }

    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TecInfoBean tecInfoBean= (TecInfoBean) parent.getItemAtPosition(position);
            Intent intent = new Intent(ValuationChooseTeacher.this, ThemeTeacherContent.class);
            int teacher_id=tecInfoBean.getTec_id();
            intent.putExtra("tec_id",teacher_id+"");
            intent.putExtra("type", "valuation");
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
