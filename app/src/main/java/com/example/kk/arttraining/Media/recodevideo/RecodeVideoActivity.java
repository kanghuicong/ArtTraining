package com.example.kk.arttraining.Media.recodevideo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.activity.PostingMain;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.AudioFileFunc;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.File;

/**
 * 作者：wschenyongyin on 2016/11/4 20:52
 * 说明:视频录制类
 */
public class RecodeVideoActivity
        extends Activity implements View.OnClickListener {
    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private ImageButton mShutter;
    private ImageButton ib_local_video;
    private TextView mMinutePrefix;
    private TextView mMinuteText;
    private TextView mSecondPrefix;
    private TextView mSecondText;
    private String outPutPath;
    private Camera mCamera;
    private MediaRecorder mRecorder;
    private TextView tv_cancel;
    private TextView tv_ok;
    private final static int CAMERA_ID = 0;

    private boolean mIsRecording = false;
    private boolean mIsSufaceCreated = false;

    private static final String TAG = "Jackie";
    private int CHOSE_LOCAL_VIDEO = 001;
    private Handler mHandler = new Handler();
    int REQUEST_CODE_ASK_CALL_PHONE = 101;
    private VideoView videoView;
    private AudioInfoBean audioInfoBean;
    //标记是哪个页面过来 设置码率
    private String from;
    private int bitRate = 512 * 1024;
    //设置录制最大时间
    private int MaxTime = 2 * 60;
    private int MinTime = 5;
    private int recodTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_recodervideo_activity);


        Intent intent = getIntent();
        from = intent.getStringExtra("fromIntent");
        switch (from) {
            //如果时发帖那么设置码率为 1024 * 1024  录制最长时间为2分钟
            case "postingMain":
                bitRate = 2*1024 * 1024;
                break;

            //如果时测评那么设置码率为 8 * 1024 * 1024  录制最长时间为5分钟
            case "production":
                bitRate = 8 * 1024 * 1024;
                MaxTime = 5;
                break;
        }


        mCameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        mMinutePrefix = (TextView) findViewById(R.id.timestamp_minute_prefix);
        mMinuteText = (TextView) findViewById(R.id.timestamp_minute_text);
        mSecondPrefix = (TextView) findViewById(R.id.timestamp_second_prefix);
        mSecondText = (TextView) findViewById(R.id.timestamp_second_text);
        tv_cancel = (TextView) findViewById(R.id.recode_video_cancel);
        tv_ok = (TextView) findViewById(R.id.recode_video_ok);

        mSurfaceHolder = mCameraPreview.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mShutter = (ImageButton) findViewById(R.id.record_shutter);
        ib_local_video = (ImageButton) findViewById(R.id.local_video);

        mShutter.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        ib_local_video.setOnClickListener(this);
        get();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mIsRecording) {
            stopRecording();
        }
        stopPreview();
    }

    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mIsSufaceCreated = false;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mIsSufaceCreated = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            startPreview();
        }
    };

    //启动预览
    private void startPreview() {
        //保证只有一个Camera对象
        if (mCamera != null || !mIsSufaceCreated) {
            Log.d(TAG, "startPreview will return");
            return;
        }

        mCamera = Camera.open(CAMERA_ID);

        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getBestPreviewSize(1920, 1080, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setPreviewFrameRate(20);


        //设置相机预览方向
        mCamera.setDisplayOrientation(90);

        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
//          mCamera.setPreviewCallback(mPreviewCallback);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        mCamera.startPreview();
    }

    private void stopPreview() {
        //释放Camera对象
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //点击录制
            case R.id.record_shutter:
                if (mIsRecording) {
                    if (recodTime < MinTime) {
                        UIUtil.ToastshowShort(this, "录制时间太短");
                    } else {
                        stopRecording();
                    }

                } else {
                    initMediaRecorder();
                    startRecording();

                    //开始录像后，每隔1s去更新录像的时间戳
                    mHandler.postDelayed(mTimestampRunnable, 1000);
                }
                break;
            case R.id.local_video:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("video/*");
                startActivityForResult(intent1, CHOSE_LOCAL_VIDEO);
                break;
            //点击取消
            case R.id.recode_video_cancel:
                finish();
                break;
            //点击完成
            case R.id.recode_video_ok:

                audioInfoBean = new AudioInfoBean();
                audioInfoBean.setAudio_path(outPutPath);
                long file_size = AudioFileFunc.getFileSize(outPutPath);
                audioInfoBean.setAudio_size(file_size);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("media_info", audioInfoBean);
                bundle.putString("type","video");
                intent.putExtras(bundle);
                if (from.equals("postingMain")) {
                    setResult(PostingMain.POST_MAIN_VIDEO_CODE, intent);
                } else if (from.equals("production")) {
                    setResult(ValuationMain.CHOSE_PRODUCTION, intent);
                }
                finish();
                break;

        }

    }

    private void initMediaRecorder() {
        mRecorder = new MediaRecorder();//实例化
        mCamera.unlock();
        //给Recorder设置Camera对象，保证录像跟预览的方向保持一致
        mRecorder.setCamera(mCamera);
        mRecorder.setOrientationHint(90);  //改变保存后的视频文件播放时是否横屏(不加这句，视频文件播放的时候角度是反的)
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置从麦克风采集声音
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置从摄像头采集图像
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // 设置视频的输出格式 为MP4
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // 设置音频的编码格式
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // 设置视频的编码格式
        mRecorder.setVideoSize(1920, 1080);  // 设置视频大小
        mRecorder.setVideoFrameRate(20); // 设置帧率
        mRecorder.setVideoEncodingBitRate(bitRate);
//        mRecorder.setMaxDuration(10000); //设置最大录像时间为10s
        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        //设置视频存储路径
        Log.i("did--->", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + "VideoRecorder");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + "VideoRecorder");
        if (!file.exists()) {
            //多级文件夹的创建
            file.mkdirs();
        }
        outPutPath = file.getPath() + File.separator + "VID_" + System.currentTimeMillis() + ".mp4";
        mRecorder.setOutputFile(outPutPath);
        UIUtil.showLog("RecoderVideoClass_outputpath", outPutPath + "");
    }

    private void startRecording() {
        if (mRecorder != null) {
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (Exception e) {
                mIsRecording = false;
                Log.e(TAG, e.getMessage());
            }
        }
        if (mCameraPreview.getVisibility() == View.GONE) {
            videoView.setVisibility(View.GONE);
            mCameraPreview.setVisibility(View.VISIBLE);
            videoView.stopPlayback();
        }


        mShutter.setImageResource(R.mipmap.recoder_video_stop);
        mIsRecording = true;
    }

    private void stopRecording() {
        if (mCamera != null) {
            mCamera.lock();
        }

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        mShutter.setImageResource(R.mipmap.recoder_video_start);
        mIsRecording = false;

        mHandler.removeCallbacks(mTimestampRunnable);

        //将录像时间还原
        mMinutePrefix.setVisibility(View.VISIBLE);
        mMinuteText.setText("0");
        mSecondPrefix.setVisibility(View.VISIBLE);
        mSecondText.setText("0");

        //重启预览
//        startPreview();
        playVideo();
    }

    private Runnable mTimestampRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimestamp();
            mHandler.postDelayed(this, 1000);
        }
    };
    private Runnable mTimePlayRunnable = new Runnable() {
        @Override
        public void run() {
            PlayVideoUpdateTimestamp();
            mHandler.postDelayed(this, 1000);
        }
    };

    //更新时间
    private void updateTimestamp() {
        int second = Integer.parseInt(mSecondText.getText().toString());
        int minute = Integer.parseInt(mMinuteText.getText().toString());
        //记录录制的时间
        recodTime++;

        second++;
        Log.d(TAG, "second: " + second);

        if (second < 10) {
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 10 && second < 60) {
            mSecondPrefix.setVisibility(View.GONE);
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 60) {
            mSecondPrefix.setVisibility(View.VISIBLE);
            mSecondText.setText("0");

            minute++;
            mMinuteText.setText(String.valueOf(minute));
        } else if (minute * 60 == MaxTime) {
            stopRecording();
        }
    }

    private void PlayVideoUpdateTimestamp() {
        int second = Integer.parseInt(mSecondText.getText().toString());
        int minute = Integer.parseInt(mMinuteText.getText().toString());
        //记录录制的时间


        second++;
        Log.d(TAG, "second: " + second);

        if (second < 10) {
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 10 && second < 60) {
            mSecondPrefix.setVisibility(View.GONE);
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 60) {
            mSecondPrefix.setVisibility(View.VISIBLE);
            mSecondText.setText("0");

            minute++;
            mMinuteText.setText(String.valueOf(minute));
        }
        if (recodTime == minute * 60 + second) {
            mHandler.removeCallbacks(mTimestampRunnable);
        }


    }

    void get() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i("获取劝你先拿 ", "------->");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, REQUEST_CODE_ASK_CALL_PHONE);
            } else {

            }
        } else {
            //上面已经写好的拨号方法

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("huoqu chengg ", "------->");
                } else {
                    // Permission Denied
                    Toast.makeText(RecodeVideoActivity.this, "CALL_PHONE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    void playVideo() {
        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        mHandler.postDelayed(mTimePlayRunnable, 1000);

        //网络视频

        Uri uri = Uri.parse(outPutPath);

        videoView = (VideoView) this.findViewById(R.id.play_video);
        videoView.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.GONE);
        mShutter.setVisibility(View.GONE);
        ib_local_video.setVisibility(View.GONE);
        tv_ok.setVisibility(View.VISIBLE);
        //设置视频控制器

        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(RecodeVideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CHOSE_LOCAL_VIDEO) {
            Uri uri = data.getData();
            UIUtil.showLog("uri", uri + "");
            outPutPath = FileUtil.getFilePath(RecodeVideoActivity.this, uri);
            UIUtil.showLog("file_path", outPutPath + "");
//            String file_size = FileUtil.getAutoFileOrFilesSize(outPutPath).trim();
            long file_size = AudioFileFunc.getFileSize(outPutPath);
            UIUtil.showLog("file_size", file_size + "");
            audioInfoBean = new AudioInfoBean();
            audioInfoBean.setAudio_path(outPutPath);
            audioInfoBean.setAudio_size(file_size);

            playVideo();


        }
    }
}
