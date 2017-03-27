package com.example.jhon.venue.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhon.venue.Adapter.PageAdapter;
import com.example.jhon.venue.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

/**
 * Created by John on 2017/3/24.
 */

public class ScanFragment extends Fragment {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout mCoordinatorTabLayout;

    private View view = null;

    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"热门", "故事", "人物", "地点"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.scan_fragment, container, false);
        }
        //使得menu可用
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        initFragments();
        initViewPager();
        mImageArray = new int[]{
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher};
        mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light};

        mCoordinatorTabLayout.setTitle("浏览")
//                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
        return view;
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new PageAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitles));
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(ScanItemFragment.getInstance());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_grid:
                Snackbar.make(view,"sas",Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
