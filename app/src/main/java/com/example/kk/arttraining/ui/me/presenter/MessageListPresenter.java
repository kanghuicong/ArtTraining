package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.ui.me.bean.ParseMessageBean;
import com.example.kk.arttraining.ui.me.view.IMessageListView;
import com.example.kk.arttraining.utils.ErrorMsgUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/12/30 10:20
 * 说明:
 */
public class MessageListPresenter {
    IMessageListView iMessageListView;

    public MessageListPresenter(IMessageListView iMessageListView) {
        this.iMessageListView = iMessageListView;
    }

    //获取新的消息列表
    public void getNewMessageData(Map<String,Object> map){
        Callback<ParseMessageBean> callback=new Callback<ParseMessageBean>() {
            @Override
            public void onResponse(Call<ParseMessageBean> call, Response<ParseMessageBean> response) {
                ParseMessageBean parseMessageBean=response.body();
                if (parseMessageBean!=null){
                    if(parseMessageBean.getError_code().equals("0")){
                        iMessageListView.SuccessNew(parseMessageBean.getMsg_list());
                    }else {
                        iMessageListView.FailureNew(parseMessageBean.getError_code(),parseMessageBean.getError_msg());
                    }
                }else {
                    iMessageListView.FailureNew(response.code()+"", ErrorMsgUtils.ERROR_MSG_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseMessageBean> call, Throwable t) {
                iMessageListView.FailureNew("400", ErrorMsgUtils.ERROR_MSG_FAILURE);
            }
        };
    }

    //获取全部消息
    public void getAllMessageData(Map<String,Object> map){
        Callback<ParseMessageBean> callback=new Callback<ParseMessageBean>() {
            @Override
            public void onResponse(Call<ParseMessageBean> call, Response<ParseMessageBean> response) {
                ParseMessageBean parseMessageBean=response.body();
                if (parseMessageBean!=null){
                    if(parseMessageBean.getError_code().equals("0")){
                        iMessageListView.SuccessAll(parseMessageBean.getMsg_list());
                    }else {
                        iMessageListView.FailureAll(parseMessageBean.getError_code(),parseMessageBean.getError_msg());
                    }
                }else {
                    iMessageListView.FailureAll(response.code()+"", ErrorMsgUtils.ERROR_MSG_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<ParseMessageBean> call, Throwable t) {
                iMessageListView.FailureAll("400", ErrorMsgUtils.ERROR_MSG_FAILURE);
            }
        };
    }

}
