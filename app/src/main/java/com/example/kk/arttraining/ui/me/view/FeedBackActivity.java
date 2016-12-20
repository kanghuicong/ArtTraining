package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class FeedBackActivity extends BaseActivity implements IFeedBack, TextWatcher {

    @InjectView(R.id.tv_aboutus_feedback_content)
    EditText btAboutusFeedbackContent;
    @InjectView(R.id.tv_aboutus_feedback_phone)
    EditText tvAboutusFeedbackPhone;
    @InjectView(R.id.bt_aboutus_feedback_refer)
    Button btAboutusFeedbackRefer;
    @InjectView(R.id.tv_feedback_count)
    TextView tvFeedbackCount;

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
        btAboutusFeedbackRefer.setEnabled(false);
        btAboutusFeedbackContent.addTextChangedListener(this);
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
    /**
     * 监听输入描述内容的字数的变化
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    private CharSequence wordNum;//记录输入的字数
    private int selectionStart;
    private int selectionEnd;
    //设置输入最大字数限制
    private int num = 240;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        wordNum = s;//实时记录输入的字数
    }

    @Override
    public void afterTextChanged(Editable s) {
        tvFeedbackCount.setText(s.length() + "/240");
        selectionStart = btAboutusFeedbackContent.getSelectionStart();
        selectionEnd = btAboutusFeedbackContent.getSelectionEnd();

        if(wordNum.length()>4){
            btAboutusFeedbackRefer.setEnabled(true);
            btAboutusFeedbackRefer.setBackgroundColor(getResources().getColor(R.color.blue_overlay));
        }else if (wordNum.length() > num) {
            //删除多余输入的字（不会显示出来）
            btAboutusFeedbackRefer.setBackgroundColor(getResources().getColor(R.color.blue_overlay));
            btAboutusFeedbackRefer.setEnabled(true);
            s.delete(selectionStart - 1, selectionEnd);
            int tempSelection = selectionEnd;
            btAboutusFeedbackContent.setText(s);
            btAboutusFeedbackContent.setSelection(tempSelection);//设置光标在最后
        }else {
            btAboutusFeedbackRefer.setBackgroundColor(getResources().getColor(R.color.grey));
            btAboutusFeedbackRefer.setEnabled(false);
        }
    }
}
