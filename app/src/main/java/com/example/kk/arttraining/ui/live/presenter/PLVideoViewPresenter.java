package com.example.kk.arttraining.ui.live.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxHelper;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxSubscribe;
import com.example.kk.arttraining.ui.live.bean.GiftBean;
import com.example.kk.arttraining.ui.live.bean.LiveCommentBean;
import com.example.kk.arttraining.ui.live.bean.ParseCommentListBean;
import com.example.kk.arttraining.ui.live.bean.ParseGiftBean;
import com.example.kk.arttraining.ui.live.bean.ParseMemerListBean;
import com.example.kk.arttraining.ui.live.bean.LiveBeingBean;
import com.example.kk.arttraining.ui.live.bean.TalkStatusBean;
import com.example.kk.arttraining.ui.live.view.IPLVideoView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.ErrorMsgUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：wschenyongyin on 2017/1/7 17:54
 * 说明:
 */
public class PLVideoViewPresenter implements IPLVideoViewPresenter {
    IPLVideoView iplVideoView;
    Callback<ParseCommentListBean> callback;
    Call<ParseCommentListBean> call;
    private ExecutorService executorService;
    //用于封装处理后的评论数据
    List<LiveCommentBean> commentDataList;
    Message commentMsg;
    //用于封装处理后的礼物数据
    List<GiftBean> giftDataList;
    Run run;
    Message giftMsg;
    /**
     * rxjava订阅
     */
    Subscription sendGiftSub;
    //获取礼物列表
    Subscription getGiftListSub;
    //获取发言状态
    Subscription getTalkStatusSub;
    //消费积分赠送礼物
    Subscription consumeScoreSub;
    //查询用户当前积分
    Subscription queryScoreSub;
    //查询用户当前云币
    Subscription queryICloudSub;
    //消费云币送礼物
    Subscription consumeICloudSub;

    LiveCommentBean liveCommentBean;
    public PLVideoViewPresenter(IPLVideoView iplVideoView) {
        this.iplVideoView = iplVideoView;
        executorService = Executors.newSingleThreadExecutor();
    }


    //获取房间信息
    @Override
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
    @Override
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


    //获取成员列表
    @Override
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

    //获取评论列表
    @Override
    public void getCommentListData(Map<String, Object> map) {
        callback = new Callback<ParseCommentListBean>() {
            @Override
            public void onResponse(Call<ParseCommentListBean> call, Response<ParseCommentListBean> response) {
                ParseCommentListBean parseCommentListBean = response.body();
                if (parseCommentListBean != null) {
                    UIUtil.showLog("liveComment-parseCommentListBean--->", parseCommentListBean.toString());
                    if (parseCommentListBean.getComment_list() != null && parseCommentListBean.getError_code().equals("0") &&  parseCommentListBean.getComment_list().size() != 0) {
                        //开启子线程处理数据
                        UIUtil.showLog("liveComment", "子线程开启");
//                        run = new Run(parseCommentListBean.getComment_list());
//                        executorService.execute(run);

                        commentDataList = new ArrayList<LiveCommentBean>();
                        giftDataList = new ArrayList<GiftBean>();
                        for (int i = 0; i < parseCommentListBean.getComment_list().size(); i++) {
                            liveCommentBean = parseCommentListBean.getComment_list().get(i);
                            commentDataList.add(liveCommentBean);
                            if (liveCommentBean.getType().equals("gift")) {
                                GiftBean giftBean = new GiftBean();
                                if (!StringUtils.isEmpty(liveCommentBean.getName()))
                                    giftBean.setName(liveCommentBean.getName());
                                if (!StringUtils.isEmpty(liveCommentBean.getContent()))
                                    giftBean.setGift_id(Integer.parseInt(liveCommentBean.getContent()));
                                if (!StringUtils.isEmpty(liveCommentBean.getGift_pic()))
                                    giftBean.setPic(liveCommentBean.getGift_pic());
                                if (!StringUtils.isEmpty(liveCommentBean.getGift_name()))
                                    giftBean.setGift_name(liveCommentBean.getGift_name());
                                giftBean.setGift_num(liveCommentBean.getGift_num());
                                giftDataList.add(giftBean);
                            }
                        }

                        if (commentDataList != null && commentDataList.size() != 0) {
                            iplVideoView.SuccessCommentData(commentDataList);
                        }
                        if (giftDataList != null && giftDataList.size() != 0) {
                            iplVideoView.starGiftAnimation(giftDataList);
                        }

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


    //用于处理评论和礼物的新线程
    class Run implements Runnable {
        List<LiveCommentBean> comment_list;
        LiveCommentBean liveCommentBean;

        Run(List<LiveCommentBean> comment_list) {
            this.comment_list = comment_list;
        }

        @Override
        public void run() {
            commentDataList = new ArrayList<LiveCommentBean>();
            giftDataList = new ArrayList<GiftBean>();
            for (int i = 0; i < comment_list.size(); i++) {
                liveCommentBean = comment_list.get(i);
                commentDataList.add(liveCommentBean);
                if (liveCommentBean.getType().equals("gift")) {
                    GiftBean giftBean = new GiftBean();
                    if (!StringUtils.isEmpty(liveCommentBean.getName()))
                        giftBean.setName(liveCommentBean.getName());
                    if (!StringUtils.isEmpty(liveCommentBean.getContent()))
                        giftBean.setGift_id(Integer.parseInt(liveCommentBean.getContent()));
                    if (!StringUtils.isEmpty(liveCommentBean.getGift_pic()))
                        giftBean.setPic(liveCommentBean.getGift_pic());
                    if (!StringUtils.isEmpty(liveCommentBean.getGift_name()))
                        giftBean.setGift_name(liveCommentBean.getGift_name());
                    giftBean.setGift_num(liveCommentBean.getGift_num());
                    giftDataList.add(giftBean);
                }
            }

            UIUtil.showLog("liveComment", "子线程开启中----");

            //发送处理好的评论消息
            if (commentDataList != null && commentDataList.size() != 0) {
                commentMsg = new Message();
                commentMsg.obj = commentDataList;
                commentHandle.sendMessage(commentMsg);
            }
            //发送处理好的礼物消息
            if (giftDataList != null && giftDataList.size() != 0) {
//                if (giftMsg == null)
                UIUtil.showLog("测试222222222", "---");
                giftMsg = new Message();
                giftMsg.obj = giftDataList;
                giftHandle.sendMessage(giftMsg);
            }
        }
    }


    //评论handler
    Handler commentHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UIUtil.showLog("liveComment", "回调----");
            iplVideoView.SuccessCommentData((List<LiveCommentBean>) msg.obj);
        }
    };

    //礼物handler
    Handler giftHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            iplVideoView.starGiftAnimation((List<GiftBean>) msg.obj);
        }
    };

    //发表评论
    @Override
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
    @Override
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

    //点赞
    @Override
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
    @Override
    public void getTalkStatus(Map<String, Object> map) {

        getTalkStatusSub = HttpRequest.getLiveApi().getTalkStatus(map)
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
        RxApiManager.get().add("gettalkstatus", getTalkStatusSub);
    }


    //获取表情列表
    @Override
    public void getGiftList() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        getGiftListSub = HttpRequest.getLiveApi().getGiftList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParseGiftBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iplVideoView.FailureGetGiftList();
                    }

