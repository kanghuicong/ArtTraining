package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.Media.recodevideo.AudioActivity;
import com.example.kk.arttraining.Media.recodevideo.RecodeVideoActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.custom.view.HideKeyboardActivity;
import com.example.kk.arttraining.ui.homePage.adapter.PostingImageGridViewAdapter;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageGridClick;
import com.example.kk.arttraining.ui.homePage.function.posting.ImageUtil;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingDialog;
import com.example.kk.arttraining.ui.homePage.function.posting.PostingTextChangeListener;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.ProgressDialog;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.presenter.SignleUploadPresenter;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;

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
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class PostingMain extends HideKeyboardActivity implements View.OnClickListener, PostingImageGridViewAdapter.PostingCallBack, ISignleUpload {

    @InjectView(R.id.et_posting_text)
    EditText etPostingText;
    @InjectView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @InjectView(R.id.ll_reshow)
    LinearLayout llReshow;
    @InjectView(R.id.ll_posting_type)
    LinearLayout llPostingType;
    @InjectView(R.id.iv_posting_image)
    ImageView ivPostingImage;
    @InjectView(R.id.iv_posting_video)
    ImageView ivPostingVideo;
    @InjectView(R.id.iv_posting_audio)
    ImageView ivPostingAudio;
    @InjectView(R.id.ll_posting_result_music)
    LinearLayout llPostingResultMusic;
    @InjectView(R.id.ll_posting_result_video)
    LinearLayout llPostingResultVideo;
    @InjectView(R.id.iv_music_fork)
    ImageView ivMusicFork;
    @InjectView(R.id.iv_video_fork)
    ImageView ivVideoFork;

    private Dialog progressDialog;
    String success_imagePath;
    String content = "";
    List<String> listfile = new ArrayList<String>();
    ArrayList<String> compressfile = new ArrayList<String>();
    Bitmap bmp;
    int content_number = 250;
    PostingImageGridViewAdapter adapter;
    public final static int POST_MAIN_VIDEO_CODE = 10001;
    public final static int POST_MAIN_AUDIO_CODE = 10002;
    //选择的视频文件大小
    private long video_size = 0;
    //选择的音频文件大小
    private long audio_size = 0;
    //选择文件的地址
    private String file_path;
    //最大的文件限制大小
    private long maxFileSize = 50 * 1024 * 1024;
    //文件上传成功后返回的地址
    private String upload_path = "";
    //将要上传的文件封装成list
    private List<String> uploadList;
    //文件类型
    private String attr_type = "";
    //请求错误码
    private String error_code = null;
    //发帖文件上传类
    private SignleUploadPresenter presenter;
    //视频封面
    private String video_pic;

    private String duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_posting);
        ButterKnife.inject(this);
        progressDialog = ProgressDialog.show(PostingMain.this, "正在发表");
        TitleBack.PosingTitleBackActivity(this, "发帖", "发布");
        PostingTextChangeListener.getTextChangeListener(this, etPostingText, content_number);
        Bundle bundle = getIntent().getExtras();
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_addpic_focused);
        progressDialog = DialogUtils.createLoadingDialog(this, "正在发表");
        uploadList = new ArrayList<String>();
        if (bundle != null) {
            if (bundle.get("type").equals("image")) {
                noScrollgridview.setVisibility(View.VISIBLE);
                llPostingType.setVisibility(View.GONE);
                if (bundle.getStringArrayList("files") != null) {
                    listfile = bundle.getStringArrayList("files");
                    if (listfile.size() != 0) {
                        try {
                            compressfile = ImageUtil.compressImage(this, listfile);
                            uploadList = compressfile;
                            attr_type = "pic";
                            PostingImageGridViewAdapter adapter = new PostingImageGridViewAdapter(PostingMain.this, compressfile, bmp, this);
                            noScrollgridview.setAdapter(adapter);
                            noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        noScrollgridview.setVisibility(View.GONE);
                        llPostingType.setVisibility(View.VISIBLE);
                    }
                } else {
                    noScrollgridview.setVisibility(View.GONE);
                    llPostingType.setVisibility(View.VISIBLE);
                }
                if (bundle.getString("evaluate_content") != null) {
                    String content = bundle.getString("evaluate_content");
                    etPostingText.setText(content + "");
                }
            }
        }
    }

    @OnClick({R.id.iv_posting_image, R.id.iv_posting_video, R.id.iv_posting_audio, R.id.tv_title_subtitle, R.id.iv_video_fork, R.id.iv_music_fork})
    public void onClick(View view) {
        //用于接收返回的文件地址

        switch (view.getId()) {
            case R.id.tv_title_subtitle:

                    content = etPostingText.getText().toString();
                    presenter = new SignleUploadPresenter(this);
                    progressDialog.show();
                    if (uploadList != null && uploadList.size() != 0 && !content.equals("")) {
                        //有附件有内容
                        if (content.length() < content_number) {

                            //判断文件大小
                            switch (attr_type) {
                                case "video":
                                    if (video_size > maxFileSize) {
                                        UIUtil.ToastshowShort(this, "上传附件太大，请重新选择");
                                        progressDialog.dismiss();
                                    } else {
                                        presenter.uploadVideoPic(video_pic,1);
                                    }
                                    break;
                                case "music":
                                    if (audio_size > maxFileSize) {
                                        UIUtil.ToastshowShort(this, "上传附件太大，请重新选择");
                                        progressDialog.dismiss();
                                    } else {
                                        presenter.upload(uploadList,1);
                                    }
                                    break;
                                case "pic":
                                    presenter.upload(uploadList,1);
                                    break;
                            }
                        } else {
                            progressDialog.dismiss();
                            UIUtil.ToastshowShort(this, "您输入的内容过长，无法发表...");
                        }
                    }
                    //没附件有内容
                    else if ((uploadList == null || uploadList.size() == 0) && content != null && !content.equals("")) {
                        if (content.length() < content_number) {
                            PostRequest();
                        } else {
                            progressDialog.dismiss();
                            UIUtil.ToastshowShort(this, "请输入发布的内容");
                        }
                    }
                    //有附件没内容
                    else if ((uploadList != null && uploadList.size() != 0) && (content.equals(""))) {
                        //判断文件大小
                        switch (attr_type) {
                            case "video":
                                if (video_size > maxFileSize) {
                                    progressDialog.dismiss();
                                    UIUtil.ToastshowShort(this, "上传附件太大，请重新选择");
                                } else {
                                    presenter.uploadVideoPic(video_pic,1);
                                }
                                break;
                            case "music":
                                if (audio_size > maxFileSize) {
                                    progressDialog.dismiss();
                                    UIUtil.ToastshowShort(this, "上传附件太大，请重新选择");
                                } else {
                                    presenter.upload(uploadList,1);
                                }
                                break;
                        }


                    } else {
                        progressDialog.dismiss();
                        UIUtil.ToastshowShort(this, "请输入发布的内容");
                    }

                break;
            case R.id.iv_posting_image:
                PostingDialog.showDialog(this, listfile, etPostingText.getText().toString());
                break;
            case R.id.iv_posting_video:
                Intent VideoIntent = new Intent(PostingMain.this, RecodeVideoActivity.class);
                VideoIntent.putExtra("fromIntent", "postingMain");
                startActivityForResult(VideoIntent, POST_MAIN_VIDEO_CODE);
                break;
            case R.id.iv_posting_audio:
                Intent AudioIntent = new Intent(PostingMain.this, AudioActivity.class);
                AudioIntent.putExtra("fromIntent", "postingMain");
                startActivityForResult(AudioIntent, POST_MAIN_AUDIO_CODE);
                break;
            case R.id.iv_video_fork:
                llPostingResultVideo.setVisibility(View.GONE);
                llPostingType.setVisibility(View.VISIBLE);
                uploadList.clear();
                break;
            case R.id.iv_music_fork:
                llPostingResultMusic.setVisibility(View.GONE);
                llPostingType.setVisibility(View.VISIBLE);
                uploadList.clear();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 102) {
            try {
                compressfile = ImageUtil.compressImage(this, listfile);
                Config.ShowImageList = compressfile;
                noScrollgridview.setVisibility(View.VISIBLE);
                llPostingType.setVisibility(View.GONE);
                uploadList = Config.ShowImageList;
                adapter = new PostingImageGridViewAdapter(PostingMain.this,
                        compressfile, bmp, this);
                noScrollgridview.setAdapter(adapter);
                noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, Config.ShowImageList, listfile, etPostingText.getText().toString()));
                attr_type = "pic";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 001 && requestCode == 001) {
            int postion = data.getIntExtra("postion", 1);
            compressfile.remove(postion);
            Config.ShowImageList = compressfile;
            //将选完的地址赋值给上传地址list
            uploadList = compressfile;
            //设置文件类型
            attr_type = "pic";
            handler.sendEmptyMessage(0);
        }
        //选择视频回来操作
        else if (resultCode == POST_MAIN_VIDEO_CODE && requestCode == POST_MAIN_VIDEO_CODE) {
            // TODO: 2016/11/8  选择视频回来的逻辑操作 返回一个"file_path","file_size"; 需要判断文件大小是否超过规定
            llPostingResultVideo.setVisibility(View.VISIBLE);
            llPostingType.setVisibility(View.GONE);
            AudioInfoBean audioInfoBean = (AudioInfoBean) data.getSerializableExtra("media_info");
            file_path = audioInfoBean.getAudio_path();
            video_size = audioInfoBean.getAudio_size();
            video_pic = audioInfoBean.getVideo_pic();
            uploadList.add(file_path);
            attr_type = "video";

        }
        //选择音频回来操作
        else if (resultCode == POST_MAIN_AUDIO_CODE && requestCode == POST_MAIN_AUDIO_CODE) {
            llPostingResultMusic.setVisibility(View.VISIBLE);
            llPostingType.setVisibility(View.GONE);
            attr_type = "music";
            AudioInfoBean audioInfoBean = (AudioInfoBean) data.getSerializableExtra("media_info");
            file_path = audioInfoBean.getAudio_path();
            audio_size = audioInfoBean.getAudio_size();
            duration=audioInfoBean.getAudio_length();
            uploadList.add(file_path);

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new PostingImageGridViewAdapter(PostingMain.this,
                    compressfile, bmp, new PostingImageGridViewAdapter.PostingCallBack() {
                @Override
                public void backResult() {
                    noScrollgridview.setVisibility(View.GONE);
                    llPostingType.setVisibility(View.VISIBLE);
                }

                @Override
                public void subResult(List<String> listfile) {
                    noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
                }
            });
            noScrollgridview.setAdapter(adapter);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Config.ShowImageList != null) {
            Config.ShowImageList.clear();
        }
    }

    @Override
    public void backResult() {
        noScrollgridview.setVisibility(View.GONE);
        llPostingType.setVisibility(View.VISIBLE);

    }

    @Override
    public void subResult(List<String> listfile) {
        noScrollgridview.setOnItemClickListener(new ImageGridClick(PostingMain.this, compressfile, listfile, etPostingText.getText().toString()));
    }

    //上传成功回掉
    @Override
    public void uploadSuccess(String file_path) {
        upload_path = file_path;
        UIUtil.showLog("upload_path---->", upload_path + "");

        PostRequest();

    }

    @Override
    public void uploadVideoPic(String video_pic) {
        this.video_pic = video_pic;
        presenter.upload(uploadList,1);
    }

    //上传失败回掉
    @Override
    public void uploadFailure(String error_code) {


    }

    //执行发帖网络请求
    void PostRequest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.TEST_ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("utype", Config.USER_TYPE);
