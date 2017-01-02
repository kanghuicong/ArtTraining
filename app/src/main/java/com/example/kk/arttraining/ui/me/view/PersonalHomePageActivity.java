package com.example.kk.arttraining.ui.me.view;

import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.custom.view.ShowImageView;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.discover.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.me.bean.UserCountBean;
import com.example.kk.arttraining.ui.me.presenter.PersonalHomePagePresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/9 20:20
 * 说明:个人主页activity
 */
public class PersonalHomePageActivity extends BaseActivity implements IPersonalHomePageActivity, BottomPullSwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener, DynamicAdapter.MusicCallBack {


    @InjectView(R.id.lv_me_personal_page)
    ListView lvMePersonalPage;
    @InjectView(R.id.idme_personal_swipe)
    BottomPullSwipeRefreshLayout idmePersonalSwipe;

    ImageView userHeader;
    TextView meTvPhoneNum;
    TextView meTvCity;
    TextView meTvGrade;
    TextView meTvSchoolName;
    LinearLayout meLlUserinfo;
    TextView meTvTopicNum;
    LinearLayout meLlTopic;
    TextView meTvFocusNum;
    LinearLayout meLlFoucs;
    TextView meTvFansNum;
    LinearLayout meLlFans;
    TextView meTvGroupNum;
    LinearLayout meLlGroup;
    PlayAudioUtil playAudioUtil = null;
    int MusicPosition = -5;
    AnimatorSet MusicArtSet = null;
    AnimationDrawable MusicAnim;

    TextView tv_foucs;

    private PersonalHomePagePresenter presenter;
    private Dialog dialog;
    private int uid;
    private List<PersonalPageFragment> fragmentList;
    //当前选中的项
    int currenttab = -1;
    private PersonalPageFragment StatusesFragment;
    private PersonalPageFragment WorksFragment;
    // Listview头部
    private View head_view;
    private DynamicAdapter dynamicAdapter;

    private int self_id;
    private Boolean Refresh_First_flag = true;
    private List<Map<String, Object>> StatusesMapList;

    private BottomPullSwipeRefreshLayout swipeRefreshLayout;
    private boolean ADD_HEADER_FIRST = true;


    private int user_id;//用户类型

    private String user_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_personal_page);
        ButterKnife.inject(this);
        init();
    }

    //初始化
    @Override
    public void init() {
        head_view = View.inflate(this, R.layout.me_personal_page_headview, null);
        userHeader = (ImageView) head_view.findViewById(R.id.user_header);
        meTvCity = (TextView) head_view.findViewById(R.id.me_tv_city);
        meTvGrade = (TextView) head_view.findViewById(R.id.me_tv_grade);
        meTvSchoolName = (TextView) head_view.findViewById(R.id.me_tv_schoolName);
        meTvTopicNum = (TextView) head_view.findViewById(R.id.me_tv_topicNum);
        meTvFocusNum = (TextView) head_view.findViewById(R.id.me_tv_focusNum);
        meTvFansNum = (TextView) head_view.findViewById(R.id.me_tv_fansNum);
        meTvGroupNum = (TextView) head_view.findViewById(R.id.me_tv_groupNum);
        meTvPhoneNum = (TextView) head_view.findViewById(R.id.me_tv_phoneNum);
        tv_foucs = (TextView) head_view.findViewById(R.id.tv_foucs);

        meLlTopic = (LinearLayout) head_view.findViewById(R.id.me_ll_topic);
        meLlFoucs = (LinearLayout) head_view.findViewById(R.id.me_ll_foucs);
        meLlGroup = (LinearLayout) head_view.findViewById(R.id.me_ll_group);
        meLlFans = (LinearLayout) head_view.findViewById(R.id.me_ll_fans);

        meLlFans.setOnClickListener(this);
        meLlFoucs.setOnClickListener(this);
        userHeader.setOnClickListener(this);


        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
//        uid = Config.UID;
        presenter = new PersonalHomePagePresenter(this);
        dialog = DialogUtils.createLoadingDialog(this, "");

        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(getApplicationContext());
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) findViewById(R.id.idme_personal_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.autoRefresh();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_ll_foucs:

                Intent intent = new Intent(this, FansActivity.class);
                intent.putExtra("type", "foucs");
                intent.putExtra("uid", uid);
                startActivity(intent);
                break;
            case R.id.me_ll_fans:
                Intent intentFans = new Intent(this, FansActivity.class);
                intentFans.putExtra("type", "fans");
                intentFans.putExtra("uid", uid);
                startActivity(intentFans);
                break;
            case R.id.tv_foucs:
                if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("utype", Config.USER_TYPE);
                    map.put("type", Config.USER_TYPE);
                    map.put("follow_id", user_id);
                    presenter.FoucsRequest(map);
                }
                break;

            case R.id.user_header:
                Intent intentImage=new Intent(this, ShowImageView.class);
                intentImage.putExtra("image_path",user_pic);
                startActivity(intentImage);
                overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_exit_anim);
            break;


        }

    }

    //获取用户信息
    @Override
    public void getUserInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("login_id", Config.UID);
        map.put("login_type", Config.USER_TYPE);
        presenter.getUserInfoData(map);
    }

    //获取用户统计信息
    @Override
    public void getUserCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);

        presenter.getUserCount(map);
    }

    //获取用户帖子动态
    @Override
    public void getUserStatuses() {
//        presenter.getUserInfoData();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("utype", Config.USER_TYPE);
        presenter.getUserStatuses(map);

    }

    //获取用户作品
    @Override
    public void getUserWorks() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("uid", uid);
