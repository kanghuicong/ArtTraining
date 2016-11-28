package com.example.kk.arttraining.ui.homePage.function.institution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.activity.ThemeInstitutionContent;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.prot.IInstitutionList;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitution3 extends Activity implements IInstitutionList,PullToRefreshLayout.OnRefreshListener{
    @InjectView(R.id.lv_institution)
    MyListView lvInstitution;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    View view_header;
    ThemeInstitutionListData themeInstitutionAllData = new ThemeInstitutionListData(this);
    List<OrgBean> orgBeanList = new ArrayList<OrgBean>();
    boolean Flag = false;
    InstitutionFragmentAdapter adapter;
    int institution_num = 0;
    String province = "广东";
    int refreshResult = PullToRefreshLayout.FAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_institution_fragment);
        ButterKnife.inject(this);
//        view_header = (View) findViewById(R.id.ll_refresh_header);
//        view_header.setVisibility(View.GONE);

//        ThemeInstitutionUntil.themeInstitutionUntil(this,lvInstitution,"");
        themeInstitutionAllData.getThemeInstitutionListData(province);

        refreshView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        UIUtil.showLog("onLoadMore","222");
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        UIUtil.showLog("onLoadMore","111");
        if (Flag) {
            UIUtil.showLog("onLoadMore","123");
            themeInstitutionAllData.loadTeacherListAllData(adapter.getSelfId(),province);
        }
    }

    @Override
    public void getInstitutionList(final List<OrgBean> orgBeanList1) {
        Flag = true;
        institution_num = orgBeanList1.size();
        orgBeanList.addAll(orgBeanList1);
        adapter = new InstitutionFragmentAdapter(ThemeInstitution3.this, orgBeanList);
        lvInstitution.setAdapter(adapter);
        lvInstitution.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrgBean orgBean = orgBeanList.get(position);
                Intent intent = new Intent(ThemeInstitution3.this, ThemeInstitutionContent.class);
                intent.putExtra("org_id", orgBean.getOrg_id()+"");
                intent.putExtra("name", orgBean.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnInstitutionListFailure(String result) {
        UIUtil.ToastshowShort(this, result);
    }

    @Override
    public void loadInstitutionList(List<OrgBean> orgBeanList1) {
        orgBeanList.addAll(orgBeanList1);
        institution_num = institution_num + orgBeanList1.size();
        adapter.changeCount(institution_num);
        adapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadInstitutionListFailure(int result) {
        switch (result) {
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
