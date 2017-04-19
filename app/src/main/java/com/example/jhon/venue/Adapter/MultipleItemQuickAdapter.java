package com.example.jhon.venue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Entity.MultipleItem;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ResultListener;
import com.example.jhon.venue.Modle.CommentModle;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.UI.UIProgressDialog;
import com.example.jhon.venue.Util.JsonUtil;
import com.example.jhon.venue.Util.TransitionHelper;
import com.example.jhon.venue.View.CommentActivity;
import com.example.jhon.venue.View.Detail_Activity;
import com.example.jhon.venue.View.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/3/27.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleItemQuickAdapter(Context context, List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TITLE, R.layout.detail_title);
        addItemType(MultipleItem.USER, R.layout.detail_user);
        addItemType(MultipleItem.CONTENT, R.layout.detail_content);
        addItemType(MultipleItem.COMMENT, R.layout.detail_comment);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        Log.d("xyxxyx","convert");
        switch (helper.getItemViewType()) {
            case MultipleItem.TITLE:
                helper.setText(R.id.tv_detail_title,item.getContent());
//                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.USER:
                getUser(helper, Integer.parseInt(item.getContent()));
//                helper.setText(R.id.img_detail_user,item.getContent());
//                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.CONTENT:
                    helper.setText(R.id.tv_detail_content,item.getContent());
//                helper.setImageUrl(R.id.iv, item.getContent());
                break;
            case MultipleItem.COMMENT:
                helper.addOnClickListener(R.id.img_talk);
                setNum(helper);
                break;
        }
    }

    private void getUser(final BaseViewHolder helper, int userId){
        LoginModle.getUserMessageById(context, new JudgeListener() {
            @Override
            public void onSuccess() {
                helper.setText(R.id.tv_detail_name, BeanUtil.getUserMessage().getNickname());
            }

            @Override
            public void onError(Exception e) {
                ShowUtil.showToast(context,e.getMessage());
            }
        },userId);
    }
    private void setNum(final BaseViewHolder helper){
        CommentModle.getCommentCount(context, new ResultListener() {
            @Override
            public void error(Exception e) {
                String num="当前评论数量为:";
                helper.setText(R.id.tv_comment_num,num+"未知");
            }

            @Override
            public void parseJson(Object object) {
                String num="当前评论数量为:";
                helper.setText(R.id.tv_comment_num,num + (CharSequence) object);
            }
        });
    }
}