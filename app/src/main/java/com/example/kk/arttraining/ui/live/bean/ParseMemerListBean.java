package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/9 15:57
 * 说明:解析直播间用户列表
 */
public class ParseMemerListBean extends NoDataResponseBean {

    private List<MemberBean> member_list;

    public ParseMemerListBean() {
    }

    public List<MemberBean> getMember_list() {
        return member_list;
    }

    public void setMember_list(List<MemberBean> member_list) {
        this.member_list = member_list;
    }

    @Override
    public String toString() {
        return "ParseUserListBean{" +
                "member_list=" + member_list +
                '}';
    }
}
