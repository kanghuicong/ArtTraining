package com.example.kk.arttraining.prot.rxjava_retrofit;

/**
 * 作者：wschenyongyin on 2017/2/8 10:14
 * 说明:网络连接
 */
public class ServerException extends Exception {
    public String code;
    public String message;

    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(String detailMessage, String code, String message) {
        super(detailMessage);
        this.code = code;
        this.message = message;
    }

    public ServerException(String detailMessage, Throwable throwable, String code, String message) {
        super(detailMessage, throwable);
        this.code = code;
        this.message = message;
    }

    public ServerException(Throwable throwable, String code, String message) {
        super(throwable);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
