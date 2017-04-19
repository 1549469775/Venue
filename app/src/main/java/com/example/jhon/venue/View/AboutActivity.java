package com.example.jhon.venue.View;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.CircleImageView;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/4/18.
 */

public class AboutActivity extends BaseActivity implements View.OnTouchListener {

    @BindView(R.id.img_about_person)
    CircleImageView imgAboutPerson;
    @BindView(R.id.img_about_person_right)
    CircleImageView imgAboutPersonRight;
    @BindView(R.id.textaaaaaa)
    RelativeLayout textaaaaaa;
    @BindView(R.id.textbbbbb)
    RelativeLayout textbbbbb;
    @BindView(R.id.img_down)
    ImageView imgDown;

    private int lastX;
    private int lastY;

    @Override
    public void initView() {
        setContentView(R.layout.about);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.img_head).asBitmap().into(imgAboutPerson);
        Glide.with(this).load(R.drawable.img_head).asBitmap().into(imgAboutPersonRight);
        imgAboutPerson.setOnTouchListener(this);
        imgAboutPersonRight.setOnTouchListener(this);
    }

    @Override
    public void initOperation() {

    }


    @OnClick(R.id.img_about_person)
    public void onViewClicked() {
        ShowUtil.showToast(this, "a");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.img_about_person) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    if (left < 0) {
                        left = 0;
                        right = left + v.getWidth();
                    }
                    if (right > v.getWidth()) {
                        right = v.getWidth();
                        left = 0;
                    }
//                if (right > ScreenUtil.getScreenWidth(this)) {
//                    right = ScreenUtil.getScreenWidth(this);
//                    left = right - v.getWidth();
//                }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > (ScreenUtil.getScreenHeight(this) / 2 + v.getWidth() / 2)) {
                        bottom = (ScreenUtil.getScreenHeight(this) / 2 + v.getWidth() / 2);
                        top = bottom - v.getHeight();
                    }
                    v.layout(left, top, right, bottom);
                    imgAboutPersonRight.layout(ScreenUtil.getScreenWidth(this) - v.getWidth(), top, ScreenUtil.getScreenWidth(this), bottom);
                    textaaaaaa.layout(left, 0, ScreenUtil.getScreenWidth(this), bottom - v.getHeight() / 2);
                    textbbbbb.layout(left, top + v.getHeight() / 2, ScreenUtil.getScreenWidth(this), ScreenUtil.getScreenHeight(this));
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    textaaaaaa.setAlpha((float) (top * 0.001));
                    textbbbbb.setAlpha((float) (top * 0.001));
                    imgDown.setAlpha((float) (1-top * 0.001));
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        if (v.getId() == R.id.img_about_person_right) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    if (left < ScreenUtil.getScreenWidth(this) - v.getWidth()) {
                        left = ScreenUtil.getScreenWidth(this) - v.getWidth();
                        right = left + v.getWidth();
                    }
//                    if (left < 0) {
//                        left = 0;
//                        right = left + v.getWidth();
//                    }
//                    if (right > v.getWidth()) {
//                        right = v.getWidth();
//                        left = 0;
//                    }
                    if (right > ScreenUtil.getScreenWidth(this)) {
                        right = ScreenUtil.getScreenWidth(this);
                        left = right - v.getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > (ScreenUtil.getScreenHeight(this) / 2 + v.getWidth() / 2)) {
                        bottom = (ScreenUtil.getScreenHeight(this) / 2 + v.getWidth() / 2);
                        top = bottom - v.getHeight();
                    }
                    v.layout(left, top, right, bottom);
                    imgAboutPerson.layout(0, top, v.getWidth(), bottom);
                    textaaaaaa.layout(0, 0, ScreenUtil.getScreenWidth(this), bottom - v.getHeight() / 2);
                    textbbbbb.layout(0, top + v.getHeight() / 2, ScreenUtil.getScreenWidth(this), ScreenUtil.getScreenHeight(this));
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    textaaaaaa.setAlpha((float) (top * 0.001));
                    textbbbbb.setAlpha((float) (top * 0.001));
                    imgDown.setAlpha((float) (1-top * 0.001));
                    ShowUtil.showLog("setAlpha", "" + top);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
