package com.example.kk.arttraining.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.dao.UserDao;
import com.example.kk.arttraining.dao.UserDaoImpl;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/26 10:14
 * 说明:
 */
public class ChoseSexActivity extends BaseActivity {

    @InjectView(R.id.ll_sex_man)
    LinearLayout ll_sex_man;
    @InjectView(R.id.ll_sex_woman)
    LinearLayout ll_sex_woman;
    @InjectView(R.id.image_sex_man)
    ImageView image_sex_man;
    @InjectView(R.id.image_sex_woman)
    ImageView image_sex_woman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_chosesex);
        init();
    }

    @Override
    public void init() {
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(ChoseSexActivity.this, "性别");
//        String sex = Config.userBean.getSex();
        String sex = "m";
        if (sex.equals("m")) {
            image_sex_man.setImageResource(R.mipmap.check_right);
        } else if (sex.equals("f")) {
            image_sex_woman.setImageResource(R.mipmap.check_right);
        }

    }

    @OnClick({R.id.ll_sex_man, R.id.ll_sex_woman})
    public void onClick(View v) {

        switch (v.getId()) {
            //点击男
            case R.id.ll_sex_man:
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("access_token", Config.ACCESS_TOKEN);
                map1.put("uid", Config.UID);
                map1.put("sex", "m");
                commit(map1);
                break;
            //点击女
            case R.id.ll_sex_woman:
                Map<String, String> map2 = new HashMap<String, String>();
                map2.put("access_token", Config.ACCESS_TOKEN);
                map2.put("uid", Config.UID);
                map2.put("sex", "f");
                commit(map2);
                break;
        }

    }

    private void commit(final Map<String, String> map) {
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                if (response.body() != null) {
                    UpdateBean updateBean = response.body();
                    if (updateBean.getError_code().equals("0")) {
                        UserDao userDao = new UserDaoImpl(getApplicationContext());
                        //更新到本地数据库
                        userDao.Update(Config.UID, "user_sex", map.get("sex"));
                        //更新配置config中的userBean信息
                        Config.userBean.setSex(map.get("sex"));
                        setResult(104);
                    } else {


                    }
                }

            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {

            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().setUserInfo(map);

    }


}
