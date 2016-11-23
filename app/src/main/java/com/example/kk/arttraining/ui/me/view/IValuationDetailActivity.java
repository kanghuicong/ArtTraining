package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/23 09:53
 * 说明:作品详情处理类
 */
public interface IValuationDetailActivity {


    //获取作品详情成功
    void setWorkInfo(StatusesDetailBean statusesDetailBean);

    //设置视频作品附件
    void setVideoInfo(AttachmentBean attInfo);

    //设置音频作品附件
    void setAudioInfo(AttachmentBean attInfo);

    //设置老师信息以及评论
    void setTecData(List<ParseCommentDetail> tecDataList);

    void OnFailure(String error_code, String error_msg);
}
