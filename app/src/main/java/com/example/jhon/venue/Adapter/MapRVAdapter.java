package com.example.jhon.venue.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.R;
import com.example.jhon.venue.Util.ScreenUtil;

import java.util.List;

/**
 * Created by John on 2017/6/10.
 */

public class MapRVAdapter extends BaseQuickAdapter<Story, BaseViewHolder> {

    private Context context;

    public MapRVAdapter(Context context,List<Story> list) {
        super(R.layout.map_rv_item, list);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, Story item) {
        helper.setText(R.id.tv_detail_title,item.getTitle())
                .setText(R.id.tv_detail_subtitle,item.getContent())
                .addOnClickListener(R.id.tv_detail_title)
                .addOnClickListener(R.id.card);
        ImageView kenBurnsView=((ImageView)helper.getView(R.id.imageKenBurns));

        Glide.with(context).load(VenueAPI.DownloadFileUrl+item.getCover())
                .placeholder(R.drawable.tesst)
                .centerCrop().into(kenBurnsView);
    }
}
