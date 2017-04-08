package com.example.jhon.venue.Modle;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.TimeLine;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by John on 2017/3/30.
 * 1.提交，包括三种分类，所以要提交三种
 */

public class SubmitModle {
/*
* 创建一个故事
* @param story需要创建的故事内容，包括标题内容，地点等
*
* */
    public void submitItem(Context context, Story story, final JudgeListener listener){
        Log.d("xyxxxxaa","ew"+JsonUtil.objectToString(story));
        OkHttpUtils
                .postString()
                .url(VenueAPI.StoryUrl)
                .addHeader("Authorization",Preference.getApiToken(context))
                .content(JsonUtil.objectToString(story))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xyxxxxaa",JsonUtil.judgeError(response));
                        if (TextUtils.equals(JsonUtil.judgeError(response.toString()),"")){
                            String entity=JsonUtil.getEntity(response.toString());
                            if (!TextUtils.isEmpty(entity)){
//                                BeanUtil.setTimeLine(JsonUtil.parseJsonWithGson(response.toString(), Story.class));
                                listener.onSuccess();
                            }
                        }else {
                            Exception exception=new Exception(JsonUtil.judgeError(response));
                            listener.onError(exception);
                        }
                    }
                });
    }

}
