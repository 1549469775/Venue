package com.example.jhon.venue.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.jhon.venue.UI.ShowUtil;

/**
 * Created by John on 2017/4/10.
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if (info!=null){
                ShowUtil.showToast(context.getApplicationContext(),"Network Is Available");
                return info.isAvailable();
            }else {
                ShowUtil.showToast(context.getApplicationContext(),"Network Is NotAvailable,please check it");
            }
        }
        return false;
    }


}
