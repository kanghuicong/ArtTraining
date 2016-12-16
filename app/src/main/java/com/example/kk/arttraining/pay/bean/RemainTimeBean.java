package com.example.kk.arttraining.pay.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/12/15 16:02
 * 说明:查看订单支付剩余时间
 */
public class RemainTimeBean extends NoDataResponseBean{

    private int remaining_time;

    public RemainTimeBean() {
    }

    public int getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(int remaining_time) {
        this.remaining_time = remaining_time;
    }

    @Override
    public String toString() {
        return "RemainTimeBean{" +
                "remaining_time=" + remaining_time +
                '}';
    }
}
