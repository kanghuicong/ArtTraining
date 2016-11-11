package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.AssessmentsBean;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.HelpBean;
import com.example.kk.arttraining.bean.parsebean.ActivityList;
import com.example.kk.arttraining.bean.parsebean.ActivityShow;
import com.example.kk.arttraining.bean.parsebean.AdvertListBean;
import com.example.kk.arttraining.bean.parsebean.AssessmentsListBean;
import com.example.kk.arttraining.bean.parsebean.HeadNewsListBean;
import com.example.kk.arttraining.bean.parsebean.HelpListBean;
import com.example.kk.arttraining.bean.parsebean.LikeListPic;
import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.ui.homePage.bean.SearchHomepagerBean;
import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/10/20 14:42
 * 说明:通用的网络请求接口
 */
public interface CommonRequestApi {

    //获取测评列表

    @POST(Config.URL_ASSESSMENTS_LIST)
    @FormUrlEncoded
    Call<AssessmentsListBean> assessmentsList(@FieldMap Map<String, String> map);

    //获取测评详细内容

    @POST(Config.URL_ASSESSMENTS_SHOW)
    @FormUrlEncoded
    Call<AssessmentsBean> assessmentsDetail(@FieldMap Map<String, String> map);

    //首页综合搜索
    @POST(Config.URL_SEARCH_PUBLIC)
    @FormUrlEncoded
    Call<SearchHomepagerBean> searchPublic(@FieldMap Map<String, String> map);

    //根据关键字搜索机构
    @POST(Config.URL_SEARCH_ORG)
    @FormUrlEncoded
    Call<SearchBean> searchOrg(@FieldMap Map<String, String> map);

    //根据关键字搜索老师
    @POST(Config.URL_SEARCH_TEC)
    @FormUrlEncoded
    Call<SearchBean> searchTec(@FieldMap Map<String, String> map);

    //根据关键字搜索动态
    @POST(Config.URL_SEARCH_STATUSES)
    @FormUrlEncoded
    Call<SearchBean> searchStatuses(@FieldMap Map<String, String> map);

    //根据关键字搜索小组
    @POST(Config.URL_SEARCH_GROUP)
    @FormUrlEncoded
    Call<SearchBean> searchGroup(@FieldMap Map<String, String> map);


    //获取省份列表
    @POST(Config.URL_COMMON_PROVINCE)
    @FormUrlEncoded
    Call<ParseProvinceListBean> locationProvince(@FieldMap Map<String, String> map);

    //获取城市列表
    @POST(Config.URL_SEARCH_CITY)
    @FormUrlEncoded
    Call<ParseLocationBean> locationCity(@FieldMap Map<String, String> map);

    //获取机构列表
    @POST(Config.URL_ORG_LIST)
    @FormUrlEncoded
    Call<OrgListBean> orgList(@FieldMap Map<String, String> map);

    //获取机构详情信息
    @POST(Config.URL_ORG_SHOW)
    @FormUrlEncoded
    Call<OrgShowBean> orgDetail(@FieldMap Map<String, String> map);

    //获取名师列表
    @POST(Config.URL_TECHER_LIST)
    @FormUrlEncoded
    Call<TecherList> techerList(@FieldMap Map<String, String> map);

    //首页测评权威
    @POST(Config.URL_TECHER_LIST_INDEX)
    @FormUrlEncoded
    Call<TecherList> techerListIndex(@FieldMap Map<String, String> map);

    //获取名师信息
    @POST(Config.URL_TECHER_SHOW)
    @FormUrlEncoded
    Call<TecherShow> techerDetail(@FieldMap Map<String, String> map);

    //获取活动列表
    @POST(Config.URL_ACTIVITYIES_LIST)
    @FormUrlEncoded
    Call<ActivityList> activityList(@FieldMap Map<String, String> map);

    //获取活动详情
    @POST(Config.URL_ACTIVITYIES_SHOW)
    @FormUrlEncoded
    Call<ActivityShow> activityDetail(@FieldMap Map<String, String> map);

    //获取活动列表
    @POST(Config.URL_HELP_LIST)
    @FormUrlEncoded
    Call<HelpListBean> helpList(@FieldMap Map<String, String> map);

    //获取活动详情
    @POST(Config.URL_HELP_SHOW)
    @FormUrlEncoded
    Call<HelpBean> helpDetail(@FieldMap Map<String, String> map);

    //发送建议反馈
    @POST(Config.URL_RECOMMEND_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> recommendCreate(@FieldMap Map<String, String> map);

    //获取头条列表
    @POST(Config.URL_INFORMATION_LIST)
    @FormUrlEncoded
    Call<HeadNewsListBean> headnewsList(@Field("access_token") String access_token);

    //获取头条详情
    @POST(Config.URL_INFORMATION_SHOW)
    @FormUrlEncoded
    Call<HeadNews> headnewsDetail(@FieldMap Map<String, String> map);

    //获取广告列表
    @POST(Config.URL_ADVERTISING_LIST)
    @FormUrlEncoded
    Call<AdvertListBean> advertisList(@FieldMap Map<String, String> map);

    //获取广告详情
    @POST(Config.URL_ADVERTISING_SHOW)
    @FormUrlEncoded
    Call<AdvertisBean> advertisDetail(@FieldMap Map<String, String> map);

}
