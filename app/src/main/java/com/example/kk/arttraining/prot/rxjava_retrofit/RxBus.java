package com.example.kk.arttraining.prot.rxjava_retrofit;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 作者：wschenyongyin on 2017/2/23 09:10
 * 说明:rx事件总线通信类
 */
public class RxBus {
    private static RxBus rxBus;
    //把publishSubject包装成线程安全的Subject。
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {
    }

    //单例
    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }


    //发送
    public void send(Object o) {
        _bus.onNext(o);
    }

    //接收
    public Observable<Object> toObserverable() {
        return _bus;
    }

}
