package com.example.kk.arttraining.receiver.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 19:11
 * 说明:
 */
public class JpushBean {
    String type;
    String value;

    public JpushBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JpushBean{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
