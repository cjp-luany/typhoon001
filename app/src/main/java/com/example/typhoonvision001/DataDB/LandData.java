package com.example.typhoonvision001.DataDB;

/**
 * Created by 乱不得静 on 2017/4/22.
 */
public class LandData {

    //private double centerlat,centerlng;
    private double lat,lng;
    private String isactive;
    private String name;
    private String tfid;
    private String warnlevel;

    public LandData(double lat, double lng, String isactive, String name, String tfid, String warnlevel, String landaddress, String landtime, String strong, String info) {
        this.lat = lat;
        this.lng = lng;
        this.isactive = isactive;
        this.name = name;
        this.tfid = tfid;
        this.warnlevel = warnlevel;
        this.landaddress = landaddress;
        this.landtime = landtime;
        this.strong = strong;
        this.info = info;
    }



    public String getLandaddress() {
        return landaddress;
    }

    public void setLandaddress(String landaddress) {
        this.landaddress = landaddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTfid() {
        return tfid;
    }

    public void setTfid(String tfid) {
        this.tfid = tfid;
    }

    public String getWarnlevel() {
        return warnlevel;
    }

    public void setWarnlevel(String warnlevel) {
        this.warnlevel = warnlevel;
    }

    public String getLandtime() {
        return landtime;
    }

    public void setLandtime(String landtime) {
        this.landtime = landtime;
    }

    public String getStrong() {
        return strong;
    }

    public void setStrong(String strong) {
        this.strong = strong;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String landaddress;
    private String landtime;
    private String strong;
    private String info;
}
