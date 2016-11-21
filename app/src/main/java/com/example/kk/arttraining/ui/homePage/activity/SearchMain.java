package com.example.kk.arttraining.ui.homePage.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.sqlite.dao.SearchDao;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.ui.homePage.function.search.DoSearch;
import com.example.kk.arttraining.ui.homePage.function.search.DoSearchData;
import com.example.kk.arttraining.ui.homePage.function.search.HistorySearch;
import com.example.kk.arttraining.ui.homePage.function.search.HotSearch;
import com.example.kk.arttraining.ui.homePage.function.search.SearchTextChangedListener;
import com.example.kk.arttraining.ui.homePage.prot.ISearch;
import com.example.kk.arttraining.utils.AutomaticKeyboard;
import com.example.kk.arttraining.utils.Config;
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
public class SearchMain extends HideKeyboardActivity implements ISearch {
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
    InstitutionFragmentAdapter institutionAdapter;
    List<OrgBean> orgBeanList = new ArrayList<OrgBean>();
    @InjectView(R.id.tv_default_search)
    TextView tvDefaultSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_search);
        ButterKnife.inject(this);

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

        DoSearch.KeySearch(this, edSearchContent, lvSearch);//修改键盘搜索键及该搜索键点击事件

        AutomaticKeyboard.GetClick(this, edSearchContent);//自动弹出键盘

    }

    @OnClick({R.id.iv_search_title_back, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_title_back://返回
                finish();
                break;
            case R.id.bt_search://搜索按钮
//                DoSearch.doSearch(SearchMain.this, edSearchContent, lvSearch);
                doSearch();
                break;
        }
    }

    public void doSearch() {
        String search_content = edSearchContent.getText().toString();
        if (search_content.equals("")) {
            UIUtil.ToastshowShort(this, "请输入搜索内容");
        } else {
            switch (type) {
                case "school":


                    break;
                case "institution":
                    DoSearchData doSearchData = new DoSearchData(this);
                    doSearchData.getInstitutionSearchData(search_content);
                    break;
            }

            SearchDao dao = new SearchDao(this);
            Boolean result = dao.contrastData(Config.User_Id, search_content);//判断搜索内容是否已经存在
            if (!result) {
                dao.addData(Config.User_Id, search_content);
            }
            UIUtil.ToastshowShort(this, search_content);
            edSearchContent.setText("");
        }
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
        if (orgBeanList.size() == 0) {
            orgBeanList.addAll(orgBeanList1);
            institutionAdapter = new InstitutionFragmentAdapter(this, orgBeanList);
            lvSearch.setAdapter(institutionAdapter);
        } else {
            orgBeanList.clear();
            orgBeanList.addAll(orgBeanList1);
            institutionAdapter.changeCount(orgBeanList1.size());
            institutionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnInstitutionSearchEmpty(String result) {
        tvDefaultSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnInstitutionSearchFailure(String result) {

    }
}
