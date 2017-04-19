package com.example.jhon.venue.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Map.Location;
import com.example.jhon.venue.Map.MapUtil;
import com.example.jhon.venue.Modle.GetModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.CircleImageView;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.example.jhon.venue.Util.TransitionHelper;
import com.example.jhon.venue.View.Detail_Activity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by John on 2017/3/27.
 */

public class MapFragment extends Fragment {

    @BindView(R.id.map)
    MapView mMapView;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_change)
    FloatingActionButton fabChange;
    @BindView(R.id.fab_location)
    FloatingActionButton fabLocation;

    private View view = null;
    private AMap aMap = null;
    private ArrayList<MarkerOptions> list=new ArrayList<>();
    private List<Story> stories=null;
    private MapUtil mapUtil;

    private Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.map_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
//        toolbar.setTitle("Home");
        //http://www.cnblogs.com/mengdd/p/5590634.html关于framgne使用toolbar问题
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.clear();
        }
        location = Location.newInstance(getContext(), mMapView);
        initDate();
        return view;
    }

    private void initDate(){
        fabChange.setClickable(false);
        fabChange.setEnabled(false);
        GetModle.getLocation(getContext(), new ParseListener() {
            @Override
            public void error(Exception e) {

            }

            @Override
            public void parseJson(String response) {
                aMap.clear();
                if (stories==null){
                    stories=JsonUtil.stringToList(JsonUtil.getString("list", JsonUtil.getEntity(response.toString())),Story.class) ;
                }else {
                    stories.clear();
                    stories.addAll(JsonUtil.stringToList(JsonUtil.getString("list", JsonUtil.getEntity(response.toString())),Story.class));
                }
                if (mapUtil==null){
                    mapUtil=MapUtil.newInstance();
                }
                mapUtil.addMarker(getContext(), stories, aMap, new MapUtil.OnMarkerClicked() {
                    @Override
                    public void onClick(Marker marker) {
                        marker.setAnimation(new AlphaAnimation(1f,0.5f));
                        marker.startAnimation();
                        ShowUtil.showToast(getContext(), stories.get(Integer.parseInt(marker.getTitle())).getTitle() + "sdasda");

                        //页面跳转，详情页
                        EventBus.getDefault().postSticky(stories.get(Integer.parseInt(marker.getTitle())));
                        startActivity(new Intent(getContext(), Detail_Activity.class));
                    }
                }, new JudgeListener() {
                    @Override
                    public void onSuccess() {
                        fabChange.setClickable(true);
                        fabChange.setEnabled(true);
                    }

                    @Override
                    public void onError(Exception e) {
                        fabChange.setClickable(true);
                        fabChange.setEnabled(true);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        Log.i("sys", "mf onResume");
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        Log.i("sys", "mf onPause");
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        location.onDestroy();
        mMapView.onDestroy();

        unbinder.unbind();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
//        Log.i("sys", "mf onDestroy");
        super.onDestroy();
//        location.onDestroy();
//        mMapView.onDestroy();
    }

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("sys", "mf onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.fab_change, R.id.fab_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_change:
                initDate();
                break;
            case R.id.fab_location:
                timer.schedule(timerTask,0,2000);
//                location.startLoscation();
                break;
        }
    }
    private Timer timer=new Timer();
    int i=1;
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            if (i!=40){
                aMap.clear();
                ShowUtil.showLog("distance",""+i);
                MapUtil.newInstance().startMove(i++,aMap);
//
            }else {
                ShowUtil.showLog("distance","Good");
                timer.cancel();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_grid:
                Snackbar.make(view,"menu",Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
