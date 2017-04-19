package com.example.jhon.venue.View;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.TimeLine;
import com.example.jhon.venue.Entity.MessageEvent;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Map.Location;
import com.example.jhon.venue.Modle.TimeLineModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeLineActivity extends BaseActivity {


    @BindView(R.id.map_timeline)
    MapView mapTimeline;
    @BindView(R.id.card_timeline)
    CardView cardTimeline;
    @BindView(R.id.tv_timeline_title)
    TextView tvTimelineTitle;
    @BindView(R.id.tv_timeline_time)
    TextView tvTimelineTime;

    private AMap aMap = null;
    private Bundle savedInstanceState;

    private Location location;
    private List<TimeLine> lines;
    private String[] title;
    private int which;

    @Override
    public void initView() {
        setContentView(R.layout.activity_time_line);
        setupToolbar(true, true);
        getSupportActionBar().setTitle("远行");
        ButterKnife.bind(this);
        //EventBus-----------注册
        EventBus.getDefault().register(this);

        mapTimeline.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        if (aMap == null) {
            aMap = mapTimeline.getMap();
            aMap.clear();
            UiSettings settings = aMap.getUiSettings();
            settings.setLogoPosition(0);
            settings.setScrollGesturesEnabled(false); //这个方法设置了地图是否允许通过手势来移动。
            settings.setTiltGesturesEnabled(false);//这个方法设置了地图是否允许通过手势来倾斜。
            settings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            settings.setZoomControlsEnabled(false);//这个方法设置了地图是否允许显示缩放按钮。
            settings.setRotateGesturesEnabled(false);//这个方法设置了地图是否允许通过手势来旋转。
            mapTimeline.setEnabled(false);
        }
    }

    @Override
    public void initOperation() {

    }

    @OnClick(R.id.card_timeline)
    public void onViewClicked() {
        transform();
    }

    private void transform() {
        TimeLineModle.getListTimeLine(this, new ParseListener() {
            @Override
            public void error(Exception e) {
                ShowUtil.showToast(TimeLineActivity.this, e.getMessage());
            }

            @Override
            public void parseJson(String response) {
                lines = JsonUtil.stringToList(JsonUtil.getString("list", JsonUtil.getEntity(response.toString())), TimeLine.class);
                if (lines != null) {
                    ShowUtil.showToast(getApplicationContext(), lines.size() + "");
                    title = new String[lines.size()];
                    for (int i = 0; i < lines.size(); i++) {
                        title[i] = lines.get(i).getTitle();
                    }
                }
                showItemDialog();
            }
        });
    }

    private void showItemDialog() {
        if (title != null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("时间轴")
//                .setView(new Button(TimeLineActivity.this))
                    .setSingleChoiceItems(title, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TimeLineActivity.this.which = which;
                            String tit = title[which];
                            ShowUtil.showToast(getApplicationContext(), tit);
                        }
                    })
                    .setNegativeButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showAddDialog();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        lines=TimeLineModle.getListTimeLine(TimeLineActivity.this);
                            if (lines != null) {
                                BeanUtil.setTimeLine(lines.get(TimeLineActivity.this.which));
                                EventBus.getDefault().postSticky(BeanUtil.getTimeLine());
                                tvTimelineTitle.setText(lines.get(TimeLineActivity.this.which).getTitle());
//                                Date date=new Date(lines.get(TimeLineActivity.this.which).getDate());
//                                tvTimelineTime.setText(date.getYear()+"-"+date.getMonth());
                            }
                        }
                    })
                    .create();
            alertDialog.show();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("时间轴")
                    .setMessage("没有时间轴,请自行创建一个")
                    .setNegativeButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showAddDialog();
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }

    private void showAddDialog(){
        final EditText editText=new EditText(this);
        editText.setHint("时间轴标题");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("创建时间轴")
                .setView(editText)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TimeLineModle.createTimeLine(TimeLineActivity.this, editText.getText().toString(), new JudgeListener() {
                            @Override
                            public void onSuccess() {
                                ShowUtil.showToast(TimeLineActivity.this,"创建时间轴成功");
                                Preference.saveTimeLineId(TimeLineActivity.this,BeanUtil.getTimeLine().getId());
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }
                })
                .create();
        alertDialog.show();
    }

    //EventBus-----------
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventBus(TimeLine  timeLine){
        tvTimelineTitle.setText(timeLine.getTitle());
    }
    //EventBus-----------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapTimeline.onSaveInstanceState(outState);
        savedInstanceState = outState;
    }

    @Override
    public void onResume() {
        Log.i("sys", "mf onResume");
        super.onResume();
        mapTimeline.onResume();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        Log.i("sys", "mf onPause");
        super.onPause();
        mapTimeline.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mapTimeline.onDestroy();
    }
}
