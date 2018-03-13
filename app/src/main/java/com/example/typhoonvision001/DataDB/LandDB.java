package com.example.typhoonvision001.DataDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 乱不得静 on 2017/4/23.
 */
public class LandDB extends SQLiteOpenHelper {

    public static final String CREATE_LANDDB = "create table landdb ("
            + "id integer primary key autoincrement, "
            + "lat double, "
            + "lng double, "
            + "isactive integer, "
            + "name text, "
            + "tfid integer, "
            + "warnlevel integer, "
            + "landaddress text, "
            + "landtime text, "
            + "strong text, "
            + "info text)";

    private Context mContext;
    public LandDB(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LANDDB);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists landdb");
        onCreate(db);
    }
}
