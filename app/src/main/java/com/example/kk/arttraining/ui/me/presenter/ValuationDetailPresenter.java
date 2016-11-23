package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.AttachmentBean;
import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.ui.me.view.IValuationDetailActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/23 10:28
 * 说明:作品详情处理类
 */
public class ValuationDetailPresenter {
    IValuationDetailActivity iValuationDetailActivity;

    public ValuationDetailPresenter(IValuationDetailActivity iValuationDetailActivity) {
        this.iValuationDetailActivity = iValuationDetailActivity;

    }

    public void getData(Map<String, Object> map) {
        Callback<StatusesDetailBean> callback = new Callback<StatusesDetailBean>() {
            @Override
            public void onResponse(Call<StatusesDetailBean> call, Response<StatusesDetailBean> response) {
                StatusesDetailBean statusesDetailBean = response.body();

                UIUtil.showLog("StatusesDetailBean---》", response.code() + "--->" + response.message());
                if (statusesDetailBean != null) {
                    UIUtil.showLog("获取作品详情---》", statusesDetailBean.toString());
                    if (statusesDetailBean.getError_code().equals("0")) {
                        //设置作品详情
                        iValuationDetailActivity.setWorkInfo(statusesDetailBean);
                        //设置附件信息
                        if (statusesDetailBean.getAtt() != null&&statusesDetailBean.getAtt().size()!=0)
                            setAttInfo(statusesDetailBean.getAtt().get(0));
                        //设置名师信息及评论
                        setTecData(statusesDetailBean.getTec_comments_list());
                    } else {
                        iValuationDetailActivity.OnFailure(statusesDetailBean.getError_code(), statusesDetailBean.getError_code());
                    }
                } else {
                    iValuationDetailActivity.OnFailure(response.code() + "", Config.Connection_ERROR_TOAST);
                }

            }

            @Override
            public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                UIUtil.showLog("StatusesDetailBean---》onFailure", t.getMessage() + "--->" + t.getCause());
                iValuationDetailActivity.OnFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<StatusesDetailBean> call = HttpRequest.getStatusesApi().statusesUserWorkDetail(map);
        call.enqueue(callback);
    }

    void setAttInfo(AttachmentBean attInfo) {
        String att_type = attInfo.getAtt_type();
        if (att_type.equals("video")) {
            iValuationDetailActivity.setVideoInfo(attInfo);
        } else if (att_type.equals("music")) {
            iValuationDetailActivity.setAudioInfo(attInfo);
        }
    }

    void setTecData(List<ParseCommentDetail> tecDataList) {

        iValuationDetailActivity.setTecData(tecDataList);
    }
}
