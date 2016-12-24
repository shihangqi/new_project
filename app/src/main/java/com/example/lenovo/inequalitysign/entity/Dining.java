package com.example.lenovo.inequalitysign.entity;

/**
 * Created by ff on 2016/12/3.
 */
public class Dining {
    private String shop_id;
    private String url;
    private String name;
    private String introduction;
    private String address;

    public Dining(String shop_id, String url, String name, String introduction, String address) {
        this.shop_id = shop_id;
        this.url = url;
        this.name = name;
        this.introduction = introduction;
        this.address = address;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
