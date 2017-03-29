package com.example.jhon.venue.Adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.Test;
import com.example.jhon.venue.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.List;

/**
 * Created by John on 2017/3/24.
 */

public class QuickAdapter extends BaseQuickAdapter<Test, BaseViewHolder> {

    private Context context;

    public QuickAdapter(Context context,List<Test> list) {
        super(R.layout.text, list);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, Test item) {
        helper.setText(R.id.tv_detail_title,item.getName())
        .addOnClickListener(R.id.tv_detail_title)
        .addOnClickListener(R.id.card)
        .addOnClickListener(R.id.img_prise)
        .addOnClickListener(R.id.img_collect)
        .addOnClickListener(R.id.img_talk);
        KenBurnsView kenBurnsView=((KenBurnsView)helper.getView(R.id.imageKenBurns));
        Glide.with(context).load(R.drawable.tesst).centerCrop().into(kenBurnsView);
//        kenBurnsView.setImageResource(R.drawable.material_design_1);
    }

}
