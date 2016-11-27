package com.example.kk.arttraining.pay.wxapi;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.example.kk.arttraining.pay.bean.WeChatBean;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.NetUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by love on 2015/10/9.
 */
public class WXPayUtils {
    //    private Activity context;
    PayReq req;
    IWXAPI msgApi;
    StringBuffer sb;
    Map<String, String> resultunifiedorder;
    private String notify_url = "";
    private String orderId = "";
    private String orderTitle = "";
    private String orderPrice = "";
    WeChatBean weChatBean;
    Context context;

    public WXPayUtils(Activity context, String notify_url) {
        this.context = context;
        this.notify_url = notify_url;
        req = new PayReq();
        sb = new StringBuffer();

    }

    public void registerAPP() {
         msgApi = WXAPIFactory.createWXAPI(context, weChatBean.getAppid(), true);
        msgApi.registerApp(weChatBean.getAppid());
    }

    public void pay(CommitOrderBean commitOrderBean, WeChatBean weChatBean, Context context) {
        this.orderTitle = commitOrderBean.getOrder_title();
        this.orderPrice = commitOrderBean.getOrder_price();
        this.orderId = commitOrderBean.getOrder_number();
        this.weChatBean = weChatBean;
        this.context = context;
        Constants.APP_ID=weChatBean.getAppid();
        registerAPP();
        genPayReq();
//        UIUtil.showLog("pay----->", weChatBean.toString());
//        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//        getPrepayId.execute();
    }

//    public void pay(String orderTitle, String orderPrice, String orderId) {
//        this.orderTitle = orderTitle;
//        this.orderPrice = orderPrice;
//        this.orderId = orderId;
//        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//        getPrepayId.execute();
//    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private void genPayReq() {


        req.appId = weChatBean.getAppid();
        req.partnerId = weChatBean.getPartnerid();
        req.prepayId = weChatBean.getPrepayid();
        req.packageValue = weChatBean.getPay_package();
        req.nonceStr = weChatBean.getNoncestr();
        req.timeStamp = weChatBean.getTimestamp();
        req.sign = weChatBean.getSign();
        msgApi.sendReq(req);
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//
//
//        UIUtil.showLog("req.sign", weChatBean.getSign() + "--------->");
//
//        sb.append("sign\n" + req.sign + "\n\n");
//        msgApi.sendReq(req);
//        String returnCode = resultunifiedorder.get("return_code");
//        UIUtil.showLog("resultunifiedorder--->",resultunifiedorder.toString()+"");
//        if ("FAIL".equals(returnCode)) {
//            Toast.makeText(context, resultunifiedorder.get("return_msg"), Toast.LENGTH_SHORT).show();
//            UIUtil.showLog("returnCode------>", resultunifiedorder.get("return_msg"));
//        }
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(weChatBean.getAppid());

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    private void sendPayReq() {

        msgApi.registerApp(weChatBean.getAppid());
        msgApi.sendReq(req);
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(weChatBean.getAppid());


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }


//    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
//
//        private ProgressDialog dialog;
//
//
//        @Override
//        protected void onPreExecute() {
//            dialog = ProgressDialog.show(context, "提示", "正在获取预支付订单...");
//        }
//
//        @Override
//        protected void onPostExecute(Map<String, String> result) {
//
//            UIUtil.showLog("onPostExecute-->", "------>" + "onPostExecute");
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//            if (result.get("err_code") != null && "OUT_TRADE_NO_USED".equals(result.get("err_code").toString())) {
//                UIUtil.ToastshowShort(context, result.get("err_code_des").toString());
//            }
//
//            UIUtil.showLog("sb------>",sb.toString()+"");
//            resultunifiedorder = result;
//            Log.e(getClass().getName(), resultunifiedorder.toString());
//            genPayReq();
////            sendPayReq();
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Map<String, String> doInBackground(Void... params) {
//
//            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            String entity = genProductArgs();
//
//            UIUtil.showLog("entity-------->", entity + "---->");
//            byte[] buf = Util.httpPost(url, entity);
//
//            String content = new String(buf);
//            Map<String, String> xml = decodeXml(content);
//
//            return xml;
//        }
//    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
        }
        return null;

    }

//    private String genProductArgs() {
//        StringBuffer xml = new StringBuffer();
//
//        try {
//            String nonceStr = genNonceStr();
//
//
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//            UIUtil.showLog(" NetUtils.getLocalIpAddress()-->", NetUtils.getLocalIpAddress(context) + "");
//            UIUtil.showLog(" req.sign-->",  weChatBean.getSign() + "");
//            packageParams.add(new BasicNameValuePair("appid", weChatBean.getAppid()));
//            packageParams.add(new BasicNameValuePair("body", orderTitle));
//            packageParams.add(new BasicNameValuePair("mch_id", weChatBean.getPartnerid()));
//            packageParams.add(new BasicNameValuePair("nonce_str", weChatBean.getNoncestr()));
//            packageParams.add(new BasicNameValuePair("notify_url", Config.BASE_URL + Config.URL_COMMENTS_CREATE_WORK));
//            packageParams.add(new BasicNameValuePair("out_trade_no", orderId));
//            packageParams.add(new BasicNameValuePair("spbill_create_ip", NetUtils.getLocalIpAddress(context)));
//            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf((int) (Float.parseFloat(orderPrice) * 100))));
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//            UIUtil.showLog("packageParams--->",packageParams.toString()+"");
//            UIUtil.showLog("appid:", weChatBean.getAppid()+"body:"+orderTitle+"mch_id:"+weChatBean.getPartnerid()+"nonce_str:"+weChatBean.getNoncestr()+"out_trade_no:"+orderId+"spbill_create_ip:"+NetUtils.getLocalIpAddress(context)+"total_fee:"+String.valueOf((int) (Float.parseFloat(orderPrice) * 100))+"trade_type:APP");
//            String sign = genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign", weChatBean.getSign()));
//
//
//            String xmlstring = toXml(packageParams);
//
//            return xmlstring;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    private String toXml(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        //return sb.toString();
        return new String(sb.toString().getBytes(), "ISO8859-1");
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


}
