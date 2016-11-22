package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.presenter.UpdatePresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

    private String update_values;
    private String from;
    private UpdatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_update_name_school);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        from=intent.getStringExtra("fromType");
        switch (from) {
            case "name":
                etMeUpdateNameSchool.setText(Config.userBean.getName());
                break;
            case "school":
                etMeUpdateNameSchool.setText(Config.userBean.getSchool());
                break;
        }
        presenter = new UpdatePresenter(this);
    }
    @OnClick({R.id.title_back,R.id.title_tv_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_tv_ok:
                update_values = etMeUpdateNameSchool.getText().toString();
                updateInfo();
                break;
        }
    }

    //更新用户信息
    @Override
    public void updateInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        if (from.equals("name")) {
            map.put("name", update_values);
        } else {
            map.put("school", update_values);
        }
        presenter.updateUserInfo(map);
    }

    //更新用户信息成功
    @Override
    public void SuccessUpdate(UpdateBean updateBean) {

        UIUtil.ToastshowShort(this, "保存成功");
        UserDao userDao=new UserDaoImpl(getApplicationContext());
        switch (from) {
            case "name":
                Config.userBean.setName(update_values);
                Intent intent = new Intent();
                intent.putExtra("user_name",update_values);
                userDao.Update(Config.UID,update_values,"name");
                setResult(AboutActivity.UPDATE_NAME);
                finish();
                break;
            case "school":
                Config.userBean.setSchool(update_values);
                Intent intentSchool = new Intent();
                intentSchool.putExtra("school",update_values);
                userDao.Update(Config.UID,update_values,"school");
                setResult(AboutActivity.UPDATE_SCHOOL,intentSchool);
                finish();
                break;
        }
    }

    //更新失败
    @Override
    public void FailureUpdate(String error_code, String error_msg) {
        if (error_code.equals(Config.TOKEN_INVALID)) {
            UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
            startActivity(new Intent(this, UserLoginActivity.class));
        } else {
            UIUtil.ToastshowShort(this, error_msg);
        }
    }
}
