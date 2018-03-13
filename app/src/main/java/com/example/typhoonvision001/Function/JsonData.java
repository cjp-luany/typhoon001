package com.example.typhoonvision001.Function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.typhoonvision001.DataDB.LandDB;
import com.example.typhoonvision001.DataDB.ListDB;
import com.example.typhoonvision001.DataDB.TyMapDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 乱不得静 on 2017/3/21.
 */
public class JsonData {

    public static List<String> StaJson(String jsonData) {

        List<String> a = new ArrayList<String>();
        try{

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String tfid = jsonObj.getString("tfid");
                a.add(tfid);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static List<String> ForecastJson(String jsonData){
        List<String> a = new ArrayList<String>();
        int sum=0;
        try{

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String name = jsonObj.getString("name")+"活跃中 \n";
                int isactive = jsonObj.getInt("isactive");
                sum += isactive;
                if(isactive>0){
                    a.add(name);
                }
            }
            if(sum>0){
                a.add("\n 请点击文本查询");
                return a;
            }else{
                a.clear();
                return a;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static List<String> ListJson(String jsonData, Context mycontext) {
        ListDB dbHelper;
        dbHelper = new ListDB(mycontext,"list",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("list",null,null);

        List<String> a = new ArrayList<String>();
        try{

            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String name ="【"+ jsonObj.getString("name")+ "】";
                String tfid = jsonObj.getString("tfid");
                String warnlevel = "初始等级" +jsonObj.getString("warnlevel");
                String stime = jsonObj.getString("starttime");
                String etime = jsonObj.getString("endtime");

                values.put("name", name);
                values.put("tfid", tfid);
                values.put("warnlevel", warnlevel);
                db.insert("list", null, values);
                values.clear();

                String b =name+tfid+warnlevel;
                String e=b+"\n";
                a.add(e);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static  List<String> lvInfoJson(String jsonData, Context mycontext) {
        LandDB dbHelper;
        dbHelper = new LandDB(mycontext,"landdb",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("landdb",null,null);

        List<String> a = new ArrayList<String>();
        List<String> k = new ArrayList<String>();
        List<String> q = new ArrayList<String>();


        try {

            JSONArray obj1 = new JSONArray(jsonData);
            for (int i = 0; i < obj1.length(); i++) {
                JSONObject jsonObj = obj1.getJSONObject(i);

                String name = jsonObj.getString("name");
                String tfid = jsonObj.getString("tfid");
                String warnlevel ="初始等级" +jsonObj.getString("warnlevel");
                String isactive =jsonObj.getString("isactive");

                String g =name+tfid+warnlevel+isactive;
                a.add(g);

                JSONArray obj3=jsonObj.getJSONArray("land");
                for(int j=0;j<obj3.length();j++) {
                    JSONObject jsonObj3 = obj3.getJSONObject(j);
                    String info = jsonObj3.getString("info");//纬度
                    String landaddress =jsonObj3.getString("landaddress");//经度
                    String landtime = jsonObj3.getString("landtime");//纬度
                    String lng =jsonObj3.getString("lng");//经度
                    String lat = jsonObj3.getString("lat");//纬度
                    String strong =jsonObj3.getString("strong");//经度

                    values.put("lat",lat);
                    values.put("lng",lng);
                    values.put("isactive",isactive);
                    values.put("name",name);
                    values.put("tfid",tfid);
                    values.put("warnlevel",warnlevel);
                    values.put("landaddress",landaddress);
                    values.put("landtime",landtime);
                    values.put("strong",strong);
                    values.put("info",info);

                    db.insert("landdb", null, values);
                    values.clear();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return a;
    }


    public static List<String> InfoJson(String jsonData, Context mycontext) {

        TyMapDB dbHelper;
        dbHelper = new TyMapDB(mycontext,"info",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("info",null,null);

        List<String> a = new ArrayList<String>();
        List<String> k = new ArrayList<String>();
        List<String> q = new ArrayList<String>();

    try{

    JSONArray obj1 = new JSONArray(jsonData);
    for (int i = 0; i < obj1.length(); i++) {
        JSONObject jsonObj = obj1.getJSONObject(i);

        String name = jsonObj.getString("name");
        String tfid ="  "+ jsonObj.getString("tfid");
        String warnlevel = "  初始等级" +jsonObj.getString("warnlevel");
        String stime = jsonObj.getString("starttime");
        String etime = jsonObj.getString("endtime");
        String b = name+tfid+warnlevel;

        JSONArray obj3=jsonObj.getJSONArray("land");
        for(int j=0;j<obj3.length();j++) {
            JSONObject jsonObj3 = obj3.getJSONObject(j);
            String info = jsonObj3.getString("info");//纬度
            String landaddress =jsonObj3.getString("landaddress");//经度
            String landtime = jsonObj3.getString("landtime");//纬度
            String lng =jsonObj3.getString("lng");//经度
            String lat = jsonObj3.getString("lat");//纬度
            String strong =jsonObj3.getString("strong");//经度
            String var3=info+"\n"+"地点"+landaddress+"\n"+strong+"  "+landtime+"\n"+"\n";
            q.add(var3);
        }

        JSONArray obj2=jsonObj.getJSONArray("points");
        for(int j=0;j<obj2.length();j++){
            JSONObject jsonObj2=obj2.getJSONObject(j);

            String lat = jsonObj2.getString("lat");//纬度
            String lng =jsonObj2.getString("lng");//经度
            String movedirection = jsonObj2.getString("movedirection");//移动方向
            String movespeed = jsonObj2.getString("movespeed");//移动速度  公里/小时
            String power =jsonObj2.getString("power");//风力  /级
            String pressure = jsonObj2.getString("pressure");//  中心气压  /百帕
            String radius10 = jsonObj2.getString("radius10");//  十级半径
            String radius7 = jsonObj2.getString("radius7");//七级半径  /公里
            String speed =jsonObj2.getString("speed");//速度  米/秒
            String strong = jsonObj2.getString("strong");//等级
            String time = jsonObj2.getString("time");//日期

            values.put("名字",name);
            values.put("纬度", lat);
            values.put("经度", lng);
            values.put("移动方向", movedirection);
            values.put("移动速度", movespeed);
            values.put("风力", power);
            values.put("中心气压", pressure);
            values.put("十级半径", radius10);
            values.put("七级半径", radius7);
            values.put("速度", speed);
            values.put("等级", strong);
            values.put("日期", time);
            db.insert("info", null, values);
            values.clear();
        }

        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                double clat = cursor.getDouble(cursor.getColumnIndex("纬度"));
                double clng = cursor.getDouble(cursor.getColumnIndex("经度"));
                String ctime = cursor.getString(cursor.getColumnIndex("日期"));
                String g=ctime+"\n"+"纬度："+String.valueOf(clat)+"\n"+"经度："+String.valueOf(clng)+"\n"+"\n";
                k.add(g);
            } while (cursor.moveToNext());
        }

        cursor.close();
        String e=b+"\n"+q+"\n";
        a.add(e);
        db.close();
    }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }
}
