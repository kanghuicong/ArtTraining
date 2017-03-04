package com.example.kk.arttraining.ui.me.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.kk.arttraining.media.recodevoice.AudioActivity;
import com.example.kk.arttraining.media.recodevoice.MediaActivity;
import com.example.kk.arttraining.media.recodevideo.MediaPermissionUtils;
import com.example.kk.arttraining.media.recodevideo.RecodeVideoActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.OrderBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.view.BottomPullSwipeRefreshLayout;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.me.adapter.OrderAdapter;
import com.example.kk.arttraining.ui.me.presenter.OrderPresenter;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.chooseimage.ProductionImgFileList;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;
import com.example.kk.arttraining.utils.upload.view.IUploadProgressListener;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 作者：wschenyongyin on 2016/10/23 13:33
 * 说明:
 */
@SuppressLint({"NewApi", "ValidFragment"})
public class OrderAllFragment extends Fragment implements IOrderView, BottomPullSwipeRefreshLayout.OnRefreshListener, BottomPullSwipeRefreshLayout.OnLoadListener, IOrderChoseProduction, IUploadProgressListener, ISignleUpload, IUploadFragment {

    ListView lv_order;
    private View view;
    private Context context;
    private List<OrderBean> list;
    private OrderAdapter orderAdapter;
    private int count;
    private OrderPresenter presenter;
    private boolean REFRESH_FIRST_FLAG = true;
    private List<OrderBean> listData;
    BottomPullSwipeRefreshLayout swipeRefreshLayout;
    private PopWindowDialogUtil popWindowDialogUtil;
    private Intent choseProductionIntent;

    AudioInfoBean audioInfoBean;
    private String production_path;
    private UploadDao uploadDao;
    private String order_id;
    private UploadDialog uploadDialog;
    private SignleUploadPresenter signleUploadPresenter;
    ArrayList<String> compressfile = new ArrayList<String>();
    private UploadPresenter uploadPresenter;
    private boolean UP_LOAD_IMAGE = false;

