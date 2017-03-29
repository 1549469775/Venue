package com.example.jhon.venue.Interface;

/**
 * Created by John on 2017/3/11.
 */

public interface ParseListener {

    public void error(Exception e);
    public void parseJson(String response);

}