//        map.put("access_token", Config.ACCESS_TOKEN);
    }

    @Override
    public void RefreshData() {

        if (Refresh_First_flag) {
            //获取用户信息
            getUserInfo();
            //获取用户统计数据
            getUserCount();
            //获取用户帖子
            getUserStatuses();
        } else {
            getUserStatuses();
        }
    }

    //上拉加载数据
    @Override
    public void LoadData() {
        self_id = dynamicAdapter.getSelfId();
        Map<String, Object> map = new HashMap<String, Object>();
        if(Config.ACCESS_TOKEN!=null&&!Config.ACCESS_TOKEN.equals(""))
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", uid);
        map.put("utype", Config.USER_TYPE);
        map.put("self", self_id);
        presenter.LoadData(map);
    }

    //获取用户信息成功
    @Override
    public void SuccessUserInfo(UserLoginBean userLoginBean) {
        user_pic=userLoginBean.getHead_pic();
        Glide.with(getApplicationContext()).load(userLoginBean.getHead_pic()).error(R.mipmap.default_user_header).transform(new GlideCircleTransform(this)).into(userHeader);
        UIUtil.showLog("PersonalPatgeActivity-->", userLoginBean.toString());
        if (userLoginBean.getIs_follow().equals("yes")) {
            tv_foucs.setText("已关注");
        } else {
            tv_foucs.setText("关注");
            tv_foucs.setOnClickListener(this);
        }
        user_id = userLoginBean.getUid();
        meTvCity.setText(userLoginBean.getCity());
        meTvGrade.setText(userLoginBean.getIdentity());
        meTvSchoolName.setText(userLoginBean.getSchool());
        meTvPhoneNum.setText(userLoginBean.getName());
    }

    //用户用户统计成功
    @Override
    public void SuccessCount(UserCountBean userCountBean) {
        meTvFansNum.setText(userCountBean.getFans_num() + "");
        meTvFocusNum.setText(userCountBean.getFollow_num() + "");
        meTvGroupNum.setText(userCountBean.getGroup_num() + "");
        meTvTopicNum.setText(userCountBean.getBbs_num() + "");
    }

    //获取用户动态成功
    @Override
    public void SuccessStatuses(List<Map<String, Object>> mapList) {

    }

    @Override
    public void SuccessWorks(List<Map<String, Object>> mapList) {

    }

    //关注成功
    @Override
    public void SuccessFoucs() {
        UIUtil.ToastshowShort(getApplicationContext(), "关注成功");

    }

    //刷新成功
    @Override
    public void SuccessRefresh(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setRefreshing(false);
        StatusesMapList = mapList;
        if (mapList.size() >= 2) {
            swipeRefreshLayout.setOnLoadListener(this);
        }
        if (Refresh_First_flag) {
            if (ADD_HEADER_FIRST) {
                lvMePersonalPage.addHeaderView(head_view);
                ADD_HEADER_FIRST = false;
            }

            dynamicAdapter = new DynamicAdapter(this, StatusesMapList, this, "personal");
            lvMePersonalPage.setAdapter(dynamicAdapter);
            Refresh_First_flag = false;

            lvMePersonalPage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            // 触摸移动时的操作
                            if (MusicPosition != -5) {
                                if (lvMePersonalPage.getFirstVisiblePosition() - 2 >= MusicPosition || lvMePersonalPage.getLastVisiblePosition() <= MusicPosition) {
                                    UIUtil.showLog("MusicStart", "onScroll");
                                    MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
                                }
                            }
                            break;
                    }
                    return false;
                }
            });
        } else {
            dynamicAdapter.notifyDataSetChanged();
        }

    }

    //上拉加载成功
    @Override
    public void SuccessLoad(List<Map<String, Object>> mapList) {
        swipeRefreshLayout.setLoading(false);
        StatusesMapList.addAll(mapList);
        dynamicAdapter.changeCount(StatusesMapList.size());
        dynamicAdapter.notifyDataSetChanged();

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
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setLoading(false);
        switch (error_code) {
            case "20007":
                StatusesMapList = new ArrayList<Map<String, Object>>();
                if(ADD_HEADER_FIRST){
                    lvMePersonalPage.addHeaderView(head_view);
                    ADD_HEADER_FIRST=false;
                }

                dynamicAdapter = new DynamicAdapter(this, StatusesMapList, this);
                lvMePersonalPage.setAdapter(dynamicAdapter);
                Refresh_First_flag = false;
                UIUtil.ToastshowShort(this, "没有更多内容哦");
                break;
        }
    }

    @Override
    public void FailureWoreks(String error_code) {

    }

    @Override
    public void OnFailure(String error_msg) {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setLoading(false);
        switch (error_msg) {
            case "20007":
                UIUtil.ToastshowShort(this, "没有更多内容哦");
                break;
            case "404":
                UIUtil.ToastshowShort(this, getResources().getString(R.string.connection_timeout));
                break;
            case "400":
                UIUtil.ToastshowShort(this, getResources().getString(R.string.connection_timeout));
                break;
        }

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }


    @Override
    public void onLoad() {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
        LoadData();
    }

    @Override
    public void onRefresh() {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
        RefreshData();
    }

    @Override
    public void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, AnimationDrawable MusicAnim, int position) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = position;
        this.MusicArtSet = MusicArtSet;
        this.MusicAnim = MusicAnim;
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
    }
}
