package com.example.kk.arttraining.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kk.arttraining.pay.PaySuccessActivity;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String orderId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        UIUtil.showLog("baseResp------->", baseResp.errCode + "" + baseResp.errStr);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "成功" + baseResp.errCode,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PaySuccessActivity.class);
                intent.putExtra("file_path", Config.order_att_path);
                intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
                intent.putExtra("order_id", Config.order_num);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "失败" + baseResp.errCode,
                        Toast.LENGTH_SHORT).show();


            }
            finish();
        }

    }


}