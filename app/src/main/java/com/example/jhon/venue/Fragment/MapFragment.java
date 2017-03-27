package com.example.jhon.venue.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.example.jhon.venue.Map.Location;
import com.example.jhon.venue.R;

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
        return view;
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
        unbinder.unbind();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
        Log.i("sys", "mf onDestroy");
        super.onDestroy();
        location.onDestroy();
        mMapView.onDestroy();
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
                break;
            case R.id.fab_location:
                location.startLocation();
                break;
        }
    }

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
