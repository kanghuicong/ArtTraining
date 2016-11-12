package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.bean.CollectBean;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.presenter.PersonalHomePagePresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/9 20:20
 * 说明:个人主页activity
 */
public class PersonalHomePageActivity extends BaseActivity implements IPersonalHomePageActivity {

    @InjectView(R.id.user_header)
    ImageView userHeader;
    @InjectView(R.id.me_tv_phoneNum)
    TextView meTvPhoneNum;
    @InjectView(R.id.me_tv_city)
    TextView meTvCity;
    @InjectView(R.id.me_tv_grade)
    TextView meTvGrade;
    @InjectView(R.id.me_tv_schoolName)
    TextView meTvSchoolName;
    @InjectView(R.id.me_ll_userinfo)
    LinearLayout meLlUserinfo;
    @InjectView(R.id.me_tv_topicNum)
    TextView meTvTopicNum;
    @InjectView(R.id.me_ll_topic)
    LinearLayout meLlTopic;
    @InjectView(R.id.me_tv_focusNum)
    TextView meTvFocusNum;
    @InjectView(R.id.me_ll_foucs)
    LinearLayout meLlFoucs;
    @InjectView(R.id.me_tv_fansNum)
    TextView meTvFansNum;
    @InjectView(R.id.me_ll_fans)
    LinearLayout meLlFans;
    @InjectView(R.id.me_tv_groupNum)
    TextView meTvGroupNum;
    @InjectView(R.id.me_ll_group)
    LinearLayout meLlGroup;
    @InjectView(R.id.lv_personal_page)
    MyListView lvPersonalPage;
    @InjectView(R.id.personal_page_swipe_ly)
    BottomPullSwipeRefreshLayout personalPageSwipeLy;
    @InjectView(R.id.rb_works)
    RadioButton rb_works;
    @InjectView(R.id.rb_topic)
    RadioButton rb_topic;

    private PersonalHomePagePresenter presenter;
    private Dialog dialog;
    private String uid;
    private List<PersonalPageFragment> fragmentList;
    //当前选中的项
    int currenttab = -1;
    private PersonalPageFragment StatusesFragment;
    private PersonalPageFragment WorksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_personal_home_page);
        ButterKnife.inject(this);
    }

    //初始化
    @Override
    public void init() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        presenter = new PersonalHomePagePresenter(this);
        dialog = DialogUtils.createLoadingDialog(this, "");
    }

    //点击事件
    @OnClick({R.id.rb_works,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_works:

                break;
            case R.id.rb_topic:

                break;
        }

    }

    //获取用户信息
    @Override
    public void getUserInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
        presenter.getUserInfoData(map);
    }

    //获取用户统计信息
    @Override
    public void getUserCount() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
        presenter.getUserCount(map);
    }

    //获取用户帖子动态
    @Override
    public void getUserStatuses() {
//        presenter.getUserInfoData();
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
        presenter.getUserStatuses(map);

    }

    //获取用户作品
    @Override
    public void getUserWorks() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
    }

    //获取用户信息成功
    @Override
    public void SuccessUserInfo(UserLoginBean userLoginBean) {

    }

    //用户用户统计成功
    @Override
    public void SuccessCount(UserCountBean userCountBean) {

    }

    //获取用户动态成功
    @Override
    public void SuccessStatuses(List<Map<String, Object>> mapList) {
    }

    @Override
    public void SuccessWorks(List<Map<String, Object>> mapList) {

    }

    //获取用户信息失败
    @Override
    public void FailureUserInfo(String error_code) {

    }

    //获取用户统计失败
    @Override
    public void FailureCount(String error_code) {

    }

    //获取用户动态失败
    @Override
    public void FailureStatuses(String error_code) {

    }

    @Override
    public void FailureWoreks(String error_code) {

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }





}
