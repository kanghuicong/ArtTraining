package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.CommentsListBean;
import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.bean.parsebean.LikeListBean;
import com.example.kk.arttraining.bean.parsebean.LikeListPic;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.bean.parsebean.TecCommentsList;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/10/20 11:01
 * 说明:
 */
public interface StatusesRequestApi {
    //获取动态（精品动态）列表
    @POST(Config.testapi)
    @FormUrlEncoded
    Call<StatusesBean> statusesGoodList(@FieldMap Map<String, String> map);

    //获取用户动态列表
    @POST(Config.URL_STATUSES_USER_TIMELINE)
    @FormUrlEncoded
    Call<StatusesBean> statusesUserList(@FieldMap Map<String, String> map);

    //获取动态详情
    @POST(Config.URL_STATUSES_SHOW)
    @FormUrlEncoded
    Call<StatusesDetailBean> statusesDetail(@FieldMap Map<String, String> map);

    //发布动态
    @POST(Config.URL_STATUSES_REPORT)
    @FormUrlEncoded
    Call<GeneralBean> statusesPublish(@FieldMap Map<String, String> map);

    //转发动态
    @POST(Config.URL_STATUSES_PUBLISH)
    @FormUrlEncoded
    Call<GeneralBean> statusesReport(@FieldMap Map<String, String> map);

    //删除动态
    @POST(Config.URL_STATUSES_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesDelete(@FieldMap Map<String, String> map);


    //获取动态的评论列表
    @POST(Config.URL_COMMENTS_LIST)
    @FormUrlEncoded
    Call<CommentsListBean> statusesCommentsList(@FieldMap Map<String, String> map);

    //发表一条评论
    @POST(Config.URL_COMMENTS_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesCommentsCreate(@FieldMap Map<String, String> map);

    //删除一条评论
    @POST(Config.URL_COMMENTS_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesCommentsDelete(@FieldMap Map<String, String> map);

    //回复一条评论
    @POST(Config.URL_COMMENTS_REPLY)
    @FormUrlEncoded
    Call<GeneralBean> statusesCommentsReply(@FieldMap Map<String, String> map);

    //获取动态的名师点评列表
    @POST(Config.URL_TECH_COMMENTS_LIST)
    @FormUrlEncoded
    Call<TecCommentsList> statusesTecCommentsList(@FieldMap Map<String, String> map);

    //发表点评
    @POST(Config.URL_TECH_COMMENTS_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesTecCommentsCreate(@FieldMap Map<String, String> map);

    //发表追问
    @POST(Config.URL_TECH_COMMENTS_REPLY)
    @FormUrlEncoded
    Call<GeneralBean> statusesTecCommentsReply(@FieldMap Map<String, String> map);

    //获取点赞列表
    @POST(Config.URL_LIKE_LIST)
    @FormUrlEncoded
    Call<LikeListBean> statusesLikeList(@FieldMap Map<String, String> map);

    //获取点赞用户头像列表
    @POST(Config.URL_LIKE_LIST_PIC)
    @FormUrlEncoded
    Call<LikeListPic> statusesLikeUserPic(@FieldMap Map<String, String> map);

    //添加收藏
    @POST(Config.URL_LIKE_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeCreate(@FieldMap Map<String, String> map);

    //删除收藏
    @POST(Config.URL_LIKE_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeDelete(@FieldMap Map<String, String> map);

    //获取点赞用户头像列表
    @POST(Config.URL_FAVORITES_LIST)
    @FormUrlEncoded
    Call<LikeListPic> statusesFavoritesList(@FieldMap Map<String, String> map);

    //添加收藏
    @POST(Config.URL_FAVORITES_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesFavoritesCreate(@FieldMap Map<String, String> map);

    //删除收藏
    @POST(Config.URL_FAVORITES_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesFavoritesDelete(@FieldMap Map<String, String> map);


}
