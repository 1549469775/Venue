package com.example.jhon.venue.Modle;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.TimeLine;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.sql.Time;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by John on 2017/4/7.
 *
 * 函数
 * 1. 创建时间轴
 * 2. 查询时间轴
 * 3. 修改时间轴
 * 4. 删除时间轴
 */

public class TimeLineModle {

    /*
    * 创建时间轴
    * @param context上下文
    * @param title是时间轴标题
    * @param JudgeListener判断成功与否
    * */

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

                        if (TextUtils.equals(JsonUtil.judgeError(response.toString()),"")){
                            String entity=JsonUtil.getEntity(response.toString());
                            if (!TextUtils.isEmpty(entity)){
                                BeanUtil.setTimeLine(JsonUtil.parseJsonWithGson(JsonUtil.getEntity(response.toString()), TimeLine.class));
                                Log.d("xyxxxxaa",""+BeanUtil.getTimeLine().getId());

                                listener.onSuccess();
                            }
                        }
                        Log.d("xyxTimeLine", "onResponse: "+response.toString());
                    }
                });
    }

    /*
    *
    * 获取一个时间轴上的信息
    *
    * */
    public static void getTimeLineMessage(){

    }

    /*
    *
    * 获取一个时间轴
    *
    * */
    public static void getListTimeLine(Context context, final ParseListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/timelines?")
                .addParams("userId", String.valueOf(Preference.getUserId(context)))
                .addParams("start","0")
                .addParams("end","10")
                .addHeader("Authorization", Preference.getApiToken(context))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (null!=e){
                    listener.error(e);
                }
                ShowUtil.showLog("parseNetworkResponse",e.getMessage());
            }

            @Override
            public void onResponse(Object response, int id) {
                listener.parseJson(response.toString());
//                JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),TimeLine.class);
//                ShowUtil.showLog("parseNetworkResponse",
// JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),TimeLine.class).get(0).getTitle());
            }
        });
    }

}
