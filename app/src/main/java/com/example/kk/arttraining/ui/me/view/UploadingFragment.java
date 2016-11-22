package com.example.kk.arttraining.ui.me.view;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.adapter.UploadingAdapter;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.MediaUtils;
import com.example.kk.arttraining.utils.RandomUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.bean.AttBean;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/23 15:32
 * 说明:正在上传列表
 */
public class UploadingFragment extends Fragment implements IUploadFragment, ISignleUpload {
    @InjectView(R.id.lv_download)
    ListView lvDownload;
    @InjectView(R.id.tv_failure_hint_)
    TextView tvFailureHint;
    @InjectView(R.id.failure_hint_layout)
    LinearLayout failureHintLayout;
    private Fragment fragment;
    private View download_view;
    private Context context;
    private UploadPresenter presenter;
    private List<UploadBean> uploadBeanList;
    private UploadingAdapter uploadingAdapter;
    private SignleUploadPresenter signleUploadPresenter;
    private String thumbnail_pic;
    private UploadBean uploadBean;
    private String jsonString;
    private String order_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        download_view = inflater.inflate(R.layout.me_download_fragment, null);
        presenter = new UploadPresenter(this);
        ButterKnife.inject(this, download_view);
        getLocalUploadData();
        signleUploadPresenter = new SignleUploadPresenter(this);
        return download_view;
    }

    @Override
    public void getLocalUploadData() {
        presenter.getLocalUploadData(context, "0");

    }

    @Override
    public void updateProgress() {

    }

    @Override
    public void onSuccess(List<UploadBean> uploadBeanList) {
        if (uploadBeanList.size() == 0) {
            tvFailureHint.setText("无上传任务哦！");
            failureHintLayout.setVisibility(View.VISIBLE);
        } else {
            uploadingAdapter = new UploadingAdapter(context, uploadBeanList);
            lvDownload.setAdapter(uploadingAdapter);
        }
    }

    @Override
    public void UpdateOrderSuccess() {
        UIUtil.showLog("更新订单----》", "成功");

    }

    @Override
    public void UpdateOrderFailure(String error_code, String error_msg) {
        UIUtil.showLog("更新订单----》", "失败" + error_code + "--->" + error_msg);
    }

    //
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(UploadQiNiuService.ACTION_UPDATE);
        context.registerReceiver(myReceiver, filter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            int progress = intent.getIntExtra("progress", 0);
            order_id = intent.getStringExtra("order_id");
            String att_path = intent.getStringExtra("upload_path");

            List<AttBean> attBeanList = new ArrayList<AttBean>();
            AttBean attBean = new AttBean();
            attBean.setStore_path(att_path);
            attBeanList.add(attBean);
            Gson gson = new Gson();
            jsonString = gson.toJson(attBeanList);
            //如果下载完成，更改本地数据库状态
            if (progress == 100) {
                UploadDao uploadDao = new UploadDao(context);
                uploadBean = uploadDao.queryOrder(order_id);
                UIUtil.showLog("UploadingFragment->UploadBean", uploadBean.toString() + "true");
                //将附件上传状态改为成功

                UIUtil.showLog("UploadingFragment->att_path", att_path + "true");
                if(uploadBean.getAtt_type().equals("video")){
                    Bitmap bitmap = MediaUtils.getVideoThumbnail(att_path);
                    String video_pic_name = RandomUtils.getRandomInt() + "";
                    UIUtil.showLog("UploadingFragment->video_pic_name", video_pic_name + "true");
                    try {
                        thumbnail_pic = FileUtil.saveFile(bitmap, video_pic_name).toString();
                        signleUploadPresenter.uploadVideoPic(thumbnail_pic);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    uploadVideoPic("");
                }
                uploadDao.update("type", "1", order_id);
            }
            //更新adapter进度条
            uploadingAdapter.updateProgress(order_id, progress);
            UIUtil.showLog("UploadingFragment->myReceiver", "true");
        }

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void uploadSuccess(String file_path) {
        UIUtil.showLog("UploadingFragment->uploadSuccess", "true");
    }

    @Override
    public void uploadVideoPic(String video_pic) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", order_id);
        map.put("pay_type", uploadBean.getPay_type());
        map.put("att_type", uploadBean.getAtt_type());
        map.put("attachment", jsonString);
        map.put("thumbnail", video_pic);

        UIUtil.showLog("请求地址:----------->",Config.BASE_URL+Config.URL_ORDERS_UPDATE);
        presenter.updateOrder(map);
}

    @Override
    public void uploadFailure(String error_code) {
        UIUtil.showLog("UploadingFragment->uploadFailure", "true");
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(myReceiver!=null) unregisterReceiver(myReceiver);
//
//    }
}
