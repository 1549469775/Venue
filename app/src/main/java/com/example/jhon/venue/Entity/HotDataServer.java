package com.example.jhon.venue.Entity;

import com.example.jhon.venue.Bean.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/3/29.
 */

public class HotDataServer {

    private List<HotData> hotDatas;

    public static List<Test> getHotData(int lenth) {
        List<Test> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Test status = new Test();
            status.setName("时间都去哪了" + i);
            list.add(status);
        }
        return list;
    }

    public List<Test> getRefreshData(int page){
        List<Test> list = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            Test status = new Test();
            status.setName("时间都去哪sad了" + i);
            list.add(status);
        }
        return list;
    }

    public List<HotData> getLoadMoreData(int page){
        List<HotData> list = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            HotData status = new HotData();
            list.add(status);
        }
        return list;
    }

}
