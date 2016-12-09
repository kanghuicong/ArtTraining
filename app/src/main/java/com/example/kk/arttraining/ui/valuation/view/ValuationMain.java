package com.example.kk.arttraining.ui.valuation.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.Media.recodevideo.AudioActivity;
import com.example.kk.arttraining.Media.recodevideo.MediaActivity;
import com.example.kk.arttraining.Media.recodevideo.MediaPermissionUtils;
import com.example.kk.arttraining.Media.recodevideo.RecodeVideoActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.pay.PayActivity;
import com.example.kk.arttraining.pay.PaySuccessActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
import com.example.kk.arttraining.ui.me.view.CouponActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.valuation.adapter.ValuationGridViewAdapter;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.ui.valuation.chooseimage.ProductionImageGridClick;
import com.example.kk.arttraining.ui.valuation.chooseimage.ProductionImgFileList;
import com.example.kk.arttraining.ui.valuation.presenter.ValuationMainPresenter;
import com.example.kk.arttraining.utils.AudioRecordWav;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.GetSDKVersion;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.wxapi.UpdateOrderPaySuccess;
import com.example.kk.arttraining.wxapi.UpdatePayPresenter;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/31 09:52
 * 说明:测评主页面
 */

public class ValuationMain extends BaseActivity implements IValuationMain, PostingImageGridViewAdapter.PostingCallBack, UpdateOrderPaySuccess {
    //作品类型
    @InjectView(R.id.valuation_tv_type)
    TextView valuation_tv_type;
    //作品名称
    @InjectView(R.id.valuation_et_name)
    EditText valuation_et_name;
    //作品描述
    @InjectView(R.id.valuation_describe)
    EditText valuation_et_describe;
    //选择附件
    @InjectView(R.id.iv_enclosure)
    ImageView iv_enclosure;
    //选择名师
    @InjectView(R.id.valuation_iv_increase)
    ImageView valuation_iv_choseTeacher;
    //确定支付
    @InjectView(R.id.iv_sure_pay)
    ImageView iv_sure_pay;
    //实付金额
    @InjectView(R.id.tv_real_cost)
    TextView tv_real_cost;
    //测评费用
    @InjectView(R.id.tv_cost)
    TextView tv_cost;
    //优惠券金额
    @InjectView(R.id.tv_coupon_cost)
    TextView tv_coupon_cost;

    @InjectView(R.id.valuation_main_ll_coupons)
    LinearLayout ll_coupon;
    @InjectView(R.id.valuation_gv_teacher)
    MyGridView valuationGvTeacher;
    @InjectView(R.id.valuation_main_right_image)
    ImageView valuation_main_right_image;
    @InjectView(R.id.gv_valuation_image)
    MyGridView gvValuationImage;


    private String valuation_type;
    String mold = "all";
    private AudioRecordWav audioFunc;
    //选择老师
    public static final int CHOSE_TEACHER = 1001;
    //选择作品
    public static final int CHOSE_PRODUCTION = 1002;
    //选择优惠券
    public static final int CHOSE_COUPON = 1003;
    private List<TecInfoBean> teacherList = new ArrayList<TecInfoBean>();
    ValuationGridViewAdapter teacherGridViewAdapter;
    private Dialog loadingDialog;
    private ValuationMainPresenter valuationMainPresenter;
    private PopWindowDialogUtil popWindowDialogUtil;
    private Intent choseProductionIntent;
    List<String> listfile = new ArrayList<String>();
    ArrayList<String> compressfile = new ArrayList<String>();
    Bitmap bmp;
    /**
     * 变量
     */
    //优惠券id
    private int coupon_id;
    //优惠券类型
    private String coupon_type;
    //优惠券价格
    private String coupon_price = "0";
    //作品价格
    private String production_price = "0";
    //实付款
    private Double real_price = 0.0;
    //作品标题
    private String production_title;
    //作品说明
    private String production_content;
    //作品文件地址
    private String production_path;
    //封装的名师列表
    private String teacher_list;

