package com.example.jhon.venue.Bean;

/**
 * Created by John on 2017/3/27.
 */

public class BeanUtil {

    private static User user=null;

    public static User getUser() {
        if (user==null){
            user=new User();
        }
        return user;
    }

    public static void setUser(User user) {
        BeanUtil.user = user;
    }
}
