package com.example.jhon.venue.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.jhon.venue.Adapter.TimeLineAdapter;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2017/5/8.
 */

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar返回可用true
        getSupportActionBar().setDisplayShowTitleEnabled(true);//false

        rvCollect.setLayoutManager(new LinearLayoutManager(this));
        rvCollect.setAdapter(new TimeLineAdapter(CollectActivity.this, BeanUtil.getList()));

    }

}
