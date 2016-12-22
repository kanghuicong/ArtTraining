package com.example.kk.arttraining;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kk.arttraining.bean.AppInfoBean;
import com.example.kk.arttraining.custom.dialog.UpdateAppDialong;
import com.example.kk.arttraining.download.updateapp.UpdateAppUtils;
import com.example.kk.arttraining.sqlite.bean.UploadBean;
import com.example.kk.arttraining.sqlite.dao.UploadDao;
import com.example.kk.arttraining.ui.course.view.CourseMain;
import com.example.kk.arttraining.ui.discover.view.DiscoverMain;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.MainRadioButton;
import com.example.kk.arttraining.ui.me.presenter.UploadPresenter;
import com.example.kk.arttraining.ui.me.view.IUploadFragment;
import com.example.kk.arttraining.ui.me.view.MeMainActivity;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
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
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by kanghuicong on 2016/9/19.
 * QQ邮箱:515849594@qq.com
 */

public class MainActivity extends FragmentActivity implements OnClickListener, ISignleUpload, IUploadFragment, IUpdateApp {
    public static RadioGroup rgMain;
    private static boolean isExit = false;// 定义一个变量，来标识是否退出
    private RadioButton rb_homepage, rb_discover, rb_valuation, rb_course, rb_me;
    private FragmentManager fm;
    PopupWindow window;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private HomePageMain homepageFragment;
    private CourseMain courseFragment;
    private DiscoverMain discoverFragment;
    private MeMainActivity meFragment;
    private ConnectivityManager connectivityManager;
    private TextView tv_valuation_music;
    private TextView tv_valuation_dance;
    private TextView tv_valuation_perform;
    private TextView tv_valuation_director;
    private TextView tv_valuation_musical;
    private TextView tv_valuation_pint;
    private ImageView iv_valuation_colse;
    private String thumbnail_pic;
    private UploadBean uploadBean;
    private String jsonString;
    private String order_id;
    SignleUploadPresenter signleUploadPresenter;
    private UploadPresenter presenter;
    private UpdateAppUtils updateAppUtils;
    private UpdateAppPersenter mainActivityPersenter;
    private UpdateAppDialong updateAppDialong;
    //推送类型
    private static String Jpush_Content_Type;
    private static Context mContext;

