package com.example.jhon.venue.Modle;

import android.content.Context;
import android.util.Log;

import com.example.jhon.venue.Bean.Comment;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ResultListener;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by John on 2017/4/12.
 */

public class CommentModle {

    public void addCommentOne(Context context, String content, long storyId, final JudgeListener judgeListener){
        String data="{"+"\""+"storyId"+"\""+":"+storyId+","+"\""+"content"+"\""+":"+"\""+content+"\""+"}";

        OkHttpUtils.postString()
                .url("http://119.23.142.44/api/story/comment")
                .addHeader("Authorization", Preference.getApiToken(context))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(data)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            judgeListener.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (JsonUtil.judgeError(response).equals("")){
                            judgeListener.onSuccess();
                        }else {
                            judgeListener.onError(new Exception(JsonUtil.judgeError(response)));
                        }
                    }
                });
    }

    public static void getCommentCount(Context context, final ResultListener listener){
        Log.d("Stringresponse","gaodedefaf");
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/comment/count")
                .addHeader("Authorization", Preference.getApiToken(context))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e)
                        listener.error(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("Stringresponse",response);
                        Log.d("Stringresponse",JsonUtil.getEntity(response));
                        Log.d("Stringresponse",JsonUtil.getString("count",JsonUtil.getEntity(response)));
                        listener.parseJson(JsonUtil.getString("count",JsonUtil.getEntity(response)));
                    }
                });
    }

    public void getComment( Context context, long storyId, final ResultListener listener){
        ShowUtil.showLog("getComment","action");
        ShowUtil.showLog("getComment","storyId"+storyId);
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/story/comment/list?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("storyId", String.valueOf(storyId))
                .addParams("start", String.valueOf(0))
                .addParams("end", String.valueOf(10))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.error(e);
                            ShowUtil.showLog("getComment","error");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (JsonUtil.judgeError(response).equals("")){
                            listener.parseJson(JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response)), Comment.class));
                            ShowUtil.showLog("getComment","response");
                        }else {
                            ShowUtil.showLog("getComment","error"+JsonUtil.judgeError(response));
                        }

                    }
                });
    }


    public static void deleteComment(Context context, long commentId, final JudgeListener listener){
        OkHttpUtils.delete()
                .url("http://119.23.142.44/api/story/comment/delete?id="+commentId)
                .addHeader("Authorization", Preference.getApiToken(context))
//                .requestBody(RequestBody.create(MediaType.parse("id"),String.valueOf(commentId)))
//                .addParams("id", String.valueOf(commentId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.onError(e);
                            ShowUtil.showLog("getComment","error");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (JsonUtil.judgeError(response).equals("")){
                            listener.onSuccess();
                            ShowUtil.showLog("getComment","response");
                        }else {
                            Exception e=new Exception(JsonUtil.judgeError(response));
                            listener.onError(e);
                        }

                    }
                });
    }

}
