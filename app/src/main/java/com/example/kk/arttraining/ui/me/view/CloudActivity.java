package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.pay.view.RechargeICloudActivity;
import com.example.kk.arttraining.ui.me.presenter.CloudData;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudActivity extends Activity implements CloudData.ICloud {

    @InjectView(R.id.tv_title_subtitle)
    TextView tvTitleSubtitle;
    @InjectView(R.id.tv_cloud_num)
    TextView tvCloudNum;
    @InjectView(R.id.bt_cloud_recharge)
    Button btCloudRecharge;
    @InjectView(R.id.bt_about_cloud)
    Button btAboutCloud;

    CloudData cloudData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_cloud);
        ButterKnife.inject(this);

        TitleBack.toTitleBackActivity(this, "我的云币", "云币明细");

        cloudData = new CloudData(this);
        cloudData.getCloud();

    }

    @OnClick({R.id.tv_title_subtitle, R.id.bt_cloud_recharge, R.id.bt_about_cloud})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_subtitle://明细
                Intent intent = new Intent(this, CloudContentActivity.class);

                startActivity(intent);
                break;
            case R.id.bt_cloud_recharge://充值
                startActivity(new Intent(this, RechargeICloudActivity.class));
                break;
            case R.id.bt_about_cloud:
                break;
        }
    }

    @Override
    public void getCloud(Double aDouble) {
        tvCloudNum.setText(StringUtils.getDouble(aDouble)+"");
    }
    @Override
    public void onFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cloudData.getCloud();
        UIUtil.showLog("onResume", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cloudData.cancelSubscription();
    }
}
