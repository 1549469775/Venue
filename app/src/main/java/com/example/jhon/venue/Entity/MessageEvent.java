package com.example.jhon.venue.Entity;

/**
 * Created by John on 2017/3/28.
 */

public class MessageEvent {

    public final String nickname;
    public final String imagePath;
    public MessageEvent(String nickname,String imagePath) {
        this.nickname = nickname;
        this.imagePath=imagePath;
    }

}
