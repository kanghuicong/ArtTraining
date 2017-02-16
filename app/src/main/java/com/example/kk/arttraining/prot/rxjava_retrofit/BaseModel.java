package com.example.kk.arttraining.prot.rxjava_retrofit;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2017/2/8 09:56
 * 说明:
 */
public class BaseModel<T> implements Serializable {
    String error_code;
    String error_msg;

    public T data;

    public boolean success(){
        return error_code.equals("0");
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "data=" + data +
                ", error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
