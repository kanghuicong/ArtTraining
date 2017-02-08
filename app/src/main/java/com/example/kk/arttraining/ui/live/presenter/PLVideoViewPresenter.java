package com.example.kk.arttraining.ui.live.presenter;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.live.bean.ParseCommentListBean;
import com.example.kk.arttraining.ui.live.bean.ParseMemerListBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.bean.ParseTimeTableBean;
import com.example.kk.arttraining.ui.live.bean.TalkStatusBean;
import com.example.kk.arttraining.ui.live.view.IPLVideoView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ErrorMsgUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：wschenyongyin on 2017/1/7 17:54
 * 说明:
 */
public class PLVideoViewPresenter {
    IPLVideoView iplVideoView;
    Callback<ParseCommentListBean> callback;
    Call<ParseCommentListBean> call;

    public PLVideoViewPresenter(IPLVideoView iplVideoView) {
        this.iplVideoView = iplVideoView;
    }


    //获取房间信息
    public void getRoomData(Map<String, Object> map) {

        Callback<LiveBeingBean> callback = new Callback<LiveBeingBean>() {
            @Override
            public void onResponse(Call<LiveBeingBean> call, Response<LiveBeingBean> response) {
                LiveBeingBean roomBean = response.body();
                if (roomBean != null) {
                    if (roomBean.getError_code().equals("0")) {
                        iplVideoView.SuccessRoom(roomBean);
                    } else {
                        iplVideoView.FailureRoom(roomBean.getError_code(), roomBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureRoom(response.code() + "", ErrorMsgUtils.ERROR_LIVE_ROOM);
                }
            }

            @Override
            public void onFailure(Call<LiveBeingBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->", t.getMessage() + "--->" + t.getCause());
                iplVideoView.FailureRoom(Config.Connection_Failure + "", Config.REQUEST_FAILURE);
            }
        };
        Call<LiveBeingBean> call = HttpRequest.getLiveApi().joinLiveRoom(map);
        call.enqueue(callback);
    }


    //退出房间
    public void exitRoom(Map<String, Object> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                UIUtil.showLog("exitRoom----->", response.code() + "");
                iplVideoView.SuccessExiyRoom();
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->", t.getMessage() + "--->" + t.getCause());
                iplVideoView.SuccessExiyRoom();
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getLiveApi().exitLiveRoom(map);
        call.enqueue(callback);
    }

    //获取评论列表
    public void getCommentListData(Map<String, Object> map) {
        callback = new Callback<ParseCommentListBean>() {
            @Override
            public void onResponse(Call<ParseCommentListBean> call, Response<ParseCommentListBean> response) {
                ParseCommentListBean parseCommentListBean = response.body();
                if (parseCommentListBean != null) {
                    UIUtil.showLog("parseCommentListBean--->", parseCommentListBean.toString());
                    if (parseCommentListBean.getError_code().equals("0")) {
                        iplVideoView.SuccessCommentData(parseCommentListBean.getComment_list());
                    } else {
                        iplVideoView.FailureCommentData(parseCommentListBean.getError_code(), parseCommentListBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureRoom(response.code() + "", ErrorMsgUtils.ERROR_LIVE_ROOM);
                }
            }

            @Override
            public void onFailure(Call<ParseCommentListBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->", t.getMessage() + "--->" + t.getCause());
                iplVideoView.FailureRoom(Config.Connection_Failure + "", Config.REQUEST_FAILURE);
            }
        };
        call = HttpRequest.getLiveApi().getCommentList(map);
        call.enqueue(callback);
    }

    //获取成员列表
    public void getMemberListData(Map<String, Object> map) {
        Callback<ParseMemerListBean> callback = new Callback<ParseMemerListBean>() {
            @Override
            public void onResponse(Call<ParseMemerListBean> call, Response<ParseMemerListBean> response) {
                ParseMemerListBean parseMemerListBean = response.body();
                if (parseMemerListBean != null) {
                    if (parseMemerListBean.getError_code().equals("0")) {
//                        iplVideoView.SuccessCommentData();
                    } else {
                        iplVideoView.FailureRoom(parseMemerListBean.getError_code(), parseMemerListBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureRoom(response.code() + "", ErrorMsgUtils.ERROR_LIVE_ROOM);
                }
            }

            @Override
            public void onFailure(Call<ParseMemerListBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->", t.getMessage() + "--->" + t.getCause());
                iplVideoView.FailureRoom(Config.Connection_Failure + "", Config.REQUEST_FAILURE);
            }
        };
        Call<ParseMemerListBean> call = HttpRequest.getLiveApi().getMemberList(map);
        call.enqueue(callback);
    }

    //发表评论
    public void create(Map<String, Object> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                NoDataResponseBean responseBean = response.body();
                if (responseBean != null) {
                    if (responseBean.getError_code().equals("0")) {
                        iplVideoView.SuccessComment();
                    } else {
                        iplVideoView.FailureComment(responseBean.getError_code(), responseBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureComment(response.code() + "", ErrorMsgUtils.ERROR_LIVE_ROOM);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                UIUtil.showLog("onFailure----------->", t.getMessage() + "--->" + t.getCause());
                iplVideoView.FailureComment(Config.Connection_Failure + "", Config.REQUEST_FAILURE);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getLiveApi().liveCreateComment(map);
        call.enqueue(callback);
    }

    //关注
    public void Focus(Map<String, Object> map) {
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iplVideoView.SuccessFocus();
                    } else {
                        iplVideoView.FailureFocus(generalBean.getError_code(), generalBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureFocus(response.code() + "", ErrorMsgUtils.ERROR_FOCUS_MSG);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iplVideoView.FailureFocus(Config.Connection_Failure, ErrorMsgUtils.ERROR_FOCUS_MSG);
            }
        };
        Call<GeneralBean> call = HttpRequest.getStatusesApi().follow_create(map);
        call.enqueue(callback);
    }

    //关注
    public void createLike(Map<String, Object> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                NoDataResponseBean responseBean = response.body();
                if (responseBean != null) {
                    if (responseBean.getError_code().equals("0")) {
                        iplVideoView.SuccessCreateLike();
                    } else {
                        iplVideoView.FailureCreateLike(responseBean.getError_code(), responseBean.getError_msg());
                    }
                } else {
                    iplVideoView.FailureCreateLike(response.code() + "", ErrorMsgUtils.ERROR_LIKE_MSG);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iplVideoView.FailureCreateLike(Config.Connection_Failure, ErrorMsgUtils.ERROR_LIKE_MSG);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getLiveApi().createLike(map);
        call.enqueue(callback);
    }

    //获取禁言状态

    public void getTalkStatus(Map<String, Object> map) {

        HttpRequest.getLiveApi().getTalkStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TalkStatusBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iplVideoView.FailureGetTalk(ErrorMsgUtils.NETWORK_ERROR_CODE, ErrorMsgUtils.ERROR_LIKE_MSG);
                    }

                    @Override
                    public void onNext(TalkStatusBean talkStatusBean) {
                        if (talkStatusBean != null) {
                            if (talkStatusBean.getError_code().equals("0")) {
                                iplVideoView.SuccessGetTalk(talkStatusBean.getIs_talk());
                            } else {
                                iplVideoView.FailureGetTalk(talkStatusBean.getError_code(), talkStatusBean.getError_msg());
                            }
                        } else {
                            iplVideoView.FailureGetTalk(ErrorMsgUtils.NETWORK_ERROR_CODE, ErrorMsgUtils.ERROR_LIKE_MSG);
                        }
                    }
                });
    }


}