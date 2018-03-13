package com.example.typhoonvision001.DataDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 乱不得静 on 2017/4/2.
 */
public class TyMapDB extends SQLiteOpenHelper {

    public static final String CREATE_INFO = "create table info ("
            + "id integer primary key autoincrement, "
            + "名字 text, "
            + "纬度 double, "
            + "经度 double, "
            + "移动方向 text, "
            + "移动速度 integer, "
            + "风力 integer, "
            + "中心气压 integer, "
            + "十级半径 integer, "
            + "七级半径 integer, "
            + "速度 integer, "
            + "等级 text, "
            + "日期 text)";

    private Context mContext;
    public TyMapDB(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INFO);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists info");
        onCreate(db);
    }
}
