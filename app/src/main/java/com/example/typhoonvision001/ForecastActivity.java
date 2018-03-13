package com.example.typhoonvision001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.typhoonvision001.Function.JsonData;
import com.example.typhoonvision001.Function.NetWorks;

import java.util.List;

/**
 * Created by 乱不得静 on 2017/3/23.
 */
public class ForecastActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView fore_tv,fore_test_tv;
    private final static  String yl = "2017";
    private Button test_btn;
    private SharedPreferences pref;
    private CheckBox forecast_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_forecast);

        forecast_test = (CheckBox)findViewById(R.id.forecast_test);


//        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isRemember = pref.getBoolean("forecast_test",true);

        test_btn = (Button) findViewById(R.id.test_btn);
        test_btn.setOnClickListener(this);

        fore_tv = (TextView) findViewById(R.id.fore_tv);
        fore_tv.setOnClickListener(this);

        fore_test_tv = (TextView) findViewById(R.id.fore_test_tv);
        fore_test_tv.setOnClickListener(this);

//        if(isRemember){
//            forecast_test.setChecked(false);
//        }

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    forecast_test.setChecked(false);
                    String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonList/"+yl+"?callback=jQuery";
                    final String result = NetWorks.MethodGet(url);
                    String getjson = result.substring(7);
//                String judge = NetWorks.MethodGet(url);
//                String testJson="[{\"endtime\":\"2016\\/7\\/10 11:00:00\",\"enname\":\"Nepartak\",\"isactive\":\"1\",\"name\":\"尼伯特\",\"starttime\":\"2016\\/7\\/3 8:00:00\",\"tfid\":\"201601\",\"warnlevel\":\"6\"}]";
                    final List<String> results = JsonData.ForecastJson(getjson);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        List<String> d = new ArrayList<String>();
//                        if(results.equals(null)){
//                            String a = "当前没有活跃中的台风";
//                            fore_tv.setText(a);
//                        }else{
//                            for (int i = 0;i<results.toArray().length;i++){
//                                String n =results.get(i).toString()+"活跃中，请点击到台风查询 \n";
//                                d.add(n);
//                            }
//                            String a = d.toString();
//                            fore_tv.setText(a);
//                        }
                            if(results.isEmpty()){
                                String a = "当前没有活跃中的台风";
                                fore_tv.setText(a);
                                Toast.makeText(ForecastActivity.this, "当前没有活跃中的台风", Toast.LENGTH_SHORT).show();
                            }else{
                                String a = results.toString();
                                fore_tv.setText(a);
                            }
                        }
                    });
                }
            }).start();
        }catch (Exception e){
            Toast.makeText(ForecastActivity.this, "请保持网络稳定并稍等或重新进入页面", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.fore_tv:
                Intent intent = new Intent(ForecastActivity.this,ListActivity.class);
                intent.putExtra("Year",yl);
                startActivity(intent);
                break;

            case R.id.fore_test_tv:
                Intent intent2 = new Intent(ForecastActivity.this,InfoActivity.class);
                intent2.putExtra("Tfid","201601");
                startActivity(intent2);
                break;

            case R.id.test_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String url = "http://typhoon.zjwater.gov.cn/Api/TyphoonList/"+yl+"?callback=jQuery";
                        final String result = NetWorks.MethodGet(url);
                        String getjson = result.substring(7);
//                String judge = NetWorks.MethodGet(url);
                String testJson="[{\"endtime\":\"2016\\/7\\/10 11:00:00\",\"enname\":\"Nepartak\",\"isactive\":\"1\",\"name\":\"尼伯特\",\"starttime\":\"2016\\/7\\/3 8:00:00\",\"tfid\":\"201601\",\"warnlevel\":\"6\"}]";
                        final List<String> results = JsonData.ForecastJson(testJson);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                        List<String> d = new ArrayList<String>();
//                        if(results.equals(null)){
//                            String a = "当前没有活跃中的台风";
//                            fore_tv.setText(a);
//                        }else{
//                            for (int i = 0;i<results.toArray().length;i++){
//                                String n =results.get(i).toString()+"活跃中，请点击到台风查询 \n";
//                                d.add(n);
//                            }
//                            String a = d.toString();
//                            fore_tv.setText(a);
//                        }

                                forecast_test.setChecked(true);
                                if(results.isEmpty()){
                                    String a = "当前没有活跃中的台风";

                                    fore_test_tv.setText(a);
                                }else{
                                    String a = results.toString();
                                    fore_test_tv.setText(a);
                                }
                            }
                        });
                    }
                }).start();
                break;
        }
    }

}
