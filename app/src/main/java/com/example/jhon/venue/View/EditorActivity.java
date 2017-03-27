package com.example.jhon.venue.View;

import android.graphics.Color;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.R;

/**
 * Created by John on 2017/3/27.
 */

public class EditorActivity extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.editor_activity);
        setupToolbar(true,true);
        getToolbar().setTitle("编辑资料");
        getToolbar().setTitleTextColor(Color.WHITE);
    }

    @Override
    public void initOperation() {

    }
}
