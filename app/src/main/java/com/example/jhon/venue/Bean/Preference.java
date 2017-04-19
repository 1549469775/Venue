package com.example.jhon.venue.Bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 2017/3/28.
 */

public class Preference {

    private static SharedPreferences sharedPreferences;

    private static String apiToken;
    private static String fileApiToken;
    private static String type;
    private static Long expiration;
    private static boolean isAutoLogin;
    private static String username;
    private static int userId;
    private static long timelineId;

    public static void save(Context context,String apiToken, String type, Long expiration,String username,int userId){
        Preference.apiToken=apiToken;
        Preference.type=type;
        Preference.expiration=expiration;

        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString("apiToken",apiToken);
        edit.putString("type",type);
        edit.putLong("expiration",expiration);
        edit.putString("username",username);
        edit.putInt("userId",userId);
        edit.commit();
    }

    public static void saveAutoLogin(Context context,boolean isAutoLogin){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putBoolean("isAutoLogin",isAutoLogin);
        edit.commit();
    }

    public static boolean getAutoLogin(Context context){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        isAutoLogin=sharedPreferences.getBoolean("isAutoLogin",false);
        return isAutoLogin;
    }

    public static String getApiToken(Context context) {
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        }
        apiToken=sharedPreferences.getString("apiToken","");
        return apiToken;
    }

    public static String getType(Context context) {
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        }
        type=sharedPreferences.getString("type","");
        return type;
    }

    public static Long getExpiration(Context context) {
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        }
        expiration=sharedPreferences.getLong("expiration",0);
        return expiration;
    }

    public static String getUsername(Context context) {
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        }
        username=sharedPreferences.getString("username","");
        return username;
    }

    public static Integer getUserId(Context context){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        userId=sharedPreferences.getInt("userId",userId);
        return userId;
    }

    public static void saveFileApiToen(Context context,String fileApiToken){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString("fileApiToken",fileApiToken);
        edit.commit();
    }

    public static String getFileApiToen(Context context){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        fileApiToken=sharedPreferences.getString("fileApiToken","");
        return fileApiToken;
    }

    public static void saveTimeLineId(Context context,long timeLineId){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putLong("timeLineId",timeLineId);
        edit.commit();
    }

    public static long getTimeLineId(Context context){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        timelineId=sharedPreferences.getLong("timeLineId",-1);
        return timelineId;
    }
}
