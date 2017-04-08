package com.example.jhon.venue.Http;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.VenueAPI;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.FileTransformUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by John on 2017/4/7.
 */

public class HttpFile {

    public static void getPermission(final Context context, final JudgeListener listener){
        OkHttpUtils.get()
                .url(VenueAPI.PermissionFileUrl)
                .addHeader("Authorization", Preference.getApiToken(context))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.equals(JsonUtil.judgeError(response.toString()),"")){
                            String entity=JsonUtil.getEntity(response.toString());
                            if (!TextUtils.isEmpty(entity)){
                                Preference.saveFileApiToen(context,JsonUtil.getString("token",JsonUtil.getEntity(response)));
                                listener.onSuccess();
                            }
                        }else {
                            Exception exception=new Exception(JsonUtil.judgeError(response));
                            listener.onError(exception);
                        }
                    }
                });
    }

    public static void uploadFile(String filePath,Context context, final ParseListener listener){
        File file=new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ShowUtil.showLog("upload",file.exists()+"dssd");
//        ShowUtil.showLog("upload",String.valueOf(FileTransformUtil.getBytes(filePath)));
        OkHttpUtils.put()
                .url(VenueAPI.UploadFileUrl)
                .addHeader("Authorization", Preference.getFileApiToen(context))
                .addHeader("Content-Encoding","UTF-8")
                .addHeader("X-Filename",file.getName())
                .addHeader("Content-Type","image/jpeg")
                .addHeader("Content-Length", String.valueOf(FileTransformUtil.getBytes(filePath).length))
                .requestBody(RequestBody.create(null,FileTransformUtil.getBytes(filePath)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null!=e){
                            listener.error(e);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (TextUtils.equals(JsonUtil.judgeError(response.toString()),"")){
                            String entity=JsonUtil.getEntity(response.toString());
                            if (!TextUtils.isEmpty(entity)){
                                listener.parseJson(entity);
                            }
                        }else {
                            Exception exception=new Exception(JsonUtil.judgeError(response));
                            listener.error(exception);
                        }
                    }
                });
    }

    public static String getFileName(String path){
        File file=new File(path);
        return file.getName();
    }

}
