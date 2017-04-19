package com.example.jhon.venue.Modle;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.User;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Entity.MessageEvent;
import com.example.jhon.venue.Http.HttpPost;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by John on 2017/3/28.
 */

public class LoginModle {

    private static boolean isLogin=false;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        LoginModle.isLogin = isLogin;
    }

    private Context context;

    public LoginModle(Context context) {
        this.context = context;
    }

    //登陆操作
    public void loginOperation(String username, String password, final JudgeListener listener){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        HttpPost.sendResponse(VenueAPI.loginUrl, user, new ParseListener() {
            @Override
            public void error(Exception e) {
                isLogin=false;
                listener.onError(e);
            }

            @Override
            public void parseJson(String response) {
                if (TextUtils.equals(JsonUtil.judgeError(response),"")){
                    isLogin=true;
                    String entity=JsonUtil.getEntity(response);
                    if (!TextUtils.isEmpty(entity)){
                        Preference.save(context,
                                JsonUtil.parseJsonWithGson(entity,User.class).getToken(),
                                JsonUtil.parseJsonWithGson(entity,User.class).getType(),
                                JsonUtil.parseJsonWithGson(entity,User.class).getExpiration(),
                                JsonUtil.parseJsonWithGson(entity,User.class).getUsername(),
                                JsonUtil.parseJsonWithGson(entity,User.class).getUserId());
                        listener.onSuccess();
                    }
                }else{
                    isLogin=false;
                    Exception exception=new Exception(JsonUtil.judgeError(response));
                    listener.onError(exception);
                }
            }
        });
    }
//注册操作
    public void registerOperation(String nickname,String username, String password, final JudgeListener listener){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        HttpPost.sendResponse(VenueAPI.registerUrl, user, new ParseListener() {
            @Override
            public void error(Exception e) {
                listener.onError(e);
            }

            @Override
            public void parseJson(String response) {
                if (TextUtils.equals(JsonUtil.judgeError(response),"")){
                    listener.onSuccess();
                }else {
                    Exception exception=new Exception(JsonUtil.judgeError(response));
                    listener.onError(exception);
                }
            }
        });
    }

    //登陆操作
    public static void loginByApiToken(Context context,final JudgeListener listener){
        OkHttpUtils.get()
                .url(VenueAPI.UserUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("type","byUsername")
                .addParams("value", Preference.getUsername(context))
                .build()
                .connTimeOut(2000)
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        isLogin=false;
                        Log.e("OKHttpAAA",e.getMessage());
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("OKHttpAAA",response);
                        String entity=JsonUtil.getEntity(response);
                        if (!TextUtils.isEmpty(entity)){
                            isLogin=true;
                            BeanUtil.setUserMessage(JsonUtil.parseJsonWithGson(entity, UserMessage.class));
                            //发数据给person更新UI
                            EventBus.getDefault().post(new MessageEvent(BeanUtil.getUserMessage().getNickname(),"null"));
                            listener.onSuccess();;
                        }else {
                            isLogin=false;
                            Exception exception=new Exception(JsonUtil.judgeError(response));
                            listener.onError(exception);
                        }
                    }
                });
    }

    public void getUserMessageByName(Context context,final JudgeListener listener){
        OkHttpUtils.get()
                .url(VenueAPI.UserUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("type","byUsername")
                .addParams("value",Preference.getUsername(context))
                .build()
                .connTimeOut(2000)
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("OKHttpAAA",e.getMessage());
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("OKHttpAAA",response);
                        String entity=JsonUtil.getEntity(response);
                        if (!TextUtils.isEmpty(entity)){
                            BeanUtil.setUserMessage(JsonUtil.parseJsonWithGson(entity, UserMessage.class));
                            listener.onSuccess();;
                        }
                        listener.onSuccess();
                    }
                });
    }

    public static void getUserMessageById(Context context,final JudgeListener listener,int userId){
        OkHttpUtils.get()
                .url(VenueAPI.UserUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("type","byId")
                .addParams("value", String.valueOf(userId))
                .build()
                .connTimeOut(2000)
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("OKHttpAAA",e.getMessage());
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("OKHttpAAA",response);
                        String entity=JsonUtil.getEntity(response);
                        if (!TextUtils.isEmpty(entity)){
                            BeanUtil.setUserMessage(JsonUtil.parseJsonWithGson(entity, UserMessage.class));
                            listener.onSuccess();;
                        }
                        listener.onSuccess();
                    }
                });
    }

    public static void getTimeLineRecent(final Context context, final JudgeListener listener){
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/timeline?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("id", String.valueOf(Preference.getTimeLineId(context)))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (JsonUtil.judgeError(response.toString()).equals("")){
//                            JsonUtil.getString("title",)
                        }else {
                            ShowUtil.showToast(context,"出错了");
                        }
                    }
                });
    }
}
