package com.example.jhon.venue.Modle;

import android.content.Context;
import android.util.Log;

import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by John on 2017/3/30.
 */

public class SubmitModle {
//TODO 成功
    public static void createTimeLine(Context context, String title, final JudgeListener listener){
        String json="{ "+"\"userId\": "+ Preference.getUserId(context)+" , "+"\"title\": "+"\""+title+"\""+" }";
        Log.d("xyxTimeLine", json);
        Log.d("xyxTimeLine", Preference.getApiToken(context));
        OkHttpUtils
                .postString()
                .url(VenueAPI.TimeLineUrl)
                .content(json)
                .addHeader("Authorization", Preference.getApiToken(context))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        Log.d("xyx", "onResponse: "+response.body().string());
//                        listener.onSuccess(response.body().string());
                        return response.body().string();//从这里返回待OnResponse
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
//                        listener.onSuccess((String) response);
                        Log.d("xyxTimeLine", "onResponse: "+response.toString());
                        listener.onSuccess();
                    }
                });
    }

}
