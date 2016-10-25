package com.example.kk.arttraining.ui.me;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateHeadBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.UploadUtils;

import java.io.File;
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
    private List<File> fileList;
    private String REQUEST_ERROR = "requestFailure";

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
//        btn_bcak.setImageResource(R.mipmap.bt_left_white);
        //findview
        ButterKnife.inject(this);
        Glide.with(AboutActivity.this).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(AboutActivity.this)).error(R.mipmap.default_user_header).into(user_header);
    }

    @OnClick({R.id.ll_about_school, R.id.ll_about_sex, R.id.ll_about_header, R.id.ll_about_city, R.id.ll_about_name, R.id.ll_about_identity, R.id.ll_about_intentional_college, R.id.ll_about_org, R.id.ll_about_chagePwd, R.id.ll_about_phone})
    public void onClick(View v) {
        finish();
        switch (v.getId()) {
            //用户头像
            case R.id.ll_about_header:
                choseImage();
                break;
            //用户性别
            case R.id.ll_about_sex:
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 103);

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 103) {// 是否选择，没选择就不会继续
            String img_path;
            Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过程。
            //对图片进行裁剪
            startPhotoZoom(uri);


        } else if (requestCode == 002) {
            Callback<UpdateHeadBean> headCallback = new Callback<UpdateHeadBean>() {
                @Override
                public void onResponse(Call<UpdateHeadBean> call, Response<UpdateHeadBean> response) {
                    Message msg = new Message();
                    if (response.body() != null) {
                        UpdateHeadBean updateHeadBean = response.body();
                        msg.obj = updateHeadBean.getError_code();
                    } else {
                        msg.obj = REQUEST_ERROR;
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
                File file = bundle.getParcelable("data");
                UIUtil.showLog("file", file.getPath()+"");
                user_header.setImageBitmap(mBitmap);
//                File file=FileUtil.saveBitmapInFile(Config.IMAGE_SAVE_PATH, image_name, mBitmap, Bitmap.CompressFormat.JPEG);
                UploadUtils.uploadFile(file, headCallback);
            }


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