    CommitOrderBean orderBean;
    private AudioInfoBean audioInfoBean = new AudioInfoBean();
    ;
    private UpdatePayPresenter presenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_main);
        ButterKnife.inject(ValuationMain.this);
        init();
    }

    @Override
    public void init() {
        //设置优惠券不能点击
        ll_coupon.setEnabled(false);

        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_addpic_focused);
        presenter = new UpdatePayPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(ValuationMain.this, "");
        audioFunc = new AudioRecordWav();
        valuationMainPresenter = new ValuationMainPresenter(this);
        TitleBack.TitleBackActivity(ValuationMain.this, "开小灶");
        Intent intent = getIntent();
        valuation_type = intent.getStringExtra("type");
        valuation_tv_type.setText(valuation_type);
        if (intent.getStringExtra("mold") != null) {
            UIUtil.showLog("ChooseTeacherItemClick0", intent.getStringExtra("mold"));
            mold = intent.getStringExtra("mold");
        }
        if ((List) intent.getStringArrayListExtra("tec") != null) {
            ll_coupon.setEnabled(true);
            teacherList = (List) intent.getStringArrayListExtra("tec");
            production_price = teacherList.get(0).getAss_pay() + "";
            real_price = Double.parseDouble(String.valueOf(teacherList.get(0).getAss_pay()));
            tv_cost.setText("￥" + production_price);
            tv_real_cost.setText("￥" + real_price);

            Gson gson = new Gson();
            teacher_list = gson.toJson(teacherList);
            teacherGridViewAdapter = new ValuationGridViewAdapter(this, teacherList);
            valuationGvTeacher.setAdapter(teacherGridViewAdapter);
            valuationGvTeacher.setOnItemClickListener(new ChooseTeacherItemClick());
            valuation_iv_choseTeacher.setVisibility(View.GONE);
        }
    }

    void JudgePermissions() {
        if (GetSDKVersion.getAndroidSDKVersion() >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    ) {
                showDialog();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA},
                        001);
            }
        } else {
            showDialog();
        }
    }

    @OnClick({R.id.valuation_iv_increase, R.id.valuation_describe, R.id.iv_sure_pay, R.id.iv_enclosure, R.id.valuation_main_ll_coupons})
    public void onClick(View v) {
        switch (v.getId()) {
            //选择老师
            case R.id.valuation_iv_increase:
                Intent intent_teacher = new Intent(this, ValuationChooseTeacher.class);
                intent_teacher.putStringArrayListExtra("teacher_list", (ArrayList) teacherList);
                intent_teacher.putExtra("spec", valuation_type);
                startActivityForResult(intent_teacher, CHOSE_TEACHER);
                break;
            //提交订单
            case R.id.iv_sure_pay:
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("ass_type", valuation_type);
                map.put("title", getProductionName());
                map.put("content", getProductionDescribe());
                map.put("attachment", getProductionPath());
//                map.put("total_pay", production_price);
                map.put("total_pay", production_price);
                map.put("coupon_pay", coupon_price);
                map.put("final", real_price);
                map.put("teacher_list", teacher_list);
                if (Integer.parseInt(coupon_price)!=0){
                    map.put("coupon_type", coupon_type);
                    map.put("coupon_id", coupon_id);
                }

//
                valuationMainPresenter.CommitOrder(map);
//                CommitOrderBean commitOrderBean = new CommitOrderBean("10000001", "69", "测试", production_path);
//                CommitOrder(commitOrderBean);

                break;
            //选择作品
            case R.id.iv_enclosure:
                JudgePermissions();
                break;

            case R.id.valuation_main_ll_coupons:
                Intent intent = new Intent(this, CouponActivity.class);
                intent.putExtra("from", "ValuationActivity");
                startActivityForResult(intent, CHOSE_COUPON);
                break;
        }
    }

    void showDialog() {
        choseProductionIntent = new Intent(ValuationMain.this, MediaActivity.class);
        popWindowDialogUtil = new PopWindowDialogUtil(ValuationMain.this, R.style.transparentDialog, R.layout.dialog_chose_production, "chose_production", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                popWindowDialogUtil.dismiss();
                switch (view.getId()) {

                    case R.id.btn_valutaion_dialog_cancel:

                        break;
                    case R.id.btn_valutaion_dialog_video:
                        if (MediaPermissionUtils.hasVideoPermission()) {
                            choseProductionIntent = new Intent(ValuationMain.this, RecodeVideoActivity.class);
                            choseProductionIntent.putExtra("fromIntent", "production");
                            startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        } else {
                            UIUtil.ToastshowShort(getApplicationContext(), "请打开拍照权限哦");
                        }


                        break;
                    //选择音频
                    case R.id.btn_valutaion_dialog_music:
                        if (MediaPermissionUtils.isHasAudioRecordPermission(ValuationMain.this)) {

                            choseProductionIntent = new Intent(ValuationMain.this, AudioActivity.class);
                            choseProductionIntent.putExtra("fromIntent", "production");
                            startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        } else {
                            UIUtil.ToastshowShort(getApplicationContext(), "请打开录音权限哦");
                        }

                        break;
                    case R.id.btn_valutaion_dialog_image:
                        Intent intent = new Intent(ValuationMain.this, ProductionImgFileList.class);
                        if (!mold.equals("all")) {
                            intent.putExtra("type", "all");
                        } else {
                            intent.putExtra("type", "onlyOne");
                        }
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
    public String getValuationName() {
        return valuation_et_name.getText().toString();
    }

    @Override
    public String getValuationDescribe() {
        return valuation_et_describe.getText().toString();
    }

    //设置付款金额
    @Override
    public void setCostPay() {

    }

    //设置实付金额
    @Override
    public void setRealCostPay() {

    }

    //获取作品名称
    @Override
    public String getProductionName() {
        production_title = valuation_et_name.getText().toString();
        return production_title;
    }

    //获取作品描述
    @Override
    public String getProductionDescribe() {
        production_content = valuation_et_describe.getText().toString();
        UIUtil.showLog("描述内容:", production_content + "");
        return production_content;
    }

    //获取选择老师信息
    @Override
    public List<TecInfoBean> getTeacherInfo() {
        return teacherList;
    }

    //获取作品文件路径
    @Override
    public String getProductionPath() {
        return production_path;
    }

    //提交订单成功，如果金额大于0则跳转到付款页面   或者跳转到支付成功页面
    @Override
    public void CommitOrder(CommitOrderBean commitOrderBean) {
        orderBean = commitOrderBean;
        UIUtil.showLog("real_price---->", real_price + "");
        //判断实际支付的价格
        if (real_price > 0) {
            commitOrderBean.setOrder_title(getProductionName());
            commitOrderBean.setOrder_price(real_price + "");
            Intent commitIntent = new Intent(ValuationMain.this, PayActivity.class);
            commitOrderBean.setFile_path(production_path);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order_bean", commitOrderBean);
            bundle.putSerializable("att_bean", audioInfoBean);
            commitIntent.putExtras(bundle);
            //保存密码
            Config.order_num = commitOrderBean.getOrder_number();
            Config.order_att_path = production_path;
            startActivity(commitIntent);

        }
        //当支付金额为0直接更新订单为成功
        else {
            updateOrder();
        }


    }

    @Override
    public void OnFailure(String error_code) {
        switch (error_code) {
            case "500":
                mHandler.sendEmptyMessage(500);
                break;
            case "501":
                mHandler.sendEmptyMessage(501);
                break;
            case "607":
                mHandler.sendEmptyMessage(607);
                break;
            case "606":
                mHandler.sendEmptyMessage(606);
                break;
            case "20028":
                mHandler.sendEmptyMessage(20028);
                break;
        }
    }

    //显示加载dialog
    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    //隐藏加载dialog
    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                //选择老师返回
                case CHOSE_TEACHER:

                    teacherList = (List) data.getExtras().getParcelableArrayList("teacher_list");
                    Gson gson = new Gson();
                    teacher_list = gson.toJson(teacherList);
                    teacherGridViewAdapter = new ValuationGridViewAdapter(this, teacherList);
                    valuationGvTeacher.setAdapter(teacherGridViewAdapter);
                    valuationGvTeacher.setOnItemClickListener(new ChooseTeacherItemClick());
                    double price = 0.0;
                    for (int i = 0; i < teacherList.size(); i++) {
                        TecInfoBean tecInfoBean = teacherList.get(i);
                        price = price + tecInfoBean.getAss_pay();
                    }
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    String temp_price = df.format(price);
                    price = StringUtils.toDouble(temp_price);
                    production_price = price + "";
                    tv_cost.setText("￥" + price);
                    tv_real_cost.setText("￥" + price);
                    real_price = price;
                    //选择老师回来设置可以选择优惠券
                    ll_coupon.setEnabled(true);
                    break;
                //选择作品返回
                case CHOSE_PRODUCTION:

                    Bundle bundle = data.getExtras();

                    audioInfoBean = (AudioInfoBean) bundle.getSerializable("media_info");
                    String type = bundle.getString("type");
                    production_path = audioInfoBean.getAudio_path();
                    UIUtil.showLog("audioInfoBean", audioInfoBean.toString() + "");
                    Config.att_type = type;
                    if (FileUtil.getFileType(production_path).equals("jpg") || FileUtil.getFileType(production_path).equals("png")) {
                        UIUtil.ToastshowShort(this, "不能选择图片作为作品附件哦！");
                        production_path = "";
                    } else {
                        if (type.equals("video")) {
                            iv_enclosure.setImageResource(R.mipmap.default_video_icon);
                        } else {
                            iv_enclosure.setImageResource(R.mipmap.default_music_icon);
                        }
                    }

                    break;
                //选择优惠券返回
                case CHOSE_COUPON:
                    coupon_price = data.getStringExtra("values");
                    real_price = (StringUtils.toDouble(production_price) - StringUtils.toDouble(coupon_price));
                    coupon_id = data.getIntExtra("coupon_id", 0);
                    coupon_type = data.getStringExtra("coupon_type");

                    if (real_price < 0) {
                        valuation_main_right_image.setVisibility(View.VISIBLE);
                        tv_real_cost.setText("￥" + production_price);
                        UIUtil.ToastshowShort(this, "此优惠券不能使用");
                        coupon_price = "0";
                    } else {
                        tv_coupon_cost.setVisibility(View.VISIBLE);
                        tv_coupon_cost.setText("￥" + coupon_price);
                        tv_real_cost.setText("￥" + real_price);
                    }
                    break;
            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 500:
                    UIUtil.ToastshowShort(ValuationMain.this, getResources().getString(R.string.connection_timeout));
                    break;
                case 501:
                    UIUtil.ToastshowShort(ValuationMain.this, getResources().getString(R.string.data_no_full));
                    break;
                case 606:
                    UIUtil.ToastshowShort(ValuationMain.this, "作品描述最多只能输入200字哦！");
                    break;
                case 607:
                    UIUtil.ToastshowShort(ValuationMain.this, "作品标题最多只能输入20字哦！");
                    break;
                case 20028:
                    startActivity(new Intent(ValuationMain.this, UserLoginActivity.class));
                    UIUtil.ToastshowShort(ValuationMain.this, "登录失效，请重新登陆");
                    break;

            }
        }
    };

    @Override
    public void updateOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", orderBean.getOrder_number());
        map.put("is_pay", "1");
        presenter.updateOrder(map);
    }

    @Override
    public void Success() {
        updateOrderUpload();
        Intent commitIntent = new Intent(ValuationMain.this, PaySuccessActivity.class);
//        commitOrderBean.setFile_path(production_path);
        commitIntent.putExtra("file_path", production_path);
        commitIntent.putExtra("order_id", orderBean.getOrder_number());
        commitIntent.putExtra("token", Config.ACCESS_TOKEN);
        //保存密码
        Config.order_num = orderBean.getOrder_number();
        Config.order_att_path = production_path;
        startActivity(commitIntent);
    }

    @Override
    public void Failure(String error_code, String error_msg) {
        UIUtil.ToastshowShort(getApplicationContext(), error_code);
    }


    //删除所选老师
    private class ChooseTeacherItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mold.equals("all")) {
                UIUtil.showLog("ChooseTeacherItemClick", mold);
                teacherList.remove(position);
                teacherGridViewAdapter.notifyDataSetChanged();
                double price = 0.0;
                if (teacherList != null && teacherList.size() > 0) {
                    for (int i = 0; i < teacherList.size(); i++) {
                        TecInfoBean tecInfoBean = teacherList.get(i);
                        price = price + tecInfoBean.getAss_pay();
                    }
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    String temp_price = df.format(price);
                    price = StringUtils.toDouble(temp_price);
                    production_price = price + "";
                    tv_cost.setText("￥" + price);
                    tv_real_cost.setText("￥" + price);
                    real_price = price;
                } else {
                    tv_cost.setText("￥" + 0.0);
                    tv_real_cost.setText("￥" + 0.0);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.ProductionImageList != null && Config.ProductionImageList.size() != 0) {
            UIUtil.showLog("ProductionImageList", Config.ProductionImageList.size() + "---");
            gvValuationImage.setVisibility(View.VISIBLE);
            iv_enclosure.setVisibility(View.GONE);

            compressfile.clear();
            compressfile.addAll(Config.ProductionImageList);

            Gson gson = new Gson();
            String jsonString = gson.toJson(compressfile);
            production_path = jsonString;
            audioInfoBean.setImage_att(compressfile);
            audioInfoBean.setMedia_type("pic");
            PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(ValuationMain.this, compressfile, bmp, "valuation", this);
            gvValuationImage.setAdapter(adapter);
            Config.att_type = "pic";
        } else {
            gvValuationImage.setVisibility(View.GONE);
            iv_enclosure.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Config.ProductionImageList != null && Config.ProductionImageList.size() != 0)
            Config.ProductionImageList.clear();


    }

    @Override
    public void backResult() {
        gvValuationImage.setVisibility(View.GONE);
        iv_enclosure.setVisibility(View.VISIBLE);
    }

    @Override
    public void subResult(List<String> listfile) {
        UIUtil.showLog("ValuationImage", Config.ProductionImageList.size() + "");
        gvValuationImage.setOnItemClickListener(new ProductionImageGridClick(this, compressfile, mold));
    }

    void updateOrderUpload() {
        UploadDao uploadDao = new UploadDao(this);
        UploadBean uploadBean = new UploadBean();
        uploadBean.setOrder_pic("");
        uploadBean.setProgress(0);
        uploadBean.setOrder_title(getProductionName());
        uploadBean.setCreate_time(orderBean.getCreate_time());
        uploadBean.setOrder_id(orderBean.getOrder_number());
        uploadBean.setAtt_length(audioInfoBean.getAudio_length() + "");
        uploadBean.setAtt_size(audioInfoBean.getAudio_size() + "");
        uploadBean.setAtt_type(Config.att_type);
        uploadBean.setFile_path(production_path);
        uploadBean.setPay_type("wxpay");
        Config.order_num = orderBean.getOrder_number();
        UIUtil.showLog("payActivity----->", "uploadbean---->" + uploadBean.toString());
        uploadDao.insert(uploadBean);
    }

    //权限获取回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                showDialog();
            } else {
                popWindowDialogUtil.dismiss();
                Toast.makeText(this, "获取权限失败,无法选择附件", Toast.LENGTH_LONG).show();
            }
        }
    }


}