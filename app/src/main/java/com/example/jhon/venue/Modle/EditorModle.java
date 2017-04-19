package com.example.jhon.venue.Modle;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.UI.ShowUtil;
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

    public void initData(int userId,
                         final EditText userNickname,
                         final EditText userNumber,
                         final EditText userQq,
                         final EditText userBirthday,
                         final EditText userBlog){


        LoginModle.getUserMessageById(context, new JudgeListener() {
            @Override
            public void onSuccess() {
                UserMessage userMessage;
                userMessage= BeanUtil.getUserMessage();
                userNickname.setText(userMessage.getNickname());
                userNumber.setText(userMessage.getEmail());
                userQq.setText(userMessage.getQq());
                userBirthday.setText(userMessage.getBirthday());
                userBlog.setText(userMessage.getBlog());
            }

            @Override
            public void onError(Exception e) {
                ShowUtil.showToast(context,e.getMessage());
            }
        },userId);
    }

    public void updateUserMessage(UserMessage userMessage, final JudgeListener listener){
        Log.d("Authorization",JsonUtil.objectToString(userMessage));
        OkHttpUtils
                .patch()
                .url(VenueAPI.UserUpdateUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),""))
                .requestBody(RequestBody.create(MediaType.parse(""),JsonUtil.objectToString(userMessage)))
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
                        if (TextUtils.isEmpty(JsonUtil.judgeError(response.toString()))){
                            listener.onSuccess();
                        }else {
                            Exception exception=new Exception(JsonUtil.judgeError(response.toString()));
                            listener.onError(exception);
                        }
                    }
                });
    }

}
