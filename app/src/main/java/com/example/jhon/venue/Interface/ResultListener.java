package com.example.jhon.venue.Interface;

/**
 * Created by John on 2017/4/12.
 */

public interface ResultListener {
    public void error(Exception e);
    public void parseJson(Object object);
}
