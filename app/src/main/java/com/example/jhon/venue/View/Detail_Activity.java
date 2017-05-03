package com.example.jhon.venue.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jhon.venue.Adapter.MultipleItemQuickAdapter;
import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Entity.MultipleItem;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.ScreenUtil;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.img_detail_bg)
    KenBurnsView imgDetailBg;

    private MultipleItemQuickAdapter multipleItemQuickAdapter;
    private List<MultipleItem> datas;
    private Story story;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.detail_activity);
        setupToolbar(true, true);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        imgDetailBg.pause();//暂停了图片的动画
//        imgDetailBg.animate().start();
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
    }

    @Override
    public void initOperation() {
        datas = getMultipleItemData();
        Log.d("xyxxyx","initOperation");
        multipleItemQuickAdapter = new MultipleItemQuickAdapter(this,datas);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(multipleItemQuickAdapter);
        multipleItemQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.img_talk){
                    Intent intent=new Intent(Detail_Activity.this, CommentActivity.class);
                    intent.putExtra("storyId",story.getId());
                    startActivity(intent);
                    return true;
                }else
                    return false;
            }
        });
    }

    private List<MultipleItem> getMultipleItemData() {
        List<MultipleItem> list = new ArrayList<>();
//        for (int i = 0; i <= 0; i++) {
        list.add(new MultipleItem(MultipleItem.TITLE,story.getTitle()));
        list.add(new MultipleItem(MultipleItem.USER,story.getOwnerId()+""));
        list.add(new MultipleItem(MultipleItem.CONTENT,story.getContent()));
        list.add(new MultipleItem(MultipleItem.COMMENT,""));
//        }

        return list;
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventBus(Story story) {
        //测试成功
//        ShowUtil.showToast(this,story.getTitle());
        this.story=story;
        Glide.with(this).load(VenueAPI.DownloadFileUrl+story.getCover())
                .placeholder(R.drawable.tesst).override(
                        ScreenUtil.getScreenWidth(this),
                ScreenUtil.getScreenHeight(this)/3).centerCrop().into(imgDetailBg);
    }
}
