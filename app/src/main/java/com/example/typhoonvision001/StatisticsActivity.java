package com.example.typhoonvision001;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typhoonvision001.Function.JsonData;
import com.example.typhoonvision001.Function.LineGraphicView;
import com.example.typhoonvision001.Function.NetWorks;
import com.example.typhoonvision001.Function.TyStatistics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 乱不得静 on 2017/4/8.
 */
public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

//    private LineGraphicView tu;
    private EditText fyid,lyid;
    private Button cul_btn,sta_btn;
    private TextView cul_data_tv;
    private ArrayList<String>  calresults=new ArrayList(),xs=new ArrayList();
    private ArrayList<Integer>  ys=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_statistics);
//        tu = (LineGraphicView) findViewById(R.id.line_graphic);

        cul_data_tv = (TextView) findViewById(R.id.cul_data_tv);
        cul_data_tv.setMovementMethod(new ScrollingMovementMethod());

        fyid = (EditText) findViewById(R.id.fyid);
        lyid = (EditText) findViewById(R.id.lyid);

        cul_btn = (Button) findViewById(R.id.cul_btn);
        cul_btn.setOnClickListener(this);

        sta_btn = (Button) findViewById(R.id.sta_btn);
        sta_btn.setOnClickListener(this);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(StatisticsActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(StatisticsActivity.this, "上传失败，数据已保存，请联网后再登录上传", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(StatisticsActivity.this, "查找失败，请联网后或填写正确后再查找", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(StatisticsActivity.this, "请填写设备ID", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(StatisticsActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(StatisticsActivity.this, "上传失败，请联网或填写正确后再上传维修情况", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cul_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                       try {
                           int fy =Integer.parseInt(fyid.getText().toString());
                           int ly =Integer.parseInt(lyid.getText().toString())+1;
                           for (int i=fy;i<ly;i++){
                               String j= Integer.toString(i);
                               String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonList/" + j + "?callback=jQuery";
                               final String result = NetWorks.MethodGet(url);
                               String getjson = result.substring(7);
                               final List<String> results = JsonData.StaJson(getjson);
//                               String qqq = Integer.toString(TyStatistics.Count(results));
//                               calresults.add(j+"  年有  "+qqq +"  次台风\n");
                               int y = TyStatistics.Count(results);
                               ys.add(y);
                               xs.add(j);

                           }


                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
//                                   String a =calresults.toString();
//                                   cul_data_tv.setText(a);
//                                   calresults.clear();
//                                   tu.setData(ys, xs, 40, 8);

                                   Bundle bundle =new Bundle();
                                   bundle.putStringArrayList("xs",xs);
                                   bundle.putIntegerArrayList("ys",ys);

                                   Intent intent=new Intent(StatisticsActivity.this,StatisticsActivityView.class);
                                   intent.putExtras(bundle);
                                   startActivity(intent);

                               }
                           });
                       }catch (Exception e){

                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(StatisticsActivity.this, "请输入正确数值或者等待一会", Toast.LENGTH_SHORT).show();
                               }
                           });

                       }
                    }
                }).start();
                break;


            case R.id.sta_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            int fy =Integer.parseInt(fyid.getText().toString());
                            int ly =Integer.parseInt(lyid.getText().toString())+1;
                            for (int i=fy;i<ly;i++){
                                String j= Integer.toString(i);
                                String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonList/" + j + "?callback=jQuery";
                                final String result = NetWorks.MethodGet(url);
                                String getjson = result.substring(7);
                                final List<String> results = JsonData.StaJson(getjson);
                               String qqq = Integer.toString(TyStatistics.Count(results));
                               calresults.add(j+"  年有  "+qqq +"  次台风\n");
//                                int y = TyStatistics.Count(results);
//                                ys.add(y);
//                                xs.add(j);

                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   String a =calresults.toString();
                                   cul_data_tv.setText(a);
                                   calresults.clear();
//                                   tu.setData(ys, xs, 40, 8);

//                                    Bundle bundle =new Bundle();
//                                    bundle.putStringArrayList("xs",xs);
//                                    bundle.putIntegerArrayList("ys",ys);
//
//                                    Intent intent=new Intent(StatisticsActivity.this,StatisticsActivityView.class);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);

                                }
                            });
                        }catch (Exception e){

                            runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(StatisticsActivity.this, "请输入正确数值或者等待一会", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
        }
    }
}