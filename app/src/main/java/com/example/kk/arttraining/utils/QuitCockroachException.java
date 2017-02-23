package com.example.kk.arttraining.utils;

/**
 * 作者：wschenyongyin on 2017/2/13 10:02
 * 说明:捕获crash
 */

final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}
