package com.example.typhoonvision001;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText year_list;
    private Button list_btn,jump_info,pre_btn;
    private ListView lv;
    private Context ctxt;
    private List<ListData>lvlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_list);

        year_list = (EditText) findViewById(R.id.year_list);

        list_btn = (Button) findViewById(R.id.list_btn);
        list_btn.setOnClickListener(this);

        jump_info = (Button) findViewById(R.id.jump_info);
        jump_info.setOnClickListener(this);

        pre_btn = (Button) findViewById(R.id.art_btn);
        pre_btn.setOnClickListener(this);

        lv=(ListView)findViewById(R.id.lv);

        ctxt =this;

        Intent intent = getIntent();
        String s = intent.getStringExtra("Year");
        year_list = (EditText) findViewById(R.id.year_list);
        year_list.setText(s);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(ListActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(ListActivity.this, "上传失败，数据已保存，请联网后再登录上传", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(ListActivity.this, "查找失败，请联网后或填写正确后再查找", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(ListActivity.this, "请填写设备ID", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(ListActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(ListActivity.this, "上传失败，请联网或填写正确后再上传维修情况", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.list_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String yl = year_list.getText().toString();

                        String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonList/"+yl+"?callback=jQuery";
                        final String result = NetWorks.MethodGet(url);
                        String getjson = result.substring(7);
                        String judge = NetWorks.MethodGet(url);
                        final List<String> results = JsonData.ListJson(getjson, ctxt);

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
                                    lvlist=new ArrayList<>();
                                    ListDB dbHelper;
                                    dbHelper = new ListDB(ctxt,"list",null,1);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    Cursor cursor = db.query("list", null, null, null, null, null, null);

                                    if (cursor.moveToFirst()) {
                                        do {
                                            String name = cursor.getString(cursor.getColumnIndex("name"));
                                            String tfid=cursor.getString(cursor.getColumnIndex("tfid"));
                                            String warnlevel=cursor.getString(cursor.getColumnIndex("warnlevel"));
                                            lvlist.add(new ListData(name,tfid,warnlevel));
                                        } while (cursor.moveToNext());
                                    }

                                    cursor.close();
                                    db.close();
                                    List<HashMap<String,Object>>data=new ArrayList<HashMap<String,Object>>();

                                    for(ListData data2:lvlist){
                                        HashMap<String,Object>i=new HashMap<String,Object>();
                                        i.put("name",data2.getName());
                                        i.put("tfid",data2.getTfid());
                                        i.put("warnlevel",data2.getWarnlevel());
                                        data.add(i);
                                    }

                                    SimpleAdapter adapter=new SimpleAdapter(ctxt,data,R.layout.item,new String[]{"name","tfid","warnlevel"},
                                            new int[]{R.id.name,R.id.tfid,R.id.warnlevel});
                                    lv.setAdapter(adapter);

                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            ListView lv =(ListView)parent;
                                            HashMap<String,Object>data=(HashMap<String,Object>)lv.getItemAtPosition(position);
                                            String tfid =data.get("tfid").toString();
                                            Toast.makeText(getApplicationContext(),tfid,Toast.LENGTH_SHORT).show();

                                            Intent intent=new Intent(ListActivity.this,InfoActivity.class);
                                            intent.putExtra("Tfid",tfid);
                                            startActivity(intent);
                                        }
                                    });

                                }
                            });
                        }
                    }
                }).start();
                break;

            case R.id.jump_info:
                Intent intent = new Intent(ListActivity.this,StatisticsActivity.class);
                startActivity(intent);
                break;

            case R.id.art_btn:
                Intent intent1 = new Intent(ListActivity.this,ArticleActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
