package com.example.typhoonvision001.DataDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 乱不得静 on 2017/4/6.
 */
public class ListDB extends SQLiteOpenHelper {
    public static final String CREATE_LIST = "create table list ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "tfid text, "
            + "warnlevel text)";

    private Context mContext;
    public ListDB(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists list");
        onCreate(db);
    }
}