    private OrderBean orderBean;
    private String REQUEST_TYPE="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        presenter = new OrderPresenter(this);
        view = View.inflate(context, R.layout.me_order_fragment, null);
        lv_order = (ListView) view.findViewById(R.id.lv_order);
        ButterKnife.inject(view);
        initView();
        return view;
    }

    private void initView() {
        signleUploadPresenter = new SignleUploadPresenter(this);
        uploadPresenter = new UploadPresenter(this);
        uploadDao = new UploadDao(context);
        swipeRefreshLayout = new BottomPullSwipeRefreshLayout(context);
        swipeRefreshLayout = (BottomPullSwipeRefreshLayout) view.findViewById(R.id.order_swipe);
        swipeRefreshLayout.setColorSchemeColors(android.graphics.Color.parseColor("#87CEFA"));
        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.autoRefresh();


    }

    @Override
    public void onRefresh() {
        REQUEST_TYPE="refresh";
        getAllOrder(REQUEST_TYPE);
    }

    @Override
    public void onLoad() {
        REQUEST_TYPE="load";
        getAllOrder(REQUEST_TYPE);
    }


    @Override
    public void getAllOrder(String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        if (type.equals("load")) map.put("self", orderAdapter.getSelfId());
        presenter.getAllOrderData(map, type);
    }

    @Override
    public void unPayOrder(String type) {

    }

    @Override
    public void AlreadyPaid(String type) {

    }

    @Override
    public void SuccessRefresh(List<OrderBean> payOrderList) {
        swipeRefreshLayout.setRefreshing(false);
        listData = payOrderList;
        if (listData.size() >= 4) swipeRefreshLayout.setOnLoadListener(this);
        if (REFRESH_FIRST_FLAG) {
            orderAdapter = new OrderAdapter(context, listData, this);
            lv_order.setAdapter(orderAdapter);
        } else {
            orderAdapter.notifyDataSetChanged();
        }
    }

    //加载成功
    @Override
    public void SuccessLoad(List<OrderBean> payOrderList) {
        swipeRefreshLayout.setLoading(false);
        listData.addAll(payOrderList);
        orderAdapter.refreshCount(listData.size());
        orderAdapter.notifyDataSetChanged();

    }


    @Override
    public void showFailedError(String error_code, String errorMsg) {
        if(REQUEST_TYPE.equals("refresh")){
            swipeRefreshLayout.setRefreshing(false);
        }else if(REQUEST_TYPE.equals("load")){
            swipeRefreshLayout.setLoading(false);
        }
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(context, context.getResources().getString(R.string.toast_token_nvalid));
            startActivity(new Intent(context,UserLoginActivity.class));
        } else if (error_code.equals("20007")) {
            UIUtil.ToastshowShort(context, "没有更多订单了哦！");
        } else {
            UIUtil.ToastshowShort(context, errorMsg);
        }

    }

    //选择作品
    @Override
    public void choseProduction(OrderBean orderBean) {
        this.order_id = orderBean.getOrder_number();
        this.orderBean=orderBean;
        UIUtil.showLog("orderBean----->",orderBean.toString()+"");
        showDialog();
    }

    void showDialog() {
        choseProductionIntent = new Intent(context, MediaActivity.class);
        popWindowDialogUtil = new PopWindowDialogUtil(context, R.style.transparentDialog, R.layout.dialog_chose_production, "chose_production", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                popWindowDialogUtil.dismiss();
                switch (view.getId()) {

                    case R.id.btn_valutaion_dialog_cancel:

                        break;
                    case R.id.btn_valutaion_dialog_video:
                        if (MediaPermissionUtils.hasVideoPermission()) {
                            choseProductionIntent = new Intent(context, RecodeVideoActivity.class);
                            choseProductionIntent.putExtra("fromIntent", "production");
                            startActivityForResult(choseProductionIntent, ValuationMain.CHOSE_PRODUCTION);
                        } else {
                            UIUtil.ToastshowShort(context.getApplicationContext(), "请打开拍照权限哦");
                        }
                        break;
                    //选择音频
                    case R.id.btn_valutaion_dialog_music:
                        if (MediaPermissionUtils.isHasAudioRecordPermission(context)) {

                            choseProductionIntent = new Intent(context, AudioActivity.class);
                            choseProductionIntent.putExtra("fromIntent", "production");
                            startActivityForResult(choseProductionIntent, ValuationMain.CHOSE_PRODUCTION);
                        } else {
                            UIUtil.ToastshowShort(context.getApplicationContext(), "请打开录音权限哦");
                        }

                        break;
                    case R.id.btn_valutaion_dialog_image:
                        UP_LOAD_IMAGE = true;
                        Intent intent = new Intent(context, ProductionImgFileList.class);
                        intent.putExtra("type", "all");
                        startActivity(intent);
                        break;
                }
            }
        });
        //设置从底部显示
        Window window = popWindowDialogUtil.getWindow();
        popWindowDialogUtil.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case ValuationMain.CHOSE_PRODUCTION:
                    Bundle bundle = data.getExtras();
                    audioInfoBean = (AudioInfoBean) bundle.getSerializable("media_info");
                    String type = bundle.getString("type");
                    production_path = audioInfoBean.getAudio_path();
                    UIUtil.showLog("audioInfoBean", audioInfoBean.toString() + "");
                    Config.att_type = type;
                    if (type.equals("video")) {
                        //上传缩略图
                        signleUploadPresenter.uploadVideoPic(audioInfoBean.getVideo_pic(), 6);
                    } else {
                        uploadAtt();
                    }
                    UploadDao uploadDao = new UploadDao(context);
                    UploadBean uploadBean = new UploadBean();
                    uploadBean.setOrder_pic("");
                    uploadBean.setProgress(0);
                    uploadBean.setOrder_title(orderBean.getWork_title()+"");
                    uploadBean.setCreate_time("");
                    uploadBean.setOrder_id(orderBean.getOrder_number());
                    uploadBean.setAtt_length(audioInfoBean.getAudio_length() + "");
                    uploadBean.setAtt_size(audioInfoBean.getAudio_size() + "");
                    uploadBean.setAtt_type(Config.att_type);
                    uploadBean.setFile_path(production_path);
                    uploadBean.setPay_type("wxpay");
                    Config.order_num = orderBean.getOrder_number();
                    UIUtil.showLog("payActivity----->", "uploadbean---->" + uploadBean.toString());
                    uploadDao.insert(uploadBean);


                    break;
            }
        }
    }

    //上传音频或视频文件
    void uploadAtt() {
        uploadDao.update("is_uploading", "1", "order_id");
        UIUtil.showLog("payactivity-->", "startUpload");
        Intent intent = new Intent(context, UploadQiNiuService.class);
        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", production_path);
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        intent.putExtra("order_id", order_id);
        context.startService(intent);

        uploadDialog = new UploadDialog(context, R.layout.dialog_upload, R.style.Dialog, new UploadDialog.UploadListener() {
            @Override
            public void onClick(View view) {
                uploadDialog.unRegisterReceiver();
                uploadDialog.dismiss();
            }
        }, this);
        uploadDialog.show();
        //更新上传状态为正在上传
        uploadDao.update("is_uploading", "1", order_id);
        orderAdapter.notifyDataSetChanged();
    }

    //上传图片附件
    void uploadImage() {
        UIUtil.showLog("file_path---->", production_path + "");
        List<String> fileFist = new ArrayList<String>();
        JSONArray jsonArray = null;
        uploadDao.update("is_uploading", "1", "order_id");
        try {
            jsonArray = new JSONArray(production_path);
            for (int i = 0; i < jsonArray.length(); i++) {
                String path = jsonArray.getString(i);
                fileFist.add(path);
            }
            signleUploadPresenter.upload(fileFist, 6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //监听上传附件成功
    @Override
    public void Complete() {
        uploadDialog.unRegisterReceiver();
        uploadDialog.dismiss();
        UIUtil.ToastshowShort(context.getApplicationContext(), "上传作品完成！");
    }

    //上传图片成功
    @Override
    public void uploadSuccess(String file_path) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", order_id);
        map.put("attr_type", Config.att_type);
        map.put("attachment", file_path);
        map.put("is_pay", "1");
        uploadPresenter.updateOrder(map);
    }

    //上传缩略图成功
    @Override
    public void uploadVideoPic(String video_pic) {
        uploadAtt();
    }

    //上传图片失败
    @Override
    public void uploadFailure(String error_code, String error_msg) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (UP_LOAD_IMAGE) {
            if (Config.ProductionImageList != null && Config.ProductionImageList.size() != 0) {
                UIUtil.showLog("ProductionImageList", Config.ProductionImageList.size() + "---");

                compressfile.clear();
                compressfile.addAll(Config.ProductionImageList);

                Gson gson = new Gson();
                String jsonString = gson.toJson(compressfile);
                production_path = jsonString;
                audioInfoBean = new AudioInfoBean();
                audioInfoBean.setImage_att(compressfile);
                audioInfoBean.setMedia_type("pic");
                Config.att_type = "pic";
                uploadImage();
            } else {
                UP_LOAD_IMAGE = false;
            }
        }

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
        UIUtil.showLog("更新订单成功--》", "true");
    }

    @Override
    public void UpdateOrderFailure(String error_code, String error_msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
