package com.example.jhon.venue.View;

import android.support.v7.app.AppCompatActivity;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.R;

/**
 * Created by John on 2017/3/27.
 */

public class SubmitActivity extends BaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.submit_activity);
        setupToolbar(true,true);
        getToolbar().setTitle("文章编辑");
    }

    @Override
    public void initOperation() {

    }
}
