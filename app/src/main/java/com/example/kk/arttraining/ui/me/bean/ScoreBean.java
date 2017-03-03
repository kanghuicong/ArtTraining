package com.example.kk.arttraining.ui.me.bean;

/**
 * 作者：wschenyongyin on 2017/2/28 15:52
 * 说明:积分
 */
public class ScoreBean {
    int score_id;
    int score;
    String consume_type;
    String consume_time;
    String remark;

    public String getConsume_time() {
        return consume_time;
    }

    public void setConsume_time(String consume_time) {
        this.consume_time = consume_time;
    }

    public String getConsume_type() {
        return consume_type;
    }

    public void setConsume_type(String consume_type) {
        this.consume_type = consume_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }
}
