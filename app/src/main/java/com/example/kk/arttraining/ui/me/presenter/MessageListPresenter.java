package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.me.bean.ParseMessageBean;
import com.example.kk.arttraining.ui.me.view.IMessageListView;
import com.example.kk.arttraining.utils.ErrorMsgUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/12/30 10:20
 * 说明:消息列表
 */
public class MessageListPresenter {
    IMessageListView iMessageListView;

    public MessageListPresenter(IMessageListView iMessageListView) {
        this.iMessageListView = iMessageListView;
    }

    //获取新的消息列表
    public void RefreshData(Map<String,Object> map){
        Callback<ParseMessageBean> callback=new Callback<ParseMessageBean>() {
            @Override
            public void onResponse(Call<ParseMessageBean> call, Response<ParseMessageBean> response) {
                ParseMessageBean parseMessageBean=response.body();
                if (parseMessageBean!=null){
                    if(parseMessageBean.getError_code().equals("0")){
                        iMessageListView.SuccessRefresh(parseMessageBean.getMsg_list());
                    }else {
                        iMessageListView.FailureRefrsh(parseMessageBean.getError_code(),parseMessageBean.getError_msg());
                    }
                }else {
                    iMessageListView.FailureRefrsh(response.code()+"", ErrorMsgUtils.ERROR_MSG_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseMessageBean> call, Throwable t) {
                iMessageListView.FailureRefrsh("400", ErrorMsgUtils.ERROR_MSG_FAILURE);
            }
        };

        Call<ParseMessageBean> call= HttpRequest.getUserApi().getPushNewList(map);
        call.enqueue(callback);
    }

    //获取全部消息
    public void LoadData(Map<String,Object> map){
        Callback<ParseMessageBean> callback=new Callback<ParseMessageBean>() {
            @Override
            public void onResponse(Call<ParseMessageBean> call, Response<ParseMessageBean> response) {
                ParseMessageBean parseMessageBean=response.body();
                if (parseMessageBean!=null){
                    if(parseMessageBean.getError_code().equals("0")){
                        iMessageListView.SuccessLoad(parseMessageBean.getMsg_list());
                    }else {
                        iMessageListView.FailureLoad(parseMessageBean.getError_code(),parseMessageBean.getError_msg());
                    }
                }else {
                    iMessageListView.FailureLoad(response.code()+"", ErrorMsgUtils.ERROR_MSG_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseMessageBean> call, Throwable t) {

                UIUtil.showLog("onFailure--------->",t.getMessage()+"-->"+t.getCause());
                iMessageListView.FailureLoad("400", ErrorMsgUtils.ERROR_MSG_FAILURE);
            }
        };
        Call<ParseMessageBean> call= HttpRequest.getUserApi().getPushNewList(map);
        call.enqueue(callback);
    }





}
