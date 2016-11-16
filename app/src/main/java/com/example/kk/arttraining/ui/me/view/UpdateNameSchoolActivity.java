package com.example.kk.arttraining.ui.me.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.UpdatePresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/11/16 19:58
 * 说明:更新用户姓名，学校
 */
public class UpdateNameSchoolActivity extends BaseActivity implements IUpdateUserInfo {

    @InjectView(R.id.title_back)
    ImageView titleBack;
    @InjectView(R.id.title_barr)
    TextView titleBarr;
    @InjectView(R.id.title_tv_ok)
    TextView titleTvOk;
    @InjectView(R.id.et_me_update_name_school)
    EditText etMeUpdateNameSchool;


    private UpdatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_update_name_school);
        ButterKnife.inject(this);
    }

    @Override
    public void init() {
        presenter=new UpdatePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateInfo() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("access_token","");



    }

    @Override
    public void SuccessUpdate(UpdateBean updateBean) {

    }

    @Override
    public void FailureUpdate(String error_code, String error_msg) {

    }
}
