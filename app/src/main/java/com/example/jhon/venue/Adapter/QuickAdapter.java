package com.example.jhon.venue.Adapter;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.Test;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.R;
import com.example.jhon.venue.Util.ScreenUtil;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.List;

/**
 * Created by John on 2017/3/24.
 */

public class QuickAdapter extends BaseQuickAdapter<Story, BaseViewHolder> {

    private Context context;

    public QuickAdapter(Context context,List<Story> list) {
        super(R.layout.text, list);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, Story item) {
        helper.setText(R.id.tv_detail_title,item.getTitle())
                .setText(R.id.tv_detail_subtitle,item.getContent())
        .addOnClickListener(R.id.tv_detail_title)
        .addOnClickListener(R.id.card)
        .addOnClickListener(R.id.img_prise)
        .addOnClickListener(R.id.img_collect)
        .addOnClickListener(R.id.img_talk);
        ImageView kenBurnsView=((ImageView)helper.getView(R.id.imageKenBurns));

        Glide.with(context).load(VenueAPI.DownloadFileUrl+item.getCover())
                .placeholder(R.drawable.tesst).override(ScreenUtil.getScreenWidth(context),ScreenUtil.getScreenHeight(context)/3).centerCrop().into(kenBurnsView);
//        kenBurnsView.setImageResource(R.drawable.material_design_1);
    }
}
