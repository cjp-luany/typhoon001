package com.example.typhoonvision001.hide;

import java.io.Serializable;

/**
 * Created by 乱不得静 on 2017/3/29.
 */
public class MarkerInfoUtil implements Serializable {

    private static final long serialVersionUID = 8633299996744734593L;

    private double latitude;//纬度
    private double longitude;//经度
    private String name;//名字
    private int imgId;//图片
    private String description;//描述
    //构造方法
    public MarkerInfoUtil() {}
    public MarkerInfoUtil(double latitude, double longitude, String name, int imgId, String description) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.imgId = imgId;
        this.description = description;
    }
    //toString方法
    @Override
    public String toString() {
        return "MarkerInfoUtil [latitude=" + latitude + ", longitude=" + longitude + ", name=" + name + ", imgId="
                + imgId + ", description=" + description + "]";
    }
    //getter setter
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
