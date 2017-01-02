package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/30 10:09
 * 说明:解析消息列表
 */
public class ParseMessageBean extends NoDataResponseBean{

    List<MessageBean> msg_list;

    public ParseMessageBean() {
    }

    public List<MessageBean> getMsg_list() {
        return msg_list;
    }

    public void setMsg_list(List<MessageBean> msg_list) {
        this.msg_list = msg_list;
    }

    @Override
    public String toString() {
        return "ParseMessageBean{" +
                "msg_list=" + msg_list +
                '}';
    }
}
