package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.parsebean.GroupCreateBean;
import com.example.kk.arttraining.bean.parsebean.GroupJoinExitBean;
import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.bean.parsebean.GroupListMyBean;
import com.example.kk.arttraining.bean.parsebean.UsersListBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/10/19 17:03
 * 说明:
 */
public interface GroupRequestApi {
    //获取小组列表
    @POST(Config.URL_GROUP_LIST)
    @FormUrlEncoded
    Call<GroupListBean> groupList(@FieldMap Map<String, String> map);

    //获取我的小组列表
    @POST(Config.URL_GROUP_LIST_MY)
    @FormUrlEncoded
    Call<GroupListBean> groupMyList(@FieldMap Map<String, Object> map);

    //获取小组详情信息
    @POST(Config.URL_GROUP_SHOW)
    @FormUrlEncoded
    Call<String> groupDetailInfo(@FieldMap Map<String, String> map);

    //获取小组成员列表
    @POST(Config.URL_GROUP_USERS)
    @FormUrlEncoded
    Call<UsersListBean> groupUsers(@FieldMap Map<String, String> map);

    //用户创建小组
    @POST(Config.URL_GROUP_CREATE)
    @FormUrlEncoded
    Call<GroupCreateBean> groupCreate(@FieldMap Map<String, String> map);

    //用户加入小组
    @POST(Config.URL_GROUP_JOIN)
    @FormUrlEncoded
    Call<GroupJoinExitBean> groupJoin(@FieldMap Map<String, String> map);

    @POST(Config.URL_GROUP_EXIT)
    @FormUrlEncoded
    Call<GroupJoinExitBean> groupExit(@FieldMap Map<String, String> map);
}
