package com.example.kk.arttraining.ui.live.view;

import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;

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

    //退出房间
    void exitRoom();

}
