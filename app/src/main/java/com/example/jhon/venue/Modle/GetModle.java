package com.example.jhon.venue.Modle;

import android.content.Context;
import android.util.Log;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by John on 2017/4/5.
 */

public class GetModle {

    public static void getInitData(Context context){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/stories/get?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start","0")
                .addParams("end","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("getInitData", "onResponse: "+response.toString());
            }
        });
    }

}
