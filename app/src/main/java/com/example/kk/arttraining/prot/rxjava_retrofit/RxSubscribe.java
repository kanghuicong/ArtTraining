package com.example.kk.arttraining.prot.rxjava_retrofit;

import android.net.ParseException;

import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.utils.NetUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.util.List;

import rx.Subscriber;

/**
 * 作者：wschenyongyin on 2017/2/8 09:24
 * 说明:封装Subscriber
 */
public abstract class RxSubscribe<T> extends Subscriber<T> {
    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override

    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            _onError("101", "网络不可用");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            _onError("101", "解析错误");
        } else if (e instanceof ConnectException) {
            _onError("101", "连接失败");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            _onError("101", "证书验证失败");
        } else if (e instanceof ServerException) {
            _onError(((ServerException) e).getCode(), e.getMessage());
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String error_code, String error_msg);


}
