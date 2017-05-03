package com.example.jhon.venue.Util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.jhon.venue.Interface.JudgeListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by John on 2017/4/20.
 */

public class NormalLoadPicUtil {
    private String uri;
    private ImageView imageView;
    private byte[] picByte;

    public void getPicture(String uri, ImageView imageView, JudgeListener listener){
        this.uri=uri;
        this.imageView=imageView;
        new Thread(runnable).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                if (picByte!=null){
                    Log.i("runnable", "handleMessage");
                    Bitmap bitmap= BitmapFactory.decodeByteArray(picByte,0,picByte.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    };

    Runnable runnable=new Runnable() {
        @Override
        public synchronized void run() {
            try{
                Log.i("runnable", "start");
                URL url=new URL(uri);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(1000);
                if (connection.getResponseCode()==200){
                    InputStream inputStream=connection.getInputStream();
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] bytes=new byte[1024];
                    int length=-1;
                    while ((length=inputStream.read(bytes))!=-1){
                        bos.write(bytes,0,length);
                    }
                    picByte=bos.toByteArray();
                    bos.close();
                    inputStream.close();

                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
