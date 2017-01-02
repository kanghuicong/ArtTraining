package com.example.kk.arttraining.ui.homePage.function.institution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ThemeInstitutionAll extends Fragment implements IInstitutionList, PullToRefreshLayout.OnRefreshListener {

    ThemeInstitutionListData themeInstitutionAllData = new ThemeInstitutionListData(this);
    List<OrgBean> orgBeanList = new ArrayList<OrgBean>();
    boolean Flag = false;
    InstitutionFragmentAdapter adapter;
    int institution_num = 0;
    int refreshResult = PullToRefreshLayout.FAIL;

    Activity activity;
    View view;
    String type;
    @InjectView(R.id.lv_institution)
    MyListView lvInstitution;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();

        if (view == null) {
            view = View.inflate(activity, R.layout.homepage_institution_fragment, null);
            ButterKnife.inject(this, view);
            type = getArguments().getString("type");
            themeInstitutionAllData.getThemeInstitutionListData(type);
            refreshView.setOnRefreshListener(this);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        if (Flag) {
            themeInstitutionAllData.loadTeacherListAllData(adapter.getSelfId(), type);
        }
    }

    @Override
    public void getInstitutionList(final List<OrgBean> orgBeanList1) {
        Flag = true;
        institution_num = orgBeanList1.size();
        orgBeanList.addAll(orgBeanList1);
        adapter = new InstitutionFragmentAdapter(activity, orgBeanList);
        lvInstitution.setAdapter(adapter);
        lvInstitution.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrgBean orgBean = orgBeanList.get(position);
                Intent intent = new Intent(activity, ThemeInstitutionContent.class);
                intent.putExtra("org_id", orgBean.getOrg_id() + "");
                intent.putExtra("name", orgBean.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnInstitutionListFailure(String result) {
        UIUtil.ToastshowShort(activity, result);
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
                UIUtil.ToastshowShort(activity, "网络连接失败！");
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
