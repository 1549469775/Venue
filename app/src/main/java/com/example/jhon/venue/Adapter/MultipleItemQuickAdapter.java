package com.example.jhon.venue.Adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Entity.MultipleItem;
import com.example.jhon.venue.R;

import java.util.List;

/**
 * Created by John on 2017/3/27.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleItemQuickAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TITLE, R.layout.detail_title);
        addItemType(MultipleItem.USER, R.layout.detail_user);
        addItemType(MultipleItem.CONTENT, R.layout.detail_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TITLE:
//                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.USER:
//                helper.setImageUrl(R.id.iv, item.getContent());
                break;
        }
    }
}
