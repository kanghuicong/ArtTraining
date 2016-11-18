package com.example.kk.arttraining.ui.me.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.WebViewShowActivity;
import com.example.kk.arttraining.bean.HelpBean;
import com.example.kk.arttraining.bean.parsebean.HelpListBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.adapter.HelpAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/17 15:57
 * 说明:帮助
 */
public class HelpActivity extends BaseActivity implements AdapterView.OnItemClickListener{
private ListView lv_help;
    private HelpAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_help);
        init();
    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this,"帮助");
        lv_help= (ListView) findViewById(R.id.lv_help);
        lv_help.setOnItemClickListener(this);
        getHelpList();
    }

    @Override
    public void onClick(View v) {

    }

    void getHelpList(){
        Map<String,Object> map=new HashMap<>();
        map.put("access_token", Config.ACCESS_TOKEN);

        Callback<HelpListBean> callback=new Callback<HelpListBean>() {
            @Override
            public void onResponse(Call<HelpListBean> call, Response<HelpListBean> response) {
                HelpListBean helpListBean=response.body();
                if(helpListBean!=null){
                    if(helpListBean.getError_code().equals("0")){
                        adapter=new HelpAdapter(HelpActivity.this,helpListBean.getHelps());
                        lv_help.setAdapter(adapter);
                    }else {
                        UIUtil.ToastshowShort(HelpActivity.this,helpListBean.getError_msg());
                    }
                }else {
                    UIUtil.ToastshowShort(HelpActivity.this,getResources().getString(R.string.connection_timeout));
                }
            }

            @Override
            public void onFailure(Call<HelpListBean> call, Throwable t) {
                UIUtil.ToastshowShort(HelpActivity.this,getResources().getString(R.string.connection_timeout));
            }
        };
        Call<HelpListBean> call= HttpRequest.getCommonApi().helpList(map);
        call.enqueue(callback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HelpBean helpBean= (HelpBean) parent.getItemAtPosition(position);
        Intent intent=new Intent(this, WebViewShowActivity.class);
        intent.putExtra("url","http://www.sj33.cn/digital/wysj/201403/37490.html");
        intent.putExtra("title","");
        startActivity(intent);
    }
}
