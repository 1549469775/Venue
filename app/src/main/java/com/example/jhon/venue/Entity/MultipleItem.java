package com.example.jhon.venue.Entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by John on 2017/3/27.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TITLE = 1;
    public static final int USER = 2;
    public static final int CONTENT = 3;

    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
