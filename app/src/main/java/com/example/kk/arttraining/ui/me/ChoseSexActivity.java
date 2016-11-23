package com.example.kk.arttraining.ui.me;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.sqlite.dao.UserDao;
import com.example.kk.arttraining.sqlite.dao.UserDaoImpl;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

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
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_chosesex);
        init();
    }

    @Override
    public void init() {
        dialog= DialogUtils.createLoadingDialog(this,"");
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(ChoseSexActivity.this, "性别");
        if (Config.userBean.getSex()!=null && !Config.userBean.getSex().equals("")) {
            String sex = Config.userBean.getSex();
            if (sex.equals("m")) {
                image_sex_man.setImageResource(R.mipmap.check_right);
            } else if (sex.equals("f")) {
                image_sex_woman.setImageResource(R.mipmap.check_right);
            }
        }

    }

    @OnClick({R.id.ll_sex_man, R.id.ll_sex_woman})
    public void onClick(View v) {

        switch (v.getId()) {
            //点击男
            case R.id.ll_sex_man:
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("access_token", Config.ACCESS_TOKEN);
                map1.put("uid", Config.UID);
                map1.put("sex", "m");
                commit(map1);
                break;
            //点击女
            case R.id.ll_sex_woman:
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("access_token", Config.ACCESS_TOKEN);
                map2.put("uid", Config.UID);
                map2.put("sex", "f");
                commit(map2);
                break;
        }

    }

    private void commit(final Map<String, Object> map) {
        dialog.show();
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                if (response.body() != null) {
                    UpdateBean updateBean = response.body();
                    if (updateBean.getError_code().equals("0")) {
                        UserDao userDao = new UserDaoImpl(getApplicationContext());
                        //更新到本地数据库
                        userDao.Update(Config.UID, map.get("sex").toString(),"user_sex");
                        //更新配置config中的userBean信息
//                        Config.userBean.setSex(map.get("sex").toString());
                        Intent intent=new Intent();
                        intent.putExtra("sex",map.get("sex").toString());
                        setResult(104,intent);
                        UIUtil.ToastshowShort(ChoseSexActivity.this,"更改成功");
                        finish();
                        dialog.dismiss();
                    } else {
                        UIUtil.ToastshowShort(ChoseSexActivity.this,updateBean.getError_msg());
                        dialog.dismiss();

                    }
                }else {
                    UIUtil.ToastshowShort(ChoseSexActivity.this,"网络连接失败");
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                UIUtil.ToastshowShort(ChoseSexActivity.this,"网络连接失败");
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().setUserInfo(map);
        call.enqueue(callback);

    }


}
