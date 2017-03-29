package com.example.jhon.venue.Modle;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by John on 2017/3/29.
 */

public class EditorModle {

    private Context context;

    public EditorModle(Context context) {
        this.context = context;
    }

    public void initData(EditText userNickname,
                                EditText userNumber,
                                EditText userQq,
                                EditText userBirthday,
                                EditText userBlog){
        UserMessage userMessage;
        userMessage= BeanUtil.getUserMessage();
        userNickname.setText(userMessage.getNickname());
        userNumber.setText(userMessage.getEmail());
        userQq.setText(userMessage.getQq());
        userBirthday.setText(userMessage.getBirthday());
        userBlog.setText(userMessage.getBlog());

    }

    public void updateUserMessage(UserMessage userMessage, final JudgeListener listener){
        Log.d("Authorization",JsonUtil.objectToString(userMessage));
        OkHttpUtils
                .patch()
                .url(VenueAPI.UserUpdateUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JsonUtil.objectToString(userMessage)))
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
                        Log.d("xyx", "onResponse: "+response.toString());
                        listener.onSuccess();
                    }
                });
    }

}
