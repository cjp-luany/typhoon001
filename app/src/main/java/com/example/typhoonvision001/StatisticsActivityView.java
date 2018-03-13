package com.example.typhoonvision001;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.typhoonvision001.Function.LineGraphicView;

import java.util.ArrayList;

/**
 * Created by 乱不得静 on 2017/4/23.
 */
public class StatisticsActivityView extends AppCompatActivity implements View.OnClickListener{

    private Button back_btn;
    private LineGraphicView tu;
    private ArrayList<Integer> yList = new ArrayList<Integer>();
    private ArrayList<String> xRawDatas = new ArrayList<String>();
    private ArrayList<Double> yDouble=new ArrayList<Double>();


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_statistics_help);
        tu = (LineGraphicView) findViewById(R.id.line_graphic);

        back_btn=(Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
//
//
//        yList.add(34);
//        yList.add(23);
//        yList.add(12);
//        yList.add(33);
//        yList.add(32);
//        yList.add(24);
//        yList.add(13);
//
//        xRawDatas.add("05-19");
//        xRawDatas.add("05-20");
//        xRawDatas.add("05-21");
//        xRawDatas.add("05-22");
//        xRawDatas.add("05-23");
//        xRawDatas.add("05-24");
//        xRawDatas.add("05-25");
//        xRawDatas.add("05-26");


        //  Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle=this.getIntent().getExtras();
        yList=bundle.getIntegerArrayList("ys");
        int len=yList.toArray().length;
        for (int i=0;i<len;i++){
            double var1=yList.get(i);
            yDouble.add(var1);
        }

        xRawDatas=bundle.getStringArrayList("xs");
        tu.setData(yDouble, xRawDatas, 40, 10);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_btn:
                yDouble.clear();xRawDatas.clear();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yDouble.clear();xRawDatas.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yDouble.clear();xRawDatas.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        yDouble.clear();xRawDatas.clear();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        yDouble.clear();xRawDatas.clear();
//    }
}
