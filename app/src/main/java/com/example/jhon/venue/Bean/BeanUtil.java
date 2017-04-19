package com.example.jhon.venue.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/3/27.
 */

public class BeanUtil {

    private static UserMessage userMessage=null;
    private static TimeLine timeLine=null;
    private static List<Story> list=null;

    public static UserMessage getUserMessage() {
        if (userMessage==null){
            userMessage=new UserMessage();
        }
        return userMessage;
    }

    public static void setUserMessage(UserMessage userMessage) {
        BeanUtil.userMessage = userMessage;
    }

    public static TimeLine getTimeLine() {
        if (timeLine==null){
            timeLine=new TimeLine();
        }
        return timeLine;
    }

    public static void setTimeLine(TimeLine timeLine) {
        BeanUtil.timeLine = timeLine;
    }

    public static List<Story> getList() {
        if (list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    public static void setList(List<Story> list) {
        BeanUtil.list = list;
    }
}
