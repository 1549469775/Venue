package com.example.jhon.venue.Preference;

import android.content.Context;

import com.example.jhon.venue.Bean.Comment;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ResultListener;
import com.example.jhon.venue.Modle.CommentModle;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.UI.UIProgressDialog;

import java.util.List;

/**
 * Created by John on 2017/4/12.
 */

public class CommentAction {

    private Context context;
    private CommentModle modle;
    private GetStoryId getStoryId;

    public CommentAction(Context context,GetStoryId getStoryId) {
        this.context = context;
        modle=new CommentModle();
        this.getStoryId=getStoryId;
    }

    public interface GetStoryId{
        long getStoryId();
        String getContent();
    }

    public void addCommentOne(final JudgeListener listener){
        UIProgressDialog.showProgress(context,"评论上传中");
        modle.addCommentOne(context, getStoryId.getContent(), getStoryId.getStoryId(), new JudgeListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
                UIProgressDialog.closeProgress();
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
                UIProgressDialog.closeProgress();
            }
        });
    }

    public void getComment(final ResultListener listener){
        modle.getComment(context, getStoryId.getStoryId(), new ResultListener() {
            @Override
            public void error(Exception e) {
                listener.error(e);
                ShowUtil.showToast(context,e.getMessage());
            }

            @Override
            public void parseJson(Object object) {
                listener.parseJson(object);
            }
        });
    }
}