    private Intent intent;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.blue_overlay));
        initView();
    }

    private void initView() {
        intent = getIntent();
        from = intent.getStringExtra("from");
        updateAppUtils = new UpdateAppUtils(getApplicationContext());
        mainActivityPersenter = new UpdateAppPersenter(this);
        mContext = MainActivity.this;
        //检查更新
        getAppVersion();

        initFragment();
        rgMain = (RadioGroup) findViewById(R.id.radioGroup);
        rgMain.check(R.id.rb_homepage);
        // 进入主页面，初始页面pager为乐跑
        rb_homepage = (RadioButton) findViewById(R.id.rb_homepage);
        rb_me = (RadioButton) findViewById(R.id.rb_me);
        rb_course = (RadioButton) findViewById(R.id.rb_course);

        rb_discover = (RadioButton) findViewById(R.id.rb_discover);
        rb_valuation = (RadioButton) findViewById(R.id.rb_valuation);

        rb_homepage.setOnClickListener(this);
        rb_course.setOnClickListener(this);
        rb_discover.setOnClickListener(this);
        rb_valuation.setOnClickListener(this);
        rb_me.setOnClickListener(this);
        signleUploadPresenter = new SignleUploadPresenter(this);
        presenter = new UploadPresenter(this);
        getTextColor();
        getImage();
    }

    private void getTextColor() {
        MainRadioButton.initColor(this, rb_homepage);
        MainRadioButton.initColor(this, rb_course);
        MainRadioButton.initColor(this, rb_discover);
        MainRadioButton.initColor(this, rb_me);
    }

    private void getImage() {
        MainRadioButton.initImage(this, rb_homepage, R.mipmap.rb_homepage_checked, R.mipmap.rb_homepage_normal);
        MainRadioButton.initImage(this, rb_course, R.mipmap.icon_main_corse_focus, R.mipmap.icon_main_corse_unfocus);
        MainRadioButton.initImage(this, rb_discover, R.mipmap.icon_main_discover_focus, R.mipmap.icon_main_discover_unfocus);
        MainRadioButton.initImage(this, rb_me, R.mipmap.rb_me_checked, R.mipmap.rb_me_normal);
    }


    private void initFragment() {
        //一开始先初始到homeFragment
        fm = getSupportFragmentManager();
        homepageFragment = new HomePageMain();
        fm.beginTransaction().replace(R.id.flMain, homepageFragment).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.rb_homepage:
                if (homepageFragment == null) {
                    homepageFragment = new HomePageMain();
                    transaction.add(R.id.flMain, homepageFragment);
                } else {
                    transaction.show(homepageFragment);
                }
                getTextColor();
                getImage();
                transaction.commit();
                break;
            case R.id.rb_course:
                if (courseFragment == null) {
                    courseFragment = new CourseMain();
                    transaction.add(R.id.flMain, courseFragment);
                } else {
                    transaction.show(courseFragment);
                }
                getTextColor();
                getImage();
                transaction.commit();
                break;
            case R.id.rb_valuation:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    showPopwindow();
                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }


                break;
            case R.id.rb_discover:
                if (discoverFragment == null) {
                    discoverFragment = new DiscoverMain();
                    transaction.add(R.id.flMain, discoverFragment);
                } else {
                    transaction.show(discoverFragment);
                }
                getTextColor();
                getImage();
                transaction.commit();
                break;
            case R.id.rb_me:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    if (meFragment == null) {
                        meFragment = new MeMainActivity();
                        transaction.add(R.id.flMain, meFragment);
                    } else {
                        transaction.show(meFragment);
                    }
                    getTextColor();
                    getImage();
                    transaction.commit();
                } else {
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);

                }


                break;

            case R.id.popwindow_valuation_music:
                window.dismiss();
                Intent musicIntent = new Intent(MainActivity.this, ValuationMain.class);
                musicIntent.putExtra("type", "声乐");
                startActivity(musicIntent);
                break;

            case R.id.popwindow_valuation_dance:
                window.dismiss();
                Intent danceIntent = new Intent(MainActivity.this, ValuationMain.class);
                danceIntent.putExtra("type", "舞蹈");
                startActivity(danceIntent);
                break;
            case R.id.popwindow_valuation_perform:
                window.dismiss();
                Intent performIntent = new Intent(MainActivity.this, ValuationMain.class);
                performIntent.putExtra("type", "表演");
                startActivity(performIntent);
                break;

            case R.id.popwindow_valuation_director:
                window.dismiss();
                Intent directorIntent = new Intent(MainActivity.this, ValuationMain.class);
                directorIntent.putExtra("type", "编导");
                startActivity(directorIntent);
                break;
            case R.id.popwindow_valuation_musical:
                window.dismiss();
                Intent musicalIntent = new Intent(MainActivity.this, ValuationMain.class);
                musicalIntent.putExtra("type", "器乐");
                startActivity(musicalIntent);
                break;
            case R.id.popwindow_valuation_pint:
                window.dismiss();
                Intent pintIntent = new Intent(MainActivity.this, ValuationMain.class);
                pintIntent.putExtra("type", "书画");
                startActivity(pintIntent);
                break;


            case R.id.popwindow_valuation_colse:
                window.dismiss();
                break;
            default:
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (homepageFragment != null) {
            transaction.hide(homepageFragment);
        }
        if (courseFragment != null) {
            transaction.hide(courseFragment);
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }

    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.popwindow_valuation, null);
        View view = View.inflate(MainActivity.this, R.layout.popwindow_valuation, null);
        tv_valuation_music = (TextView) view.findViewById(R.id.popwindow_valuation_music);
        tv_valuation_dance = (TextView) view.findViewById(R.id.popwindow_valuation_dance);
        tv_valuation_perform = (TextView) view.findViewById(R.id.popwindow_valuation_perform);
        tv_valuation_director = (TextView) view.findViewById(R.id.popwindow_valuation_director);
        tv_valuation_musical = (TextView) view.findViewById(R.id.popwindow_valuation_musical);
        tv_valuation_pint = (TextView) view.findViewById(R.id.popwindow_valuation_pint);

        iv_valuation_colse = (ImageView) view.findViewById(R.id.popwindow_valuation_colse);

        tv_valuation_music.setOnClickListener(this);
        tv_valuation_dance.setOnClickListener(this);
        tv_valuation_perform.setOnClickListener(this);
        tv_valuation_musical.setOnClickListener(this);
        tv_valuation_director.setOnClickListener(this);
        tv_valuation_pint.setOnClickListener(this);

        iv_valuation_colse.setOnClickListener(this);

        // 得到宽度和高度 getWindow().getDecorView().getWidth()

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(
                MainActivity.this.findViewById(R.id.ll_main),
                Gravity.BOTTOM, 0, 0);
        // 这里检验popWindow里的button是否可以点击
        // popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });
    }

    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(UploadQiNiuService.ACTION_UPDATE);
        registerReceiver(myReceiver, filter);
        if (Config.EXIT_FLAG != null && Config.EXIT_FLAG.equals("exit")) {
            FragmentTransaction transaction = fm.beginTransaction();
            hideAllFragment(transaction);
            if (homepageFragment == null) {
            homepageFragment = new HomePageMain();
            transaction.add(R.id.flMain, homepageFragment);
            } else {
                transaction.show(homepageFragment);
            }
            rb_homepage.setChecked(true);
            rb_me.setChecked(false);
            getTextColor();
            getImage();
            transaction.commit();
            Config.EXIT_FLAG = null;
        }

    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {


            int progress = intent.getIntExtra("progress", 0);
            order_id = intent.getStringExtra("order_id");
            String att_path = intent.getStringExtra("upload_path");
            String upKey = intent.getStringExtra("upKey");
            List<AttBean> attBeanList = new ArrayList<AttBean>();
            AttBean attBean = new AttBean();
            attBean.setStore_path(upKey);
            attBeanList.add(attBean);
            Gson gson = new Gson();
            jsonString = gson.toJson(attBeanList);
            //如果下载完成，更改本地数据库状态
            if (progress == 100) {
                UploadDao uploadDao = new UploadDao(context);
                uploadBean = uploadDao.queryOrder(order_id);
                UIUtil.showLog("UploadingFragment->UploadBean", uploadBean.toString() + "true");
                //将附件上传状态改为成功

                UIUtil.showLog("UploadingFragment->att_path", jsonString + "true");
                if (uploadBean.getAtt_type().equals("video")) {
                    Bitmap bitmap = MediaUtils.getVideoThumbnail(att_path);
                    String video_pic_name = RandomUtils.getRandomInt() + "";
                    UIUtil.showLog("UploadingFragment->video_pic_name", video_pic_name + "true");
                    try {
                        thumbnail_pic = FileUtil.saveFile(bitmap, video_pic_name).toString();
                        signleUploadPresenter.uploadVideoPic(thumbnail_pic, 6);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    uploadVideoPic("");
                }
                uploadDao.update("type", "1", order_id);
            }
        }

    };

    @Override
    public void uploadSuccess(String file_path) {

    }

    @Override
    public void uploadVideoPic(String video_pic) {
        Map<String, Object> map = new HashMap<String, Object>();
        String att_type = uploadBean.getAtt_type();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("order_number", order_id);
        map.put("pay_type", uploadBean.getPay_type());
        map.put("attr_type", att_type);
        map.put("attachment", jsonString);
        map.put("is_pay", "1");
        map.put("attr_long", uploadBean.getAtt_length());
        if (att_type.equals("video")) map.put("thumbnail", video_pic);
        UIUtil.showLog("请求地址:----------->", Config.BASE_URL + Config.URL_ORDERS_UPDATE);
        UIUtil.showLog("uploadBean---->", uploadBean.toString());
        presenter.updateOrder(map);
    }

    @Override
    public void uploadFailure(String error_code, String error_msg) {

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

    }

    @Override
    public void UpdateOrderFailure(String error_code, String error_msg) {

    }


    /**
     * 检查更新app
     */
    @Override
    public void getAppVersion() {
        Map<String, Object> map = new HashMap<String, Object>();
        int version_code = updateAppUtils.getVersionCode();
        String version_name = updateAppUtils.getVersionName();
        map.put("version_no", version_code);
        map.put("version_name", version_name);
        mainActivityPersenter.updateApp(map);
    }

    //检查更新成功
    @Override
    public void SuccessAppVersion(final AppInfoBean appInfoBean) {
        updateAppDialong = new UpdateAppDialong(this, R.layout.dialog_update_app, R.style.transparentDialog, appInfoBean.getVersion_name(), appInfoBean.getDescrible(), new UpdateAppDialong.UpdateAppListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    //立即更新
                    case R.id.update_now:
                        updateAppUtils.download(appInfoBean.getVersion_url());
                        UIUtil.ToastshowShort(getApplicationContext(), "任务正在后台下载中！");
                        updateAppDialong.dismiss();
                        break;
                    //下次更新
                    case R.id.update_monent:
                        updateAppDialong.dismiss();
                        break;
                }

            }
        });
        Window window = updateAppDialong.getWindow();
        updateAppDialong.show();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void FailureAppVersion(String error_code, String error_msg) {
        UIUtil.showLog("检查更新----》", error_code + "---->" + error_msg);
    }


    //接收推送消息
    //接收后台推送消息
    public static class JpushMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Jpush_Content_Type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
            UIUtil.showLog("Mainactivity推送消息类型---》", Jpush_Content_Type + "");
            switch (Jpush_Content_Type) {
                case "ass_no":
                    String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                    UIUtil.showLog("推送消息内容---》", message + "");
                    break;
                case "ass_yes":
                    break;
                case "master_msg":
                    break;
                case "money_msg":
                    break;
                case "info_msg":
                    break;
                case "course_msg":
                    break;
            }

        }

    }


}