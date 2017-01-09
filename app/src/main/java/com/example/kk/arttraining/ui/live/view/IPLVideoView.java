package com.example.kk.arttraining.ui.live.view;

import com.example.kk.arttraining.ui.live.bean.RoomBean;

/**
 * 作者：wschenyongyin on 2017/1/7 17:56
 * 说明:
 */
public interface IPLVideoView {
    //获取房主信息
    void getRoomData();

    //获取房间信息成功
    void SuccessRoom(RoomBean roomBean);

    //获取房间信息成功
    void FailureRoom(String error_code, String error_msg);

    //获取用户评论消息


    //发表评论

    //
}
