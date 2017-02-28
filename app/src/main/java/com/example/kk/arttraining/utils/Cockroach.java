package com.example.kk.arttraining.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.prot.rxjava_retrofit.RxApiManager;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：wschenyongyin on 2017/2/13 10:02
 * 说明:捕获crash
 */

public final class Cockroach {

    public interface ExceptionHandler {

        void handlerException(Thread thread, Throwable throwable);
    }

    private Cockroach() {
    }

    private static ExceptionHandler sExceptionHandler;
    private static Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;
    private static boolean sInstalled = false;//标记位，避免重复安装卸载

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler
     */
    public static synchronized void install(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
//                        Binder.clearCallingIdentity();
                        if (e instanceof QuitCockroachException) {
                            return;
                        }
                        if (sExceptionHandler != null) {
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.handlerException(t, e);
                }
            }
        });

    }

    public static synchronized void uninstall() {
        if (!sInstalled) {
            return;
        }
        sInstalled = false;
        sExceptionHandler = null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new QuitCockroachException("Quit Cockroach.....");//主线程抛出异常，迫使 while (true) {}结束
            }
        });

    }


    public static  void sendExceptionInfo(String throwableInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("app_type", "stu");
        map.put("version_no", DeviceUtils.getVersionCode(UIUtil.getContext()));
        map.put("version_name", DeviceUtils.getVersionName(UIUtil.getContext()));
        map.put("device_name", DeviceUtils.getPhoneBrand() +":"+ DeviceUtils.getDeviceName());
        map.put("device_os", "android:" + DeviceUtils.getDeviceSDK());
        map.put("exception", throwableInfo);
        Subscription crashSub = HttpRequest.getCommonApi().sendExceptionInfo(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<NoDataResponseBean>() {
                    @Override
                    public void call(NoDataResponseBean bean) {
                        if (bean != null) {
                            Log.e("sendCrash--->", bean.toString());
                        }
                    }
                });
        RxApiManager.get().add("crash", crashSub);

    }
}
