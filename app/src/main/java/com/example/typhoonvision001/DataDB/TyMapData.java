package com.example.typhoonvision001.DataDB;

/**
 * Created by 乱不得静 on 2017/3/31.
 */
public class TyMapData {

    private String time;
    private String name;
    private double latitude;//纬度
    private double longitude;//经度
    private String movedirection,strong;

    private int movespeed,power,pressure,radius10,radius7,speed;

    public TyMapData(String name,String time, double latitude, double longitude, String movedirection, int movespeed, int power, int pressure, int radius10, int radius7, int speed, String strong) {
        this.name=name;
        this.time = time;//日期
        this.latitude = latitude;//纬度
        this.longitude = longitude;//经度
        this.movedirection=movedirection;//移动方向
        this.movespeed=movespeed;//移动速度
        this.power=power;//等级
        this.pressure=pressure;//中心气压
        this.radius7=radius7;//7级半径
        this.radius10=radius10;//10级半径
        this.speed=speed;//速度
        this.strong=strong;//强度
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovedirection() {
        return movedirection;
    }

    public void setMovedirection(String movedirection) {
        this.movedirection = movedirection;
    }

    public int getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(int movespeed) {
        this.movespeed = movespeed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getRadius10() {
        return radius10;
    }

    public void setRadius10(int radius10) {
        this.radius10 = radius10;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getRadius7() {
        return radius7;
    }

    public void setRadius7(int radius7) {
        this.radius7 = radius7;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getStrong() {
        return strong;
    }

    public void setStrong(String strong) {
        this.strong = strong;
    }

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
}
