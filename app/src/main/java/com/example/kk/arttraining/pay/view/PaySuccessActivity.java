package com.example.kk.arttraining.pay.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.ui.me.view.IUploadFragment;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;
import com.example.kk.arttraining.utils.upload.view.IUploadProgressListener;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/20 16:08
 * 说明:测评付款页面
 */
public class PaySuccessActivity extends BaseActivity implements IUploadProgressListener, ISignleUpload, IUploadFragment {

    @InjectView(R.id.tv_title_back)
    TextView tvTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;

    private UploadDialog uploadDialog;
    private String file_path;
    private String token;
    private String order_id;
    private SignleUploadPresenter signleUploadPresenter;
    private UploadBean uploadBean;
    private UploadPresenter presenter;
    private UploadDao uploadDao;
    String pay_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_order_detail);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        uploadDao = new UploadDao(getApplicationContext());
        signleUploadPresenter = new SignleUploadPresenter(this);
        Intent intent = getIntent();
        file_path = intent.getStringExtra("file_path");
        token = intent.getStringExtra("token");
        order_id = intent.getStringExtra("order_id");
        pay_type = intent.getStringExtra("pay_type");
        tvTitleBar.setText("支付成功");
        presenter = new UploadPresenter(this);

        //更新上传列表付款状态为已付款
        uploadDao.update("is_pay", "1", order_id);
        startUpload();
    }

    @OnClick(R.id.tv_title_back)
    public void onClick(View v) {
        ActivityManage.getAppManager().finishAllActivity();
        finish();
    }
    //开始传
    void startUpload() {
        if (file_path == null || file_path.equals("")) {
            uploadDao.delete(order_id);
            UIUtil.ToastshowShort(getApplicationContext(), "作品附件地址丢失，请到我的测评页面重新选择附件");
        } else {
            if (Config.att_type != null && Config.att_type.equals("pic")) {
                uploadImage();
            } else {
                uploadAtt();
            }
        }
    }
    //上传图片附件
    void uploadImage() {
        UIUtil.showLog("file_path---->", file_path + "");
        UpdateOrderFailure("paysuccess-->uploadImage", "true");
        List<String> fileFist = new ArrayList<String>();
        JSONArray jsonArray = null;
        uploadDao.update("is_uploading", "1", "order_id");
        try {
            jsonArray = new JSONArray(file_path);
            for (int i = 0; i < jsonArray.length(); i++) {
                String path = jsonArray.getString(i);
                fileFist.add(path);
            }
            signleUploadPresenter.upload(fileFist, 6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //上传音频或视频文件
    void uploadAtt() {
        uploadDao.update("is_uploading", "1", order_id);
        UIUtil.showLog("payactivity-->", "startUpload");
        Intent intent = new Intent(this, UploadQiNiuService.class);
        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", file_path);

        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        intent.putExtra("order_id", order_id);
        startService(intent);
        uploadDialog = new UploadDialog(this, R.layout.dialog_upload, R.style.Dialog, new UploadDialog.UploadListener() {
            @Override
            public void onClick(View view) {
                uploadDialog.unRegisterReceiver();
                uploadDialog.dismiss();
            }
        }, this);
        uploadDialog.show();
    }

    //上传完成
    @Override
    public void Complete() {
        uploadDialog.unRegisterReceiver();
        uploadDialog.dismiss();
        UIUtil.ToastshowShort(this, "上传作品完成！");
    }

    //如果附件时图片，则更新订单状态
    @Override
    public void uploadSuccess(String file_path) {
        UIUtil.showLog("uploadSuccess__file_path---->", file_path);
        UploadDao uploadDao = new UploadDao(this);
        uploadBean = uploadDao.queryOrder(order_id);
        Map<String, Object> map = new HashMap<String, Object>();
        String att_type = uploadBean.getAtt_type();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", order_id);
        map.put("pay_type", uploadBean.getPay_type());
        map.put("attr_type", att_type);
        map.put("pay_type", pay_type);
        map.put("attachment", file_path);
        map.put("is_pay", "1");
        presenter.updateOrder(map);
    }

    @Override
    public void uploadVideoPic(String video_pic) {
    }

    @Override
    public void uploadFailure(String error_code, String error_msg) {
        uploadDao.update("is_uploading", "0", "order_id");
        UIUtil.ToastshowShort(getApplicationContext(), "上传失败");
    }

    @Override
    public void getLocalUploadData() {
    }

    @Override
    public void updateProgress() {

    }

    @Override
    public void onSuccess(List<UploadBean> uploadBeanList) {

    }

    @Override
    public void UpdateOrderSuccess() {
        UIUtil.showLog("更新订单状态成功-->", "true");
    }

    @Override
    public void UpdateOrderFailure(String error_code, String error_msg) {

    }
}
