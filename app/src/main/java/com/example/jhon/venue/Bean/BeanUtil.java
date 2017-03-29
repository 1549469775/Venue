package com.example.jhon.venue.Bean;

/**
 * Created by John on 2017/3/27.
 */

public class BeanUtil {

    private static UserMessage userMessage=null;

    public static UserMessage getUserMessage() {
        if (userMessage==null){
            userMessage=new UserMessage();
        }
        return userMessage;
    }

    public static void setUserMessage(UserMessage userMessage) {
        BeanUtil.userMessage = userMessage;
    }
}
