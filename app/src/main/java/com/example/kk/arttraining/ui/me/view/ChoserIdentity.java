package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.adapter.IdentityAdapter;
import com.example.kk.arttraining.ui.me.bean.IdentityBean;
import com.example.kk.arttraining.ui.me.bean.ParseIdentityBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/12 09:33
 * 说明:选择身份
 */
public class ChoserIdentity extends Activity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.lv_identity)
    ListView lv_identity;
    private IdentityAdapter identityAdapter;
    private String identity_name;
    private int identity_id;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_about_identity_activity);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        TitleBack.TitleBackActivity(this,"选择身份");
        Map<String, Object> map = new HashMap<String, Object>();
        dialog = DialogUtils.createLoadingDialog(this, "");
        getIdentityList(map);
        lv_identity.setOnItemClickListener(this);


    }

    //获取身份列表
    void getIdentityList(Map<String, Object> map) {
        dialog.show();
        Callback<ParseIdentityBean> callback = new Callback<ParseIdentityBean>() {
            @Override
            public void onResponse(Call<ParseIdentityBean> call, Response<ParseIdentityBean> response) {
                ParseIdentityBean parseIdentityBean = response.body();
                if (parseIdentityBean != null) {
                    if (parseIdentityBean.getError_code().equals("0")) {
                        SuccessIdentityList(parseIdentityBean.getIdentitys());
                    } else {
                        UIUtil.ToastshowShort(ChoserIdentity.this, parseIdentityBean.getError_msg());
                        dialog.dismiss();
                    }

                } else {
                    UIUtil.ToastshowShort(ChoserIdentity.this, getResources().getString(R.string.connection_timeout));
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ParseIdentityBean> call, Throwable t) {
                UIUtil.ToastshowShort(ChoserIdentity.this, getResources().getString(R.string.connection_timeout));
                dialog.dismiss();
            }
        };
        Call<ParseIdentityBean> call = HttpRequest.getUserApi().getIdentityList(map);
        call.enqueue(callback);

    }

    //获取列表成功
    void SuccessIdentityList(List<IdentityBean> identityBeenList) {
        dialog.dismiss();
        identityAdapter = new IdentityAdapter(ChoserIdentity.this, identityBeenList);
        lv_identity.setAdapter(identityAdapter);
    }


    //保存修改
    void saveRequest(Map<String, Object> map) {
        dialog.show();
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                UpdateBean updateBean = response.body();
                if (updateBean != null) {
                    if (updateBean.getError_code().equals("0")) {
                        saveSuccess();

                    } else {
                        UIUtil.ToastshowShort(ChoserIdentity.this, updateBean.getError_msg());

                    }
                } else {
                    UIUtil.ToastshowShort(ChoserIdentity.this, getResources().getString(R.string.connection_timeout));
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                UIUtil.ToastshowShort(ChoserIdentity.this, getResources().getString(R.string.connection_timeout));
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().setUserInfo(map);
        call.enqueue(callback);
    }

    //保存成功
    void saveSuccess() {
        dialog.dismiss();
        UIUtil.ToastshowShort(this, "修改成功");
        Intent intent = new Intent();
        intent.putExtra("identity_name", identity_name);
        setResult(AboutActivity.CHOSE_IDENTITY_CODE, intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IdentityBean identityBean = (IdentityBean) parent.getItemAtPosition(position);
        identity_id = identityBean.getIdentity_id();
        identity_name = identityBean.getIdentity_name();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("identity_id", identity_id);
        map.put("identity", identity_name);
        saveRequest(map);
    }
}
