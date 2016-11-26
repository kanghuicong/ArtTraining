package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.sqlite.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.search.DoSearchData;
import com.example.kk.arttraining.ui.homePage.function.search.HistorySearch;
import com.example.kk.arttraining.ui.homePage.function.search.HotSearch;
import com.example.kk.arttraining.ui.homePage.function.search.SearchTextChangedListener;
import com.example.kk.arttraining.ui.homePage.prot.ISearch;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.KeyBoardUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 */
public class SearchMain extends HideKeyboardActivity implements ISearch, PullToRefreshLayout.OnRefreshListener {
    @InjectView(R.id.iv_search_title_back)
    ImageView ivSearchTitleBack;
    @InjectView(R.id.ed_search_content)
    EditText edSearchContent;
    @InjectView(R.id.bt_search)
    Button btSearch;
    @InjectView(R.id.gv_search_hot)
    GridView gvSearchHot;
    @InjectView(R.id.ll_search_hot)
    LinearLayout llSearchHot;
    @InjectView(R.id.lv_search_history)
    ListView lvSearchHistory;
    @InjectView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @InjectView(R.id.ll_search_clear_history)
    LinearLayout llSearchClearHistory;
    @InjectView(R.id.lv_search)
    ListView lvSearch;

    String type;
    String search_content;
    InstitutionFragmentAdapter institutionAdapter;
    List<OrgBean> orgBeanList = new ArrayList<OrgBean>();
    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
    ThemeTeacherAdapter teacherAdapter;
    String Flag = "none";
    DoSearchData doSearchInstitutionData;
    DoSearchData doSearchTeacherData;
    int teacher_num;
    int institution_num;

    @InjectView(R.id.tv_default_search)
    TextView tvDefaultSearch;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);

        refreshView.setOnRefreshListener(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (type.equals("homepage")) {

        } else {
            llSearchHistory.setVisibility(View.GONE);
            llSearchClearHistory.setVisibility(View.GONE);
            llSearchHot.setVisibility(View.GONE);
        }

        SearchTextChangedListener.SearchTextListener(edSearchContent, btSearch);//监听搜索内容变化

        HotSearch.GetHotSearch(this, gvSearchHot);//热门搜索

        HistorySearch.GetHistorySearch(this, lvSearchHistory, llSearchClearHistory);//历史搜索

//        DoSearch.KeySearch(this, edSearchContent, lvSearch);//修改键盘搜索键及该搜索键点击事件
        KeySearch();//修改键盘搜索键及该搜索键点击事件

        AutomaticKeyboard.GetClick(this, edSearchContent);//自动弹出键盘

    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back://返回
                finish();
                break;
            case R.id.bt_search://搜索按钮
                doSearch();
                break;
        }
    }

    public void doSearch() {
        search_content = edSearchContent.getText().toString();
        if (search_content.equals("")) {
            UIUtil.ToastshowShort(this, "请输入搜索内容");
        } else {
            switch (type) {
                case "school":
//                    DoSearchData doSearchSchoolData = new DoSearchData(this);
//                    doSearchSchoolData.getSchoolSearchData(search_content);
                    break;
                case "institution":
                    doSearchInstitutionData = new DoSearchData(this);
                    doSearchInstitutionData.getInstitutionSearchData(search_content);
                    break;
                case "teacher":
                    doSearchTeacherData = new DoSearchData(this);
                    doSearchTeacherData.getTeacherSearchData(search_content);
                    break;
            }

            SearchDao dao = new SearchDao(this);
            Boolean result = dao.contrastData(Config.User_Id, search_content);//判断搜索内容是否已经存在
            if (!result) {
                dao.addData(Config.User_Id, search_content);
            }
            edSearchContent.setText("");
        }
    }

    public void KeySearch() {
        edSearchContent.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 修改回车键功能
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    KeyBoardUtils.closeKeybord(edSearchContent, SearchMain.this);
                    doSearch();
                }
                return false;
            }
        });
    }


    @Override
    public void getDoSearchData(List<Map<String, Object>> mapList) {

    }

    @Override
    public void OnFailure(String error_code) {

    }

    //机构搜索
    @Override
    public void getInstitutionSearch(List<OrgBean> orgBeanList1) {
        lvSearch.setVisibility(View.VISIBLE);
        llSearchHistory.setVisibility(View.GONE);
        tvDefaultSearch.setVisibility(View.GONE);
        Flag = "institution";
        if (orgBeanList.size() == 0) {
            orgBeanList.addAll(orgBeanList1);
            institutionAdapter = new InstitutionFragmentAdapter(this, orgBeanList);
            lvSearch.setAdapter(institutionAdapter);
            institution_num = orgBeanList.size();
        } else {
            orgBeanList.clear();
            orgBeanList.addAll(orgBeanList1);
            institutionAdapter.changeCount(orgBeanList1.size());
            institutionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadInstitutionSearch(List<OrgBean> orgBeanList1) {
        orgBeanList.addAll(orgBeanList1);
        institution_num = institution_num + orgBeanList1.size();
        institutionAdapter.changeCount(institution_num);
        institutionAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }


    //老师搜索
    @Override
    public void getTeacherSearch(List<TecInfoBean> tecInfoBeanList1) {
        lvSearch.setVisibility(View.VISIBLE);
        llSearchHistory.setVisibility(View.GONE);
        tvDefaultSearch.setVisibility(View.GONE);
        Flag = "teacher";
        if (tecInfoBeanList.size() == 0) {
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherAdapter = new ThemeTeacherAdapter(this, tecInfoBeanList);
            lvSearch.setAdapter(teacherAdapter);
            teacher_num = tecInfoBeanList.size();
        } else {
            tecInfoBeanList.clear();
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherAdapter.ChangeCount(tecInfoBeanList1.size());
            teacherAdapter.notifyDataSetChanged();
        }
    }

    //上拉老师搜索
    @Override
    public void loadTeacherSearch(List<TecInfoBean> tecInfoBeanList1) {
        tecInfoBeanList.addAll(tecInfoBeanList1);
        teacher_num = teacher_num + tecInfoBeanList1.size();
        teacherAdapter.ChangeCount(teacher_num);
        teacherAdapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    //上拉老师搜索失败
    @Override
    public void OnLoadFailure(String result) {
        UIUtil.ToastshowShort(this, result);
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void OnSearchEmpty(String result) {
        tvDefaultSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnSearchFailure(String result) {
        UIUtil.ToastshowShort(this, result);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        switch (Flag) {
            case "teacher":
                doSearchTeacherData.loadTeacherSearchData(search_content, teacherAdapter.getSelfId());
                break;
            case "institution":
                doSearchInstitutionData.loadInstitutionSearchData(search_content, institutionAdapter.getSelfId());
                break;
            case "none":
                if (refreshView != null) {
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                        }
                    }.sendEmptyMessageDelayed(0, 3000);
                }
                break;
        }
    }
}
