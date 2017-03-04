package com.example.kk.arttraining.ui.live.presenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2017/3/1 14:36
 * 说明:直播
 */
public interface IPLVideoViewPresenter {
    //获取房间信息
    void getRoomData(Map<String, Object> map);

    //退出房间
    void exitRoom(Map<String, Object> map);

    //获取成员列表
    void getMemberListData(Map<String, Object> map);

    //获取评论列表
    void getCommentListData(Map<String, Object> map);

    //发表评论
    void create(Map<String, Object> map);

    //关注
    void Focus(Map<String, Object> map);

    //点赞
    void createLike(Map<String, Object> map);
    //获取禁言状态

    void getTalkStatus(Map<String, Object> map);

    //获取表情列表
    void getGiftList();

    //送礼物接口
    void sendGift(Map<String, Object> map);

    //查询当前积分数量
    void QueryScore(HashMap<String, Object> map);

    //查询当前云币数量
    void QueryICloud(HashMap<String, Object> map);

    //积分消费送礼物
    void sendGiftByScore(HashMap<String, Object> map);

    //消费云币送积分
    void sendGiftByICloud(HashMap<String, Object> map);

    //解除订阅
    void cancelSubscription();

}
