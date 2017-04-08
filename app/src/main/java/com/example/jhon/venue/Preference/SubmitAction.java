package com.example.jhon.venue.Preference;

import android.content.Context;

import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Http.HttpFile;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Modle.SubmitModle;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;

/**
 * Created by John on 2017/4/5.
 */

public class SubmitAction {

    private Context context;
    private SubmitModle submitModle;
    private SubmitListener submitListener;

    public SubmitAction(Context context,SubmitListener listener) {
        this.context = context;
        submitModle=new SubmitModle();
        submitListener=listener;
    }

    public interface SubmitListener{
        Story getStory();
    }

    public void submit(final JudgeListener listener){
        submitModle.submitItem(context, submitListener.getStory(), new JudgeListener() {
                    @Override
                    public void onSuccess() {
                        listener.onSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        listener.onError(e);
                    }
                });

    }

    public void upLoad(String path, final ParseListener listener){
        HttpFile.uploadFile(path, context, new ParseListener() {
            @Override
            public void error(Exception e) {
                listener.error(e);
            }

            @Override
            public void parseJson(String response) {
                listener.parseJson(response);
            }
        });
    }
}
