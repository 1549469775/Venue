package com.example.jhon.venue.Preference;

import android.content.Context;
import android.widget.EditText;

import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Modle.EditorModle;

/**
 * Created by John on 2017/3/29.
 */

public class EditorAction {

    private Context context;
    private EditorModle editorModle;

    public EditorAction(Context context) {
        this.context = context;
        editorModle=new EditorModle(context);
    }

    public void initData(EditText userNickname,
                         EditText userNumber,
                         EditText userQq,
                         EditText userBirthday,
                         EditText userBlog){
        editorModle.initData(userNickname,
                userNumber,
                userQq,
                userBirthday,
                userBlog);
    }

    public void updateData(String userNickname,
                           String userNumber,
                           String userQq,
                           String userBirthday,
                           String userBlog, final JudgeListener listener) {

        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(Preference.getUserId(context));
        userMessage.setGender("male");
        userMessage.setNickname(userNickname);
        userMessage.setEmail(userNumber);
        userMessage.setQq(userQq);
        userMessage.setBirthday(userBirthday);
        userMessage.setBlog(userBlog);
        editorModle.updateUserMessage(userMessage, new JudgeListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        });
    }
}
