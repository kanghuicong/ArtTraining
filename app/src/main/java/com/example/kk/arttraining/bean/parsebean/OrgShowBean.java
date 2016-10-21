package com.example.kk.arttraining.bean.parsebean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 10:41
 * 说明:
 */
public class OrgShowBean {
    private String error_code;
    private String error_msg;
    private int id;
    private String name;
    private List<pic> pic;
    private int comment;
    private int fans_num;
    private String auth;
    private int sign_up;
    private int browse_num;
    private String introduction;
    private String remarks;


    public class pic {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
