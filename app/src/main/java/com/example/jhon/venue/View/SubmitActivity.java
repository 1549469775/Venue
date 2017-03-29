package com.example.jhon.venue.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Modle.SubmitModle;
import com.example.jhon.venue.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/3/27.
 */

public class SubmitActivity extends BaseActivity {
    @BindView(R.id.et_submit_title)
    EditText etSubmitTitle;
    @BindView(R.id.img_submit_booktop)
    ImageView imgSubmitBooktop;
    @BindView(R.id.et_submit_assent)
    EditText etSubmitAssent;
    @BindView(R.id.tv_submit_location)
    TextView tvSubmitLocation;
    @BindView(R.id.tv_submit_location_data)
    TextView tvSubmitLocationData;
    @BindView(R.id.switch_private)
    Switch switchPrivate;
    @BindView(R.id.btn_submit_submit)
    Button btnSubmitSubmit;

    @Override
    public void initView() {
        setContentView(R.layout.submit_activity);
        setupToolbar(true, true);
        getToolbar().setTitle("文章编辑");
        ButterKnife.bind(this);
    }

    @Override
    public void initOperation() {

    }

    @OnClick({R.id.img_submit_booktop, R.id.switch_private, R.id.btn_submit_submit})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.img_submit_booktop:
                break;
            case R.id.switch_private:
                break;
            case R.id.btn_submit_submit:
                break;
        }
    }
}
