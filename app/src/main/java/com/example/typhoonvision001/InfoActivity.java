package com.example.typhoonvision001;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typhoonvision001.DataDB.LandDB;
import com.example.typhoonvision001.DataDB.LandData;
import com.example.typhoonvision001.DataDB.ListDB;
import com.example.typhoonvision001.DataDB.ListData;
import com.example.typhoonvision001.Function.JsonData;
import com.example.typhoonvision001.Function.NetWorks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 乱不得静 on 2017/3/21.
 */
public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tyid;
    private Button info_btn,geo_btn;
    private TextView info_data_tv;
    private Context ctxt;
    private ListView lv_info;
    private List<LandData>landlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_info);

        lv_info=(ListView)findViewById(R.id.lv_info);

        ctxt =this;

        info_data_tv = (TextView) findViewById(R.id.info_data_tv);
        info_data_tv.setMovementMethod(new ScrollingMovementMethod());
        info_data_tv.setText("空白代表没有台风登陆点数据");

        info_btn = (Button) findViewById(R.id.info_btn);
        info_btn.setOnClickListener(this);

        geo_btn = (Button) findViewById(R.id.map_btn);
        geo_btn.setOnClickListener(this);

        Intent intent = getIntent();
        String s = intent.getStringExtra("Tfid");

        tyid = (EditText) findViewById(R.id.tyid);
        tyid.setText(s);

//        Intent intent2 = getIntent();
//        final String s2 = intent2.getStringExtra("Yearid");


        if(s!=""){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String tid = tyid.getText().toString();
                    String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonInfo/"+tid+"?callback=jQuery";
                    final String result = NetWorks.MethodGet(url);
                    String getjson = result.substring(7);
                    String judge = NetWorks.MethodGet(url);
                   final List<String> results = JsonData.InfoJson(getjson, ctxt);


                    if("1".equals(judge)){
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    }else {
                        Message message = new Message();
                        message.what = 5;
                        handler.sendMessage(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }
            }).start();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(InfoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(InfoActivity.this, "上传失败，数据已保存，请联网后再登录上传", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(InfoActivity.this, "查找失败，请联网后或填写正确后再查找", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(InfoActivity.this, "请填写设备ID", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(InfoActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(InfoActivity.this, "上传失败，请联网或填写正确后再上传维修情况", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.info_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String tid = tyid.getText().toString();
                        String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonInfo/"+tid+"?callback=jQuery";
                        final String result = NetWorks.MethodGet(url);
                        String getjson = result.substring(7);
                        String judge = NetWorks.MethodGet(url);
                        final List<String> results = JsonData.lvInfoJson(getjson, ctxt);

                        if("1".equals(judge)){
                            Message message = new Message();
                            message.what = 3;
                            handler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 5;
                            handler.sendMessage(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    String a =results.toString();
//                                    info_data_tv.setText(a);
                                    landlist=new ArrayList<>();
                                    LandDB dbHelper;
                                    dbHelper = new LandDB(ctxt,"landdb",null,1);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    Cursor cursor = db.query("landdb", null, null, null, null, null, null);

                                    if (cursor.moveToFirst()) {
                                        do {
                                            String name = cursor.getString(cursor.getColumnIndex("name"));
                                            String tfid=cursor.getString(cursor.getColumnIndex("tfid"));
                                            String warnlevel=cursor.getString(cursor.getColumnIndex("warnlevel"));
                                            String isactive=cursor.getString(cursor.getColumnIndex("isactive"));
                                            int a =Integer.parseInt(isactive);String b=aaa(a);
                                            String landaddress=cursor.getString(cursor.getColumnIndex("landaddress"));
                                            String landtime=cursor.getString(cursor.getColumnIndex("landtime"));
                                            String strong=cursor.getString(cursor.getColumnIndex("strong"));
                                            String info=cursor.getString(cursor.getColumnIndex("info"));
                                            double lat=cursor.getDouble(cursor.getColumnIndex("lat"));
                                            double lng=cursor.getDouble(cursor.getColumnIndex("lng"));
                                            landlist.add(new LandData(lat,lng,b,name,tfid,warnlevel,landaddress,landtime,strong,info));
                                        } while (cursor.moveToNext());
                                    }

                                    cursor.close();
                                    db.close();
                                    List<HashMap<String,Object>>data=new ArrayList<HashMap<String,Object>>();

                                    for(LandData data2:landlist){
                                        HashMap<String,Object>i=new HashMap<String,Object>();
                                        i.put("name",data2.getName());
                                        i.put("tfid",data2.getTfid());
                                        i.put("warnlevel",data2.getWarnlevel());
                                        i.put("isactive",data2.getIsactive());
                                        i.put("landaddress","登陆地"+data2.getLandaddress());
                                        i.put("landtime","登陆时间"+data2.getLandtime());
                                        i.put("strong","强度"+data2.getStrong());
                                        i.put("info",data2.getInfo());
                                        i.put("lat","纬度"+data2.getLat());
                                        i.put("lng","经度"+data2.getLng());
                                        data.add(i);
                                    }

                                    SimpleAdapter adapter=new SimpleAdapter(ctxt,data,R.layout.item_info,new String[]
                                            {"name","tfid","warnlevel","landaddress","landtime","lat","lng","isactive","strong","info"},
                                            new int[]{R.id.info_name,R.id.info_tfid,R.id.info_warnlevel,R.id.info_landaddress,R.id.info_landtime,R.id.info_lat,R.id.info_lng,R.id.info_isactive,R.id.info_strong,R.id.info_info});
                                    lv_info.setAdapter(adapter);
                                }
                            });
                        }
                    }
                }).start();
                break;

            case R.id.map_btn:
                Intent intent = new Intent(InfoActivity.this,TyMapActivity.class);
                startActivity(intent);
                break;
        }
    }

    public String aaa(int var1){

        String b;
        if(var1>0){
            return b="活跃中";
        }else{
            return b="不活跃";
        }
    }
}
