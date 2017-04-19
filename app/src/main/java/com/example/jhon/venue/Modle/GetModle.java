package com.example.jhon.venue.Modle;

import android.content.Context;
import android.util.Log;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by John on 2017/4/5.
 */

public class GetModle {

    public static void getInitData(Context context, final ParseListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/stories/get?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start","0")
                .addParams("end","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                listener.parseJson(response.toString());
                Log.d("getInitData", "onResponse: "+response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class);
            }
        });
    }

    public static void getRecentData(Context context, final ParseListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/recent?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("start","0")
                .addParams("end","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                listener.parseJson(response.toString());
                Log.d("getInitData", "onResponse: "+response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class);
            }
        });
    }

    public static void getRefreshData(Context context, final ParseListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/recent?")
                .addHeader("Authorization", Preference.getApiToken(context))
//                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start","0")
                .addParams("end","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                listener.parseJson(response.toString());
                Log.d("getInitData", "onResponse: "+response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class);
            }
        });
    }

    public static void getMoreData(Context context, final ParseListener listener,int page){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/recent?")
                .addHeader("Authorization", Preference.getApiToken(context))
//                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start", String.valueOf(0+page*10))
                .addParams("end", String.valueOf((page+1)*10-1))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                listener.parseJson(response.toString());
                Log.d("getMoreData", "onResponse: "+response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class);
            }
        });
    }

    public static void getLocation(Context context, final ParseListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/recent?")
                .addHeader("Authorization", Preference.getApiToken(context))
//                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start", String.valueOf(0))
                .addParams("end", String.valueOf(10))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                listener.parseJson(response.toString());
                Log.d("getLocation", "onResponse: "+response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class);
            }
        });
    }

}
