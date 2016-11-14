package com.example.kk.arttraining.prot;

import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.CommentsListBean;
import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.bean.parsebean.LikeListBean;
import com.example.kk.arttraining.bean.parsebean.LikeListPic;
import com.example.kk.arttraining.bean.parsebean.ParseBannerBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.bean.parsebean.TecCommentsList;
import com.example.kk.arttraining.ui.me.bean.ParseCollectBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 作者：wschenyongyin on 2016/10/20 11:01
 * 说明:动态 点赞 轮播接口
 */
public interface StatusesRequestApi {
    //获取首页最新动态、帖子列表
    @POST(Config.URL_STATUSES_PUBLIC_TIMELINE_BBS)
    @FormUrlEncoded
    Call<StatusesBean> statusesGoodList(@FieldMap Map<String, Object> map);

    //获取用户动态列表
    @POST(Config.URL_STATUSES_USER_TIMELINE_BBS)
    @FormUrlEncoded
    Call<StatusesBean> statusesUserList(@FieldMap Map<String, String> map);

    //获取动态详情
    @POST(Config.URL_STATUSES_SHOW_BBS)
    @FormUrlEncoded
    Call<StatusesDetailBean> statusesDetail(@FieldMap Map<String, String> map);

    //发布动态
    @POST(Config.URL_STATUSES_REPORT_BBS)
    @FormUrlEncoded
    Call<GeneralBean> statusesReport(@FieldMap Map<String, String> map);

    //转发动态
    @POST(Config.URL_STATUSES_PUBLISH_BBS)
    @FormUrlEncoded
    Call<GeneralBean> statusesPublish(@FieldMap Map<String, String> map);

    //删除动态
    @POST(Config.URL_STATUSES_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesDelete(@FieldMap Map<String, String> map);


    //获取小组动态列表
    @POST(Config.URL_STATUSES_TIMELINE_GROUP)
    @FormUrlEncoded
    Call<StatusesBean> statusesGoodListGroup(@FieldMap Map<String, Object> map);

    //获取用户动态列表
    @POST(Config.URL_STATUSES_USER_TIMELINE_GROUP)
    @FormUrlEncoded
    Call<StatusesBean> statusesUserListGroup(@FieldMap Map<String, String> map);

    //获取动态详情
    @POST(Config.URL_STATUSES_SHOW_GROUP)
    @FormUrlEncoded
    Call<StatusesDetailBean> statusesDetailGroup(@FieldMap Map<String, String> map);

    //发布动态
    @POST(Config.URL_STATUSES_REPORT_GROUP)
    @FormUrlEncoded
    Call<GeneralBean> statusesPublishGroup(@FieldMap Map<String, String> map);

    //转发动态
    @POST(Config.URL_STATUSES_PUBLISH_GROUP)
    @FormUrlEncoded
    Call<GeneralBean> statusesReportGroup(@FieldMap Map<String, String> map);


    //获取用户的作品列表
    @POST(Config.URL_STATUSES_USER_TIMELINE_WORK)
    @FormUrlEncoded
    Call<StatusesBean> statusesUserWorkList(@FieldMap Map<String, String> map);

    //获取作品详情
    @POST(Config.URL_STATUSES_SHOW_WORK)
    @FormUrlEncoded
    Call<StatusesDetailBean> statusesUserWorkDetail(@FieldMap Map<String, String> map);

    //获取动态的评论列表
    @POST(Config.URL_COMMENTS_LIST)
    @FormUrlEncoded
    Call<CommentsListBean> statusesCommentsList(@FieldMap Map<String, String> map);

    //发表一条评论
    @POST(Config.URL_COMMENTS_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesCommentsCreate(@FieldMap Map<String, Object> map);

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

    //获取帖子点赞列表
    @POST(Config.URL_LIKE_LIST_BBS)
    @FormUrlEncoded
    Call<LikeListBean> statusesLikeListBBS(@FieldMap Map<String, String> map);

    //获取帖子点赞用户头像列表
    @POST(Config.URL_LIKE_LIST_PIC_BBS)
    @FormUrlEncoded
    Call<LikeListPic> statusesLikeUserPicBBS(@FieldMap Map<String, String> map);

    //添加帖子点赞
    @POST(Config.URL_LIKE_CREATE_BBS)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeCreateBBS(@FieldMap Map<String, Object> map);

    //获取小组点赞列表
    @POST(Config.URL_LIKE_LIST_GROUP)
    @FormUrlEncoded
    Call<LikeListBean> statusesLikeListGroup(@FieldMap Map<String, String> map);

    //获取小组点赞用户头像列表
    @POST(Config.URL_LIKE_LIST_PIC_GROUP)
    @FormUrlEncoded
    Call<LikeListPic> statusesLikeUserPicGroup(@FieldMap Map<String, String> map);

    //添加小组收藏
    @POST(Config.URL_LIKE_CREATE_GROUP)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeCreateGroup(@FieldMap Map<String, String> map);

    //获取作品点赞列表
    @POST(Config.URL_LIKE_LIST_WORK)
    @FormUrlEncoded
    Call<LikeListBean> statusesLikeListWork(@FieldMap Map<String, String> map);

    //获取作品点赞用户头像列表
    @POST(Config.URL_LIKE_LIST_PIC_WORK)
    @FormUrlEncoded
    Call<LikeListPic> statusesLikeUserPicWork(@FieldMap Map<String, String> map);

    //添加作品收藏
    @POST(Config.URL_LIKE_CREATE_WORK)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeCreateWork(@FieldMap Map<String, String> map);

    //删除收藏
    @POST(Config.URL_LIKE_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesLikeDelete(@FieldMap Map<String, String> map);

    //获取用户收藏列表
    @POST(Config.URL_FAVORITES_LIST)
    @FormUrlEncoded
    Call<ParseCollectBean> statusesFavoritesList(@FieldMap Map<String, Object> map);

    //添加收藏
    @POST(Config.URL_FAVORITES_CREATE)
    @FormUrlEncoded
    Call<GeneralBean> statusesFavoritesCreate(@FieldMap Map<String, String> map);

    //删除收藏
    @POST(Config.URL_FAVORITES_DELETE)
    @FormUrlEncoded
    Call<GeneralBean> statusesFavoritesDelete(@FieldMap Map<String, String> map);

    //获取轮播列表
    @POST(Config.URL_BANNER_LIST)
    @FormUrlEncoded
    Call<ParseBannerBean> bannerList(@FieldMap Map<String, String> map);

    //获取轮播详情
    @POST(Config.URL_BANNER_SHOW)
    @FormUrlEncoded
    Call<BannerBean> bannerShow(@FieldMap Map<String, String> map);
}
