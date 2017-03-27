package com.example.jhon.venue.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jhon.venue.Adapter.MultipleItemQuickAdapter;
import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Entity.MultipleItem;
import com.example.jhon.venue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2017/3/27.
 */

public class Detail_Activity extends BaseActivity {

    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private MultipleItemQuickAdapter multipleItemQuickAdapter;
    private List<MultipleItem> datas;

    @Override
    public void initView() {
        setContentView(R.layout.detail_activity);
        setupToolbar(true, true);
        ButterKnife.bind(this);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
    }

    @Override
    public void initOperation() {
        datas = getMultipleItemData();
        multipleItemQuickAdapter = new MultipleItemQuickAdapter(datas);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(multipleItemQuickAdapter);
    }

    private List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i <= 0; i++) {
            list.add(new MultipleItem(MultipleItem.TITLE));
            list.add(new MultipleItem(MultipleItem.USER));
            list.add(new MultipleItem(MultipleItem.CONTENT));
//        }

        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
