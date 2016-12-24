package com.example.lenovo.inequalitysign.adapter;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by ff on 2016/12/24.
 */
public class MarkerInfoUtil implements Serializable {
    private String shop_id;
    private String shop_name;
    private String shop_num;
    private String shop_address;
    private LatLng lng;
    public MarkerInfoUtil(){

    }

    public MarkerInfoUtil(String shop_id, String shop_name, String shop_num, String shop_address, LatLng lng) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_num = shop_num;
        this.shop_address = shop_address;
        this.lng = lng;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_num() {
        return shop_num;
    }

    public void setShop_num(String shop_num) {
        this.shop_num = shop_num;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public LatLng getLng() {
        return lng;
    }

    public void setLng(LatLng lng) {
        this.lng = lng;
    }
}
