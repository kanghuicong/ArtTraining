package com.example.kk.arttraining.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.custom.dialog.DialogExchangeCoupon;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.homePage.activity.ChooseProvinceMain;
import com.example.kk.arttraining.ui.me.presenter.UpdatePresenter;
import com.example.kk.arttraining.ui.me.view.ChangePwdActivity;
import com.example.kk.arttraining.ui.me.view.ChoseOrgActivity;
import com.example.kk.arttraining.ui.me.view.ChoserIdentity;
import com.example.kk.arttraining.ui.me.view.IUpdateUserInfo;
import com.example.kk.arttraining.ui.me.view.MeMainActivity;
import com.example.kk.arttraining.ui.me.view.RegisterSendPhone;
import com.example.kk.arttraining.ui.me.view.UpdateNameSchoolActivity;
import com.example.kk.arttraining.ui.me.view.UpdatePhone;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.RandomUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/9/22 11:37
 * 说明:用户信息设置
 */
public class AboutActivity extends BaseActivity implements ISignleUpload, IUpdateUserInfo {
    //用户头像
    @InjectView(R.id.img_userheader)
    ImageView user_header;
    //选择用户头像
    @InjectView(R.id.ll_about_header)
    LinearLayout ll_user_header;
    //昵称
    @InjectView(R.id.ll_about_name)
    LinearLayout ll_about_name;
    //选择性别
    @InjectView(R.id.ll_about_sex)
    LinearLayout ll_about_sex;
    //选择地区
    @InjectView(R.id.ll_about_city)
    LinearLayout ll_about_city;
    //身份
    @InjectView(R.id.ll_about_identity)
    LinearLayout ll_about_identity;
    //学校
    @InjectView(R.id.ll_about_school)
    LinearLayout ll_about_school;
    //报考院校
    @InjectView(R.id.ll_about_intentional_college)
    LinearLayout ll_about_intentional_college;
    //培训机构
    @InjectView(R.id.ll_about_org)
    LinearLayout ll_about_org;
    //修改密码
    @InjectView(R.id.ll_about_chagePwd)
    LinearLayout ll_about_chagePwd;
    //手机号码
    @InjectView(R.id.ll_about_phone)
    LinearLayout ll_about_phone;
    @InjectView(R.id.about_tv_sex)
    TextView tv_about_sex;
    @InjectView(R.id.about_tv_name)
    TextView aboutTvName;
    @InjectView(R.id.about_tv_city)
    TextView aboutTvCity;
    @InjectView(R.id.about_tv_identity)
    TextView aboutTvIdentity;
    @InjectView(R.id.about_tv_school)
    TextView aboutTvSchool;
    @InjectView(R.id.about_tv_phone)
    TextView aboutTvPhone;
    @InjectView(R.id.about_tv_intentional)
    TextView aboutTvIntentional;
    @InjectView(R.id.about_tv_org)
    TextView aboutTvOrg;
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    private List<String> fileList;
    private String REQUEST_ERROR = "requestFailure";
    //选择图片dialog
    private PopWindowDialogUtil popWindowDialogUtil;
    //选择的图片的地址
    private String image_path;
    //拍照后图片的uri
    private Uri imageFileUri;
    String pic_name;
    private DialogExchangeCoupon dialogUtil;
    //选择机构
    public static final int CHOSE_ORG_CODE = 10001;
    //选择院校
    public static final int CHOSE_SCHOOL_CODE = 10002;
    //选择身份
    public static final int CHOSE_IDENTITY_CODE = 10003;