//        map.put("stus_type", "status");
        map.put("title", content);
        map.put("content", content);
//        map.put("upload_path", content);
        map.put("attr", upload_path + "");
        map.put("attr_type", attr_type + "");
        if(attr_type.equals("video"))map.put("thumbnail", video_pic);
        if(attr_type.equals("music"))map.put("duration",duration);


        UIUtil.showLog("attr_type---->", attr_type + "duration---->"+duration);
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                UIUtil.showLog("onResponse", "----------》" + response.code()+"----->"+response.message());
                if (response.body() != null) {
                    GeneralBean generalBean = response.body();
                    if (generalBean.getError_code().equals("0")) {
                        UIUtil.showLog("成功", "----------》" + generalBean.toString());
                        progressDialog.dismiss();
                        // TODO: 2016/11/21 暂时抛异常
                        try {
                            Config.ShowImageList.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();
                    } else {
                        error_code = generalBean.getError_code();
                        errorHandler.sendEmptyMessage(0);
                    }
                } else {
                    error_code = Config.Connection_Failure;
                    errorHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("onFailure", "----------》" + t.getMessage()+"----->"+t.getMessage());
                error_code = Config.Connection_Failure;
                errorHandler.sendEmptyMessage(0);
            }
        };

        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesPublish(map);
        call.enqueue(callback);
    }

    //处理错误信息
    Handler errorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            switch (error_code) {
                case Config.Connection_Failure:
                    UIUtil.ToastshowShort(PostingMain.this, getResources().getString(R.string.connection_timeout));
                    break;
            }
        }
    };
}
