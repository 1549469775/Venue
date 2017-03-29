package com.example.jhon.venue.Http;

import android.util.Log;

import com.example.jhon.venue.Interface.ParseListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by John on 2017/3/11.
 */

public class HttpGet {

    public static void getResponse(String url, final ParseListener listener){
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .connTimeOut(2000)
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("OKHttp",e.getMessage());
                        listener.error(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("OKHttp",response);
                        listener.parseJson(response);
                    }
                });
    }
}
