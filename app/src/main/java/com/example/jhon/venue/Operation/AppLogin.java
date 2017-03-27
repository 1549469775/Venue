package com.example.jhon.venue.Operation;

import com.example.jhon.venue.Bean.BeanUtil;

/**
 * Created by John on 2017/3/27.
 */

public class AppLogin {

    private static boolean isLogin=false;

    public static void login(){
        isLogin=true;
    }

    public static void loginOut(){
        isLogin=false;
    }

    public static boolean isLogin() {
        return isLogin;
    }
}
