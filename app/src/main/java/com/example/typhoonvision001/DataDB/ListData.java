package com.example.typhoonvision001.DataDB;

/**
 * Created by 乱不得静 on 2017/4/6.
 */
public class ListData {
    private String name,tfid,warnlevel;

    public ListData(String name, String tfid, String warnlevel) {
        this.name = name;
        this.tfid = tfid;
        this.warnlevel = warnlevel;
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
}
