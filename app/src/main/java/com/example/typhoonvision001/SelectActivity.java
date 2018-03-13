package com.example.typhoonvision001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 乱不得静 on 2017/4/17.
 */
public class SelectActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_forecase,iv_query,iv_word,iv_calculate,iv_geo,iv_link,iv_explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        iv_forecase = (ImageView) findViewById(R.id.iv_forecase);
        iv_forecase.setOnClickListener(this);

        iv_query = (ImageView) findViewById(R.id.iv_query);
        iv_query.setOnClickListener(this);

        iv_word = (ImageView) findViewById(R.id.iv_word);
        iv_word.setOnClickListener(this);

        iv_calculate = (ImageView) findViewById(R.id.iv_calculate);
        iv_calculate.setOnClickListener(this);

        iv_geo = (ImageView) findViewById(R.id.iv_geo);
        iv_geo.setOnClickListener(this);

        iv_link = (ImageView) findViewById(R.id.iv_link);
        iv_link.setOnClickListener(this);

        iv_explain = (ImageView) findViewById(R.id.iv_explain);
        iv_explain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_forecase:
                Intent intent1 = new Intent(SelectActivity.this, ForecastActivity.class);
                startActivity(intent1);
                break;

            case R.id.iv_query:
                Intent intent2 = new Intent(SelectActivity.this, ListActivity.class);
                startActivity(intent2);
                break;

            case R.id.iv_word:
                Intent intent3 = new Intent(SelectActivity.this, ArticleActivity.class);
                startActivity(intent3);
                break;

            case R.id.iv_calculate:
                Intent intent4 = new Intent(SelectActivity.this, StatisticsActivity.class);
                startActivity(intent4);
                break;

            case R.id.iv_geo:
                Intent intent5 = new Intent(SelectActivity.this, TyMapActivity.class);
                startActivity(intent5);
                break;

            case R.id.iv_link:
                Intent intent6 = new Intent(SelectActivity.this, LinkActivity.class);
                startActivity(intent6);
                break;

            case R.id.iv_explain:
                Intent intent7 = new Intent(SelectActivity.this, ExplainActivity.class);
                startActivity(intent7);
                break;
            default:
                break;
        }
    }
}
