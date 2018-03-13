package com.example.typhoonvision001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by 乱不得静 on 2017/4/17.
 */
public class ExplainActivity extends AppCompatActivity {
    private TextView exp_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_explain);

        exp_tv = (TextView) findViewById(R.id.exp_tv);
        exp_tv.setMovementMethod(new ScrollingMovementMethod());

        String s = "这是APP的使用说明书";

        exp_tv.setText(s);
    }
}
