package com.example.typhoonvision001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 乱不得静 on 2017/4/17.
 */
public class LinkActivity extends AppCompatActivity {

    private TextView link_tv0, link_tv1, link_tv2, link_tv3, link_tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typhoon_link);

        link_tv0 = (TextView) findViewById(R.id.link_tv0);
        link_tv1 = (TextView) findViewById(R.id.link_tv1);
        link_tv2 = (TextView) findViewById(R.id.link_tv2);
        link_tv3 = (TextView) findViewById(R.id.link_tv3);
        link_tv4 = (TextView) findViewById(R.id.link_tv4);

    }
}
