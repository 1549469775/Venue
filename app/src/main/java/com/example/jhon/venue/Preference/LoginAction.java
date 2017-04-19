package com.example.jhon.venue.Preference;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Entity.MessageEvent;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.UI.UIProgressDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by John on 2017/3/28.
 */

public class LoginAction {

    private Context context;
    private LoginModle loginModle;

    public LoginAction(Context context) {
        this.context = context;
        loginModle=new LoginModle(context);
    }

    public void login(String username, String password, final JudgeListener listener){
        UIProgressDialog.showProgress(context,"登陆中");
        loginModle.loginOperation(username, password, new JudgeListener() {
            @Override
            public void onSuccess() {
                UIProgressDialog.closeProgress();//放在里面会报错
                //更新UI
                loginModle.getUserMessageByName(context, new JudgeListener() {
                    @Override
                    public void onSuccess() {
                        //发数据给person更新UI
                        EventBus.getDefault().post(new MessageEvent(BeanUtil.getUserMessage().getNickname(),"null"));
                        listener.onSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        listener.onError(e);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                UIProgressDialog.closeProgress();
                listener.onError(e);
            }
        });
    }

    public void register(String nickname,String username, String password, final JudgeListener listener){
        UIProgressDialog.showProgress(context,"注册中");
        loginModle.registerOperation(nickname, username, password, new JudgeListener() {
            @Override
            public void onSuccess() {
                UIProgressDialog.closeProgress();
                listener.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                UIProgressDialog.closeProgress();
                listener.onError(e);
            }
        });
    }
}
