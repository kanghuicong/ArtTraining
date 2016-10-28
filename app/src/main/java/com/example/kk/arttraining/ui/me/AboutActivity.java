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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateHeadBean;
import com.example.kk.arttraining.custom.dialog.PopDialogUtil;
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
public class AboutActivity extends BaseActivity {
    @InjectView(R.id.iv_title_back)
    ImageView btn_bcak;
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


    private List<File> fileList;
    private String REQUEST_ERROR = "requestFailure";
    //选择图片dialog
    private PopDialogUtil popDialogUtil;
    //选择的图片的地址
    private String image_path;
    //拍照后图片的uri
    private Uri imageFileUri;

    String pic_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_activity);
        init();
    }

    @Override
    public void init() {
        //设置头部标签栏信息
        TitleBack.TitleBackActivity(AboutActivity.this, "个人信息");
        ButterKnife.inject(this);
        Glide.with(AboutActivity.this).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(AboutActivity.this)).error(R.mipmap.default_user_header).into(user_header);
    }

    @OnClick({R.id.ll_about_school, R.id.ll_about_sex, R.id.ll_about_header, R.id.ll_about_city, R.id.ll_about_name, R.id.ll_about_identity, R.id.ll_about_intentional_college, R.id.ll_about_org, R.id.ll_about_chagePwd, R.id.ll_about_phone})
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
                break;
            //用户昵称
            case R.id.ll_about_name:
                break;
            //身份
            case R.id.ll_about_identity:
                break;
            //报考院校
            case R.id.ll_about_intentional_college:
                break;
            //培训机构
            case R.id.ll_about_org:
                break;
            //修改密码
            case R.id.ll_about_chagePwd:
                break;
            //手机号码
            case R.id.ll_about_phone:
                break;
            //学校
            case R.id.ll_about_school:
                break;
        }
    }

    //更改用户头像
    public void choseImage() {
        popDialogUtil = new PopDialogUtil(AboutActivity.this, R.style.dialog, R.layout.dialog_chose_header, "chosePic", new PopDialogUtil.ChosePicDialogListener() {
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
                        popDialogUtil.dismiss();
                        break;
                    //从相册选择
                    case R.id.btn_chosePic:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 103);
                        popDialogUtil.dismiss();
                        break;
                    //取消
                    case R.id.btn_header_cancel:
                        popDialogUtil.dismiss();
                        break;
                }

            }
        });
//设置从底部显示
        Window window = popDialogUtil.getWindow();
        popDialogUtil.show();
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
//                if (Config.userBean.getSex().equals("m")) {
//                    tv_about_sex.setText("男");
//                } else {
//                    tv_about_sex.setText("女");
//                }

                break;


            //裁剪后回来操作，并上传图片
            case 002:
                Callback<UpdateHeadBean> headCallback = new Callback<UpdateHeadBean>() {
                    @Override
                    public void onResponse(Call<UpdateHeadBean> call, Response<UpdateHeadBean> response) {
                        Message msg = new Message();
                        if (response.body() != null) {
                            UpdateHeadBean updateHeadBean = response.body();
                            msg.obj = updateHeadBean.getError_code();
                            mHandler.sendMessage(msg);
                        } else {
                            msg.obj = REQUEST_ERROR;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateHeadBean> call, Throwable t) {
                        Message msg = new Message();
                        msg.obj = REQUEST_ERROR;
                        mHandler.sendMessage(msg);
                    }
                };
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap mBitmap = bundle.getParcelable("data");
                    try {
                        File file = null;
                        file = FileUtil.saveFile(mBitmap, pic_name);
                        Glide.with(AboutActivity.this).load(file).transform(new GlideCircleTransform(AboutActivity.this)).error(R.mipmap.default_user_header).into(user_header);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
                    userDao.Update(Config.UID, "user_header", Config.IMAGE_SAVE_PATH + pic_name + ".jpg");
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
}

