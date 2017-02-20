package com.example.kk.arttraining.ui.live.view;

import com.example.kk.arttraining.ui.live.bean.GiftBean;
import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.gitanimation.GiftFrameLayout;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/7 17:56
 * 说明:
 */
public interface IPLVideoView {


    //获取房主信息
    void getRoomData();

    //获取房间信息成功
    void SuccessRoom(LiveBeingBean roomBean);

    //获取房间信息成功
    void FailureRoom(String error_code, String error_msg);

    //获取用户评论消息
    void getCommentData();

    //获取评论成功
    void SuccessCommentData(List<LiveCommentBean> liveCommentBeanList);

    //获取评论失败
    void FailureCommentData(String error_code, String error_msg);

    //获取发言状态
    void getTalkStatus();

    //获取发言状态成功
    void SuccessGetTalk(String talkStatus);

    //获取发言状态失败
    void FailureGetTalk(String error_code, String error_msg);

    //发表评论
    void createComment();

    //评论成功
    void SuccessComment();

    //评论失败
    void FailureComment(String error_code, String error_msg);

    //点赞
    void createLike();

    //点赞成功
    void SuccessCreateLike();

    //点赞失败
    void FailureCreateLike(String error_code, String error_msg);

    //关注
    void Focus();

    //关注成功
    void SuccessFocus();

    //关注失败
    void FailureFocus(String error_code, String error_msg);

    //获取礼物列表
    void getGiftList();

    //获取礼物列表成功
    void SuccessGetGiftList(List<GiftBean> giftBeenList);

    //获取礼物列表失败
    void FailureGetGiftList();

    void setGiftBean(GiftBean giftBean);

    //送礼物请求
    void sendGift(int gift_id, int gift_num);

    //送礼物成功
    void SuccessSendGift();

    //开启送礼物动画
    void starGiftAnimation(List<GiftBean> giftDataList);

    //显示礼物动画
    void sendGiftAnimation(final GiftFrameLayout view, GiftBean giftBean);

    //送礼物失败
    void FailureSendGift();

    //退出房间
    void exitRoom();

    void showExitDialog();

    //退出房间成功
    void SuccessExiyRoom();

    //隐藏所有view
    void HideAllView();

    //显示v所有iew
    void ShowAllView();

}
