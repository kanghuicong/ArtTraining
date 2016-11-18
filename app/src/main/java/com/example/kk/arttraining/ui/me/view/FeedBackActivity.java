package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.FeedBackPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/17 11:23
 * 说明:反馈
 */
public class FeedBackActivity extends BaseActivity implements IFeedBack {

    @InjectView(R.id.tv_aboutus_feedback_content)
    EditText btAboutusFeedbackContent;
    @InjectView(R.id.tv_aboutus_feedback_phone)
    EditText tvAboutusFeedbackPhone;
    @InjectView(R.id.bt_aboutus_feedback_refer)
    Button btAboutusFeedbackRefer;

    private FeedBackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_feedback);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "反馈");
        presenter = new FeedBackPresenter(this);
    }

    @OnClick({R.id.bt_aboutus_feedback_refer})
    public void onClick(View v) {
        commitFeedBack();

    }

    @Override
    public void commitFeedBack() {
        String user_phone = tvAboutusFeedbackPhone.getText().toString();
        String content = btAboutusFeedbackContent.getText().toString();
        presenter.commitFeedBack(user_phone, content);
    }

    @Override
    public void Success() {
        startActivity(new Intent(this, FeedBackSuccessShow.class));
        finish();
    }

    @Override
    public void OnFailure(String error_code, String error_msg) {
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
            startActivity(new Intent(this, UserLoginActivity.class));
        } else {
            UIUtil.ToastshowShort(this, error_msg);
        }
    }
}
