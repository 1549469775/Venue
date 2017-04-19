package com.example.jhon.venue.Entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.jhon.venue.Bean.Story;

/**
 * Created by John on 2017/3/27.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TITLE = 1;
    public static final int USER = 2;
    public static final int CONTENT = 3;
    public static final int COMMENT=4;

    private String content;

    private int itemType;

    public MultipleItem(int itemType,String content) {
        this.itemType = itemType;
        this.content=content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getContent() {
        return content;
    }
}
