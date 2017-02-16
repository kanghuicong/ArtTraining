package com.example.kk.arttraining.prot.rxjava_retrofit;

import rx.Subscription;

/**
 * 作者：wschenyongyin on 2017/2/10 10:38
 * 说明:
 */
public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);
    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
