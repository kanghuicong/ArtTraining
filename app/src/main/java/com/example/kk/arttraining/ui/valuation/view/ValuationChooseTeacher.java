package com.example.kk.arttraining.ui.valuation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacherContent;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationGridViewAdapter;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationListViewAdapter;
import com.example.kk.arttraining.ui.valuation.presenter.ChoserTeacherPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

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
public class ValuationChooseTeacher extends BaseActivity implements IValuationChooseTeacher, PullToRefreshLayout.OnRefreshListener {

    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.chose_tec_zj)
    TextView choseTecZj;
    @InjectView(R.id.chose_tec_ms)
    TextView choseTecMs;
    private ValuationListViewAdapter teacherListViewAdapter;
    private boolean TAG;//判断是不是第一次进入该Activity，区别 new Adapter和 notifyDataSetChanged
    private int isClickNum = 0;//isClick的数目
    private List<TecInfoBean> listInfo = new ArrayList<TecInfoBean>();//grid数据
    private ValuationGridViewAdapter teacherGridViewAdapter;
    private String search_key = "";
    private List<TecInfoBean> listData = new ArrayList<TecInfoBean>();
    @InjectView(R.id.lv_valuation_teacher)
    ListView lvValuationTeacher;
    @InjectView(R.id.gv_teacher)
    MyGridView gvTeacher;
    @InjectView(R.id.et_search_teacher)
    EditText et_search_teacher;
    private ChoserTeacherPresenter presenter;
    private String spec = "声乐";
    private int self_id;
    int refreshResult = PullToRefreshLayout.FAIL;

    private String tec_identity = "zj";

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_choose_teacher);
        ButterKnife.inject(this);
        init();

    }

    @Override
    public void init() {
        refreshView.setOnRefreshListener(this);

        KeySearch();//修改键盘搜索键及该搜索键点击事件

        Intent intent = getIntent();
        spec = intent.getStringExtra("spec");
        getTecData();

    }


    void getTecData() {
        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.show();
        presenter = new ChoserTeacherPresenter(this);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", spec);
        map.put("identity", tec_identity);
        if (!search_key.equals("")) {
            map.put("key", search_key);
        }
        presenter.RefreshData(map);


        et_search_teacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_search_teacher.getText().toString())) {
                    search_key = et_search_teacher.getText().toString();
                }else {
                    search_key = "";
                }
            }
        });
    }

    @OnClick({R.id.bt_teacher_valuation, R.id.im_search_teacher, R.id.chose_tec_zj, R.id.chose_tec_ms, R.id.iv_title_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_search_teacher:
                doSearch();
                break;
            case R.id.bt_teacher_valuation:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("teacher_list", (ArrayList) listInfo);
                setResult(ValuationMain.CHOSE_TEACHER, intent);
                finish();
                break;
            case R.id.iv_title_back:
                finish();
                break;
            //点击专家
            case R.id.chose_tec_zj:
                tec_identity = "zj";
                search_key = "";
                choseTecZj.setBackground(getResources().getDrawable(R.drawable.shape_chose_school_left_focus));
                choseTecMs.setBackground(getResources().getDrawable(R.drawable.shape_chose_school_right_unfocus));
                choseTecMs.setTextColor(getResources().getColor(R.color.white));
                choseTecZj.setTextColor(getResources().getColor(R.color.blue_overlay));
                getTecData();
                break;
            //点击名师
            case R.id.chose_tec_ms:
                tec_identity = "ms";
                search_key = "";
                choseTecZj.setBackground(getResources().getDrawable(R.drawable.shape_chose_school_left_unfocus));
                choseTecMs.setBackground(getResources().getDrawable(R.drawable.shape_chose_school_right_focus));
                choseTecMs.setTextColor(getResources().getColor(R.color.blue_overlay));
                choseTecZj.setTextColor(getResources().getColor(R.color.white));
                getTecData();
                break;
        }
    }



    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TecInfoBean tecInfoBean = (TecInfoBean) parent.getItemAtPosition(position);
            Intent intent = new Intent(ValuationChooseTeacher.this, ThemeTeacherContent.class);
            int teacher_id = tecInfoBean.getTec_id();
            intent.putExtra("tec_id", teacher_id + "");
            intent.putExtra("type", "valuation");
            startActivity(intent);
        }
    }

    private class TeacherGridItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TecInfoBean tecInfoBean = listInfo.get(position);
            for (int n = 0; n < listData.size(); n++) {
                TecInfoBean tecInfoBean1 = listData.get(n);
                if (tecInfoBean.getTec_id() == tecInfoBean1.getTec_id()) {
                    listData.get(n).setClick(false);
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

    // 修改回车键功能
    public void KeySearch() {
        et_search_teacher.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    KeyBoardUtils.closeKeybord(et_search_teacher, ValuationChooseTeacher.this);
                    doSearch();
                }
                return false;
            }
        });
    }

    //搜索
    public void doSearch() {
        search_key = et_search_teacher.getText().toString();
        if (search_key.equals("") || search_key == null) {
            UIUtil.ToastshowShort(ValuationChooseTeacher.this, "请输入搜索内容");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("access_token", Config.ACCESS_TOKEN);
            map.put("uid", Config.UID);
            map.put("spec", spec);
            map.put("key", search_key);
            map.put("identity", tec_identity);
            presenter.SearchTeacher(map);
        }
    }

    //下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", spec);
        map.put("identity", tec_identity);
        if (!search_key.equals("")) {
            map.put("key", search_key);
        }
        presenter.RefreshData(map);

        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    //刷新成功
    @Override
    public void SuccessRefresh(List<TecInfoBean> tecInfoBeanList) {
        loadingDialog.dismiss();
        listData.clear();
        listData.addAll(tecInfoBeanList);
        //获取从测评页已选择的老师信息
        listInfo = (List) getIntent().getStringArrayListExtra("teacher_list");
        if (listInfo.size() == 0) {
            TAG = true;
        } else {
            TAG = false;
            isClickNum = listInfo.size();

            for (int i = 0; i < listInfo.size(); i++) {
                TecInfoBean tecInfoBean = listInfo.get(i);
                for (int n = 0; n < listData.size(); n++) {
                    TecInfoBean tecInfoBean1 = listData.get(n);
                    if (tecInfoBean.getTec_id() == tecInfoBean1.getTec_id()) {
                        listData.set(n, tecInfoBean);
                        break;
                    }
                }
            }
            teacherGridViewAdapter = new ValuationGridViewAdapter(ValuationChooseTeacher.this, listInfo);
            gvTeacher.setAdapter(teacherGridViewAdapter);
        }

        teacherListViewAdapter = new ValuationListViewAdapter(this, listData, isClickNum, new ValuationListViewAdapter.CallBack() {
            @Override
            public void callbackAdd(int misClickNum, TecInfoBean tecInfoBean) {
                listInfo.add(tecInfoBean);
                isClickNum = misClickNum;
                if (listInfo.size() == 1 || !TAG) {
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

    //搜索成功
    @Override
    public void SuccessSearch(List<TecInfoBean> tecInfoBeanList) {

        listData.clear();
        listData.addAll(tecInfoBeanList);
        for (int i = 0; i < listInfo.size(); i++) {
            TecInfoBean tecInfoBean = listInfo.get(i);
            for (int n = 0; n < listData.size(); n++) {
                TecInfoBean tecInfoBean1 = listData.get(n);
                if (tecInfoBean.getTec_id() == tecInfoBean1.getTec_id()) {
                    listData.set(n, tecInfoBean);
                    break;
                }
            }
        }
        teacherListViewAdapter.Refresh(listData.size());
        teacherListViewAdapter.notifyDataSetChanged();
    }

    //搜索失败
    @Override
    public void FailureSearch(String error_msg) {

        if (error_msg.equals("20007")) {
            UIUtil.ToastshowShort(getApplicationContext(), "没有找到相关内容");
        }else {
            UIUtil.ToastshowShort(getApplicationContext(),error_msg);
        }
    }
    //刷新失败
    @Override
    public void FailureRefresh(String error_msg) {

        UIUtil.ToastshowShort(getApplicationContext(), error_msg);
        refreshView.refreshFinish(PullToRefreshLayout.FAIL);
    }

    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        self_id = teacherListViewAdapter.self_id();
        UIUtil.showLog("self_id", self_id + "");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("spec", spec);
        map.put("self", self_id);
        map.put("identity", tec_identity);
        if (!search_key.equals("")) {
            map.put("key", search_key);
        }
        presenter.LoadData(map);

    }

    //上拉加载成功
    @Override
    public void SuccessLoad(List<TecInfoBean> tecBeanList) {
        UIUtil.showLog("SuccessLoad", tecBeanList.size() + "--");
        listData.addAll(tecBeanList);
        teacherListViewAdapter.Refresh(listData.size());
        teacherListViewAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    //加载失败
    @Override
    public void FailureLoad(int flag) {
        switch (flag) {
            case 0:
                refreshResult = PullToRefreshLayout.EMPTY;
                break;
            case 1:
                refreshResult = PullToRefreshLayout.FAIL;
                break;
            case 2:
                refreshResult = PullToRefreshLayout.FAIL;
                UIUtil.ToastshowShort(this, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(refreshResult);
            }
        }.sendEmptyMessageDelayed(0, 1000);

    }




}
