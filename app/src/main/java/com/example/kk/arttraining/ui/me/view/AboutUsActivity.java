package com.example.kk.arttraining.ui.me.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.IUpdateApp;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AppInfoBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.dialog.UpdateAppDialong;
import com.example.kk.arttraining.download.updateapp.UpdateAppUtils;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.UpdateAppPersenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/17 15:12
 * 说明:关于我们
 */
public class AboutUsActivity extends BaseActivity implements IUpdateApp{


    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.update_app)
    Button updateApp;
    @InjectView(R.id.tv_user_agreement)
    TextView tvUserAgreement;

    private UpdateAppUtils updateAppUtils;
    private UpdateAppDialong updateAppDialong;
    private UpdateAppPersenter updateAppPersenter;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_us);
        ButterKnife.inject(this);
        tvTitleBar.setText("关于我们");
        init();
    }

    @Override
    public void init() {
        updateAppUtils = new UpdateAppUtils(getApplicationContext());
        updateAppPersenter=new UpdateAppPersenter(this);
        loadingDialog=LoadingDialog.getInstance(this);
    }

    @OnClick({R.id.iv_title_back, R.id.update_app, R.id.tv_user_agreement})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.update_app:
                loadingDialog.show();
                getAppVersion();
                break;
            case R.id.tv_user_agreement:

                break;
        }
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
        updateAppPersenter.updateApp(map);
    }

    //检查更新成功
    @Override
    public void SuccessAppVersion(final AppInfoBean appInfoBean) {
        loadingDialog.dismiss();
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
        loadingDialog.dismiss();
        UIUtil.ToastshowShort(getApplicationContext(),error_msg);
        UIUtil.showLog("检查更新----》", error_code + "---->" + error_msg);
    }
}