    public static final int UPDATE_PHONE = 10004;
    public static final int UPDATE_NAME = 10005;
    public static final int UPDATE_SCHOOL = 10006;
    public static final int CHOSE_CITY = 10007;
    private SignleUploadPresenter presenter;
    private String save_pic;
    private UpdatePresenter updatePresenter;
    private String city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_activity);
        ButterKnife.inject(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_overlay));
        init();
    }

    @Override
    public void init() {
        //设置头部标签栏信息
//        TitleBack.TitleBackActivity(AboutActivity.this, "个人信息");
        tvTitleBar.setText("个人信息");
        presenter = new SignleUploadPresenter(this);
        aboutTvPhone.setText(Config.userBean.getMobile());
        aboutTvSchool.setText(Config.userBean.getSchool());
        aboutTvIdentity.setText(Config.userBean.getIdentity());
        aboutTvCity.setText(Config.userBean.getCity());
        aboutTvName.setText(Config.userBean.getName());
        if (Config.userBean.getSex() != null && !Config.userBean.getSex().equals("")) {
            if (Config.userBean.getSex().equals("f")) {
                tv_about_sex.setText("女");
            } else if (Config.userBean.getSex().equals("m")) {
                tv_about_sex.setText("男");
            }
        }
        updatePresenter = new UpdatePresenter(this);
        aboutTvOrg.setText(Config.userBean.getOrg());
        aboutTvIntentional.setText(Config.userBean.getIntentional_college());
        Glide.with(getApplicationContext()).load(Config.userBean.getHead_pic()).transform(new GlideCircleTransform(AboutActivity.this)).error(R.mipmap.default_user_header).into(user_header);
    }

    @OnClick({R.id.iv_title_back, R.id.ll_about_school, R.id.ll_about_sex, R.id.ll_about_header, R.id.ll_about_city, R.id.ll_about_name, R.id.ll_about_identity, R.id.ll_about_intentional_college, R.id.ll_about_org, R.id.ll_about_chagePwd, R.id.ll_about_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            //用户头像
            case R.id.ll_about_header:
                choseImage();
                break;
            //用户性别
            case R.id.ll_about_sex:
                Intent sexIntent = new Intent(AboutActivity.this, ChoseSexActivity.class);
                startActivityForResult(sexIntent, 104);
//                startActivity(sexIntent);
                break;
            //地区
            case R.id.ll_about_city:
                Intent intent = new Intent(AboutActivity.this, ChooseProvinceMain.class);
                intent.putExtra("fromType", "about_city");
                startActivityForResult(intent, CHOSE_CITY);
                break;
            //用户昵称
            case R.id.ll_about_name:
                Intent intentName = new Intent(this, UpdateNameSchoolActivity.class);
                intentName.putExtra("fromType", "name");
                startActivityForResult(intentName, UPDATE_NAME);
                break;
            //身份
            case R.id.ll_about_identity:
                Intent identityIntent = new Intent(AboutActivity.this, ChoserIdentity.class);
                startActivityForResult(identityIntent, CHOSE_IDENTITY_CODE);
                break;
            //报考院校
            case R.id.ll_about_intentional_college:
                String college_name = aboutTvIntentional.getText().toString();
                Intent intentCollege = new Intent(this, ChoseOrgActivity.class);
                intentCollege.putExtra("fromType", "college");
                intentCollege.putExtra("values", college_name);
                startActivityForResult(intentCollege, CHOSE_SCHOOL_CODE);
                break;
            //培训机构
            case R.id.ll_about_org:
                String org_name = aboutTvOrg.getText().toString();
                Intent intentOrg = new Intent(this, ChoseOrgActivity.class);
                intentOrg.putExtra("fromType", "org");
                intentOrg.putExtra("values", org_name);
                startActivityForResult(intentOrg, CHOSE_ORG_CODE);
                break;
            //修改密码
            case R.id.ll_about_chagePwd:
                Intent intentChange=new Intent(this, RegisterSendPhone.class);
                intentChange.putExtra("from","change");
                startActivity(intentChange);
                break;
            //手机号码
            case R.id.ll_about_phone:
                Intent intenPhone = new Intent(this, UpdatePhone.class);
                startActivityForResult(intenPhone, UPDATE_PHONE);
                break;
            //学校
            case R.id.ll_about_school:
                Intent intentSchool = new Intent(this, UpdateNameSchoolActivity.class);
                intentSchool.putExtra("fromType", "school");
                startActivityForResult(intentSchool, UPDATE_SCHOOL);
                break;
            case R.id.iv_title_back:
                setResult(MeMainActivity.INTENT_ABOUT);
                finish();
                break;
        }
    }

    //更改用户头像
    public void choseImage() {
        popWindowDialogUtil = new PopWindowDialogUtil(AboutActivity.this, R.style.transparentDialog, R.layout.dialog_chose_header, "chosePic", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //拍照
                    case R.id.btn_takePic:
                        pic_name = StringUtils.getDataTime();
                        image_path = Environment
                                .getExternalStorageDirectory()
                                .getAbsolutePath()
                                + "/" + pic_name + ".jpg";
                        File file = new File(image_path);
                        imageFileUri = Uri.fromFile(file);
                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        it.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                        startActivityForResult(it, 102);
                        popWindowDialogUtil.dismiss();
                        break;
                    //从相册选择
                    case R.id.btn_chosePic:
                        pic_name = RandomUtils.getRandomInt() + ".jpg";
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 103);
                        popWindowDialogUtil.dismiss();
                        break;
                    //取消
                    case R.id.btn_header_cancel:
                        popWindowDialogUtil.dismiss();
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
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            //拍照回来后的处理
            case 102:
                if (resultCode == Activity.RESULT_OK) {
                    startPhotoZoom(imageFileUri);
                }
                break;
            //从相册中选择图片回来
            case 103:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过程。
                    //对图片进行裁剪
                    startPhotoZoom(uri);
                }
                break;
            case 104:
                try {
                    String sex = data.getStringExtra("sex");
                    if (sex.equals("m")) {
                        tv_about_sex.setText("男");
                    } else {
                        tv_about_sex.setText("女");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            //裁剪后回来操作，并上传图片
            case 002:

                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap mBitmap = bundle.getParcelable("data");
                    try {
                        File file = null;
                        file = FileUtil.saveFile(mBitmap, pic_name);
                        fileList = new ArrayList<String>();
                        Log.i("图片地址", file.toString() + "");
                        fileList.add(file.toString());
                        presenter.upload(fileList, 5);
                        image_path = file.toString();
                        Glide.with(getApplicationContext()).load(file).transform(new GlideCircleTransform(AboutActivity.this)).error(R.mipmap.default_user_header).into(user_header);
                        uploadSuccess(image_path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
//手机号码
            case UPDATE_PHONE:
                try {
                    String phone = data.getStringExtra("mobile");
                    if (!phone.equals(""))
                        aboutTvPhone.setText(phone);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
//姓名
            case UPDATE_NAME:
                try {
                    String user_name = data.getStringExtra("user_name");
                    if (!user_name.equals(""))
                        aboutTvName.setText(user_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            //学校
            case UPDATE_SCHOOL:
                try {
                    String school = data.getStringExtra("school");
                    if (!school.equals(""))
                        aboutTvSchool.setText(school);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            //报考院校
            case CHOSE_SCHOOL_CODE:

                try {
                    String college_name = data.getStringExtra("college_name");
                    if (!college_name.equals(""))
                        aboutTvIntentional.setText(college_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //机构
            case CHOSE_ORG_CODE:

                try {
                    String org_name = data.getStringExtra("org_name");
                    if (!org_name.equals(""))
                        aboutTvOrg.setText(org_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CHOSE_IDENTITY_CODE:
                try {
                    String identity_name = data.getStringExtra("identity_name");
                    if (!identity_name.equals(""))
                        aboutTvIdentity.setText(identity_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CHOSE_CITY:
//                UIUtil.showLog("city_name2222", data.getStringExtra("city_name") + "");
                if (data != null && !data.equals("")) {
                    aboutTvCity.setText(data.getStringExtra("city_name"));
                    int city_id = data.getIntExtra("city_id", 1);
                    city_name = data.getStringExtra("city_name");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("city_id", city_id);
                    map.put("city", city_name);
                    updatePresenter.updateUserInfo(map);
                }
                break;
        }


    }


    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true); //黑边
        intent.putExtra("scaleUpIfNeeded", true); //黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 002);

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result_code = (String) msg.obj;
            switch (result_code) {
                //更新头像成功
                case "0":
                    UserDao userDao = new UserDaoImpl(getApplicationContext());
                    userDao.Update(Config.UID, image_path, "head_pic");
                    break;
                //更新失败 token失效
                case "20039":
                    break;
                //网络请求失败
                case "requestFailure":

                    break;
            }
        }
    };


    @Override
    public void uploadSuccess(String file_path) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("head_pic", file_path);

        Callback<UpdateBean> headCallback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                Message msg = new Message();
                if (response.body() != null) {
                    UpdateBean updateHeadBean = response.body();
                    msg.obj = updateHeadBean.getError_code();
                    mHandler.sendMessage(msg);
                } else {
                    msg.obj = REQUEST_ERROR;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                Message msg = new Message();
                msg.obj = REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().updateHead(map);
        call.enqueue(headCallback);
    }

    @Override
    public void uploadVideoPic(String video_pic) {

    }

    @Override
    public void uploadFailure(String error_code,String error_msg) {

    }

    @Override
    public void updateInfo() {

    }

    @Override
    public void SuccessUpdate(UpdateBean updateBean) {
        UserDao userDao = new UserDaoImpl(getApplicationContext());
        userDao.Update(Config.UID, city_name, "city");
        Config.userBean.setCity(city_name);
        UIUtil.ToastshowShort(AboutActivity.this, "保存成功");
    }

    @Override
    public void FailureUpdate(String error_code, String error_msg) {
        switch (error_code) {
            case "404":
                UIUtil.ToastshowShort(this, error_msg);
                break;
            case "500":
                UIUtil.ToastshowShort(this, error_msg);
                break;
            case "400":
                UIUtil.ToastshowShort(this, error_msg);
                break;
            case Config.TOKEN_INVALID:
                UIUtil.ToastshowShort(this, "请重新登陆哦");
                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        setResult(MeMainActivity.INTENT_ABOUT);
        finish();
    }
}

