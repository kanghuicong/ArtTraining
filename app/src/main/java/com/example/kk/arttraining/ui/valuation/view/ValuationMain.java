package com.example.kk.arttraining.ui.valuation.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.Media.recodevideo.AudioActivity;
import com.example.kk.arttraining.Media.recodevideo.MediaActivity;
import com.example.kk.arttraining.Media.recodevideo.RecodeVideoActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.pay.PayActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
import com.example.kk.arttraining.ui.me.view.CouponActivity;
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
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.google.gson.Gson;

import java.io.IOException;
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

public class ValuationMain extends BaseActivity implements IValuationMain, PostingImageGridViewAdapter.PostingCallBack {
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
    //优惠券价格
    private String coupon_price = "0";
    //作品价格
    private String production_price = "0";
    //实付款
    private String real_price = "0";
    //作品标题
    private String production_title;
    //作品说明
    private String production_content;
    //作品文件地址
    private String production_path;
    //封装的名师列表
    private String teacher_list;

    private AudioInfoBean audioInfoBean= new AudioInfoBean();;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_main);
        ButterKnife.inject(ValuationMain.this);
        init();
    }

    @Override
    public void init() {
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_addpic_focused);

        loadingDialog = DialogUtils.createLoadingDialog(ValuationMain.this, "");
        audioFunc = new AudioRecordWav();
        valuationMainPresenter = new ValuationMainPresenter(this);
        TitleBack.TitleBackActivity(ValuationMain.this, "名师测评");
        Intent intent = getIntent();
        valuation_type = intent.getStringExtra("type");
        valuation_tv_type.setText(valuation_type);
        if (intent.getStringExtra("mold") != null) {
            UIUtil.showLog("ChooseTeacherItemClick0", intent.getStringExtra("mold"));
            mold = intent.getStringExtra("mold");
        }
        if ((List) intent.getStringArrayListExtra("tec") != null) {
            teacherList = (List) intent.getStringArrayListExtra("tec");
            Gson gson = new Gson();
            teacher_list = gson.toJson(teacherList);
            teacherGridViewAdapter = new ValuationGridViewAdapter(this, teacherList);
            valuationGvTeacher.setAdapter(teacherGridViewAdapter);
            valuationGvTeacher.setOnItemClickListener(new ChooseTeacherItemClick());
            valuation_iv_choseTeacher.setVisibility(View.GONE);
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
                map.put("total_pay", 0.01);
                map.put("coupon_pay", coupon_price);
                map.put("final", 0.01);
                map.put("teacher_list", teacher_list);
//
                valuationMainPresenter.CommitOrder(map);
//                CommitOrderBean commitOrderBean = new CommitOrderBean("10000001", "69", "测试", production_path);
//                CommitOrder(commitOrderBean);

                break;
            //选择作品
            case R.id.iv_enclosure:
                showDialog();
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

//                        choseProductionIntent.putExtra("media_type", "video");
//                        startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        choseProductionIntent = new Intent(ValuationMain.this, RecodeVideoActivity.class);
                        choseProductionIntent.putExtra("fromIntent", "production");
                        startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        break;
                    //选择音频
                    case R.id.btn_valutaion_dialog_music:

                        choseProductionIntent = new Intent(ValuationMain.this, AudioActivity.class);
                        choseProductionIntent.putExtra("fromIntent", "production");
                        startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
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

    //提交订单
    @Override
    public void CommitOrder(CommitOrderBean commitOrderBean) {
        commitOrderBean.setOrder_title(getProductionName());
        commitOrderBean.setOrder_price("0.01");
        Intent commitIntent = new Intent(ValuationMain.this, PayActivity.class);
        commitOrderBean.setFile_path(production_path);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_bean", commitOrderBean);
        bundle.putSerializable("att_bean", audioInfoBean);
        commitIntent.putExtras(bundle);
        startActivity(commitIntent);
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
                    float price = 0;
                    for (int i = 0; i < teacherList.size(); i++) {
                        TecInfoBean tecInfoBean = teacherList.get(i);
                        price = price + tecInfoBean.getAss_pay();
                    }
                    tv_cost.setText("￥" + price);
                    tv_real_cost.setText("￥" + price);

                    break;
                //选择作品返回
                case CHOSE_PRODUCTION:

                    Bundle bundle = data.getExtras();

                    audioInfoBean = (AudioInfoBean) bundle.getSerializable("media_info");
                    String type = bundle.getString("type");
                    production_path = audioInfoBean.getAudio_path();
                    UIUtil.showLog("audioInfoBean", audioInfoBean.toString() + "");
                    Config.att_type=type;
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
                    tv_coupon_cost.setText("￥" + coupon_price);
                    tv_coupon_cost.setVisibility(View.VISIBLE);
                    real_price = (Integer.parseInt(production_price) - Integer.parseInt(coupon_price)) + "";
                    tv_real_cost.setText("￥" + real_price);
                    valuation_main_right_image.setVisibility(View.GONE);
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

            }
        }
    };


    private class ChooseTeacherItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mold.equals("all")) {
                UIUtil.showLog("ChooseTeacherItemClick", mold);
                teacherList.remove(position);
                teacherGridViewAdapter.notifyDataSetChanged();
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

//                compressfile = ImageUtil.compressImage(this, Config.ProductionImageList);

            compressfile = Config.ProductionImageList;
            Gson gson = new Gson();
            String jsonString = gson.toJson(compressfile);
            production_path=jsonString;
            UIUtil.showLog("图片上传地址----->", jsonString + "");
            audioInfoBean.setImage_att(compressfile);
            audioInfoBean.setMedia_type("pic");
            PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(ValuationMain.this, compressfile, bmp, "valuation", this);
            gvValuationImage.setAdapter(adapter);
            Config.att_type="pic";
        } else {
            gvValuationImage.setVisibility(View.GONE);
            iv_enclosure.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Config.ProductionImageList!=null&&Config.ProductionImageList.size()!=0)
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
}
