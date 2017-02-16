package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2017/2/8 15:04
 * 说明:礼物bean
 */
public class GiftBean {
    int gift_id;
    String pic;
    String name;
    String type;
    double price;
    int gift_num;
    String gift_name;


    public GiftBean(){};
    public  GiftBean(String name,String pic){
        this.name=name;
        this.pic=pic;
    }

    public int getGift_id() {
        return gift_id;
    }

    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGift_num() {
        return gift_num;
    }

    public void setGift_num(int gift_num) {
        this.gift_num = gift_num;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    @Override
    public String toString() {
        return "GiftBean{" +
                "gift_id=" + gift_id +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", gift_num=" + gift_num +
                ", gift_name='" + gift_name + '\'' +
                '}';
    }


}