                    @Override
                    public void onNext(ParseGiftBean parseGiftBean) {
                        if (parseGiftBean != null) {
                            if (parseGiftBean.getError_code().equals("0")) {
                                iplVideoView.SuccessGetGiftList(parseGiftBean.getGift_list());
                            } else {

                            }
                        } else {
                        }
                    }
                });
        RxApiManager.get().add("getgiftlist", getGiftListSub);
    }


    //送礼物接口
    @Override
    public void sendGift(Map<String, Object> map) {
        sendGiftSub = HttpRequest.getLiveApi().giveGift(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NoDataResponseBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iplVideoView.FailureSendGift();
                    }

                    @Override
                    public void onNext(NoDataResponseBean parseGiftBean) {
                        if (parseGiftBean != null) {
                            if (parseGiftBean.getError_code().equals("0")) {
                                iplVideoView.SuccessSendGift();
                            } else {
                                UIUtil.showLog("error---------->", parseGiftBean.getError_code() + "-------->" + parseGiftBean.getError_msg());
                                iplVideoView.FailureSendGift();

                            }
                        } else {
                            iplVideoView.FailureSendGift();
                        }
                    }
                });
        RxApiManager.get().add("sendgift", sendGiftSub);
    }

    //查询当前用户积分
    @Override
    public void QueryScore(HashMap<String, Object> map) {
        queryScoreSub = HttpRequest.getScoreApi().queryScore(map).compose(RxHelper.<Integer>handleResult()).subscribe(new RxSubscribe<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            protected void _onNext(Integer integer) {
                iplVideoView.SuccessQueryScore(integer.intValue());
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iplVideoView.FailureQuery(error_code, error_msg);
            }
        });
        RxApiManager.get().add("queryScoreSub", queryScoreSub);
    }


    //积分消费
    @Override
    public void sendGiftByScore(HashMap<String, Object> map) {
        consumeScoreSub = HttpRequest.getScoreApi().consumeScore(map)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        iplVideoView.SuccessSendGift();
                    }

                    @Override
                    protected void _onError(String error_code, String error_msg) {
                        iplVideoView.FailureSendGift();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
        RxApiManager.get().add("consumeScoreSub", consumeScoreSub);

    }

    //查询用户当前云币
    @Override
    public void QueryICloud(HashMap<String, Object> map) {

        queryICloudSub = HttpRequest.getCloudApi().queryCloud(map).compose(RxHelper.<Double>handleResult()).subscribe(new RxSubscribe<Double>() {
            @Override
            protected void _onNext(Double aDouble) {
                iplVideoView.SuccessQueryCloud(aDouble);
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iplVideoView.FailureQuery(error_code, error_msg);
            }

            @Override
            public void onCompleted() {

            }
        });
        RxApiManager.get().add("queryICloudSub", queryICloudSub);
    }

    //x消费云币送积分
    @Override
    public void sendGiftByICloud(HashMap<String, Object> map) {

        consumeICloudSub = HttpRequest.getCloudApi().consumeCloud(map).compose(RxHelper.<String>handleResult()).subscribe(new RxSubscribe<String>() {
            @Override
            protected void _onNext(String s) {
                iplVideoView.SuccessSendGift();
            }

            @Override
            protected void _onError(String error_code, String error_msg) {
                iplVideoView.FailureSendGift();
            }

            @Override
            public void onCompleted() {

            }
        });
        RxApiManager.get().add("consumeICloudSub", consumeICloudSub);
    }

    //取消订阅
    @Override
    public void cancelSubscription() {
        RxApiManager.get().cancel("sendgift");
        RxApiManager.get().cancel("getgiftlist");
        RxApiManager.get().cancel("gettalkstatus");
        RxApiManager.get().cancel("queryScoreSub");
        RxApiManager.get().cancel("consumeScoreSub");
    }

}
