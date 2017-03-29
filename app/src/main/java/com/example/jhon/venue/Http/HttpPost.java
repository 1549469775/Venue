package com.example.jhon.venue.Http;

import android.util.Log;

import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by John on 2017/3/11.
 */

public class HttpPost {

    public static void sendResponse(String url, Object object, final ParseListener listener){
        OkHttpUtils
                .postString()
                .url(url)
                .content(JsonUtil.objectToString(object))
                //提交一个Gson字符串到服务器端，注意：传递JSON的时候，
                // 不要通过addHeader去设置contentType，而使用.mediaType(MediaType.parse("application/json; charset=utf-8")).。
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
                            listener.error(e);
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
//                        listener.onSuccess((String) response);
                        Log.d("xyx", "onResponse: "+response.toString());
                        listener.parseJson(response.toString());
                    }
                });
    }

}
