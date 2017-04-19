package com.example.jhon.venue.Adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Comment;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Modle.CommentModle;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;

import java.util.List;

/**
 * Created by John on 2017/4/11.
 */

public class CommmentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    private Context context;

    public CommmentAdapter(Context context,int layoutResId, List<Comment> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Comment item) {
        helper.setText(R.id.tv_comment_content,item.getContent());
        helper.setText(R.id.tv_comment_nickname,item.getUsername());
        helper.addOnClickListener(R.id.tv_comment_delete);
    }
}
