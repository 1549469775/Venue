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
import android.widget.Button;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.EyeOn;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Entity.MultipleItem;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ResultListener;
import com.example.jhon.venue.Modle.CommentModle;
import com.example.jhon.venue.Modle.GetModle;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.UI.UIProgressDialog;
import com.example.jhon.venue.Util.JsonUtil;
import com.example.jhon.venue.Util.TransitionHelper;
import com.example.jhon.venue.View.CommentActivity;
import com.example.jhon.venue.View.Detail_Activity;
import com.example.jhon.venue.View.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by John on 2017/3/27.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private Context context;
    private long followerId;
    private String username;

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
    protected void convert(final BaseViewHolder helper, MultipleItem item) {
        Log.d("xyxxyx","convert");
        switch (helper.getItemViewType()) {
            case MultipleItem.TITLE:
                helper.setText(R.id.tv_detail_title,item.getContent());
//                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.USER:
                getUser(helper, Integer.parseInt(item.getContent()));
                helper.getView(R.id.btn_follow).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        if (((Button)helper.getView(R.id.btn_follow)).getText().equals("关注")) {
                            eyeOn(new JudgeListener() {
                                @Override
                                public void onSuccess() {
                                    ShowUtil.showSnack(v,"已关注");
                                    helper.setText(R.id.btn_follow,"已关注");
                                    getEyeOn(context, new ResultListener() {
                                        @Override
                                        public void error(Exception e) {
                                            ShowUtil.showSnack(v,e.getMessage());
                                            helper.setText(R.id.tv_eyeon,"获取失败");
                                        }

                                        @Override
                                        public void parseJson(Object object) {
                                            List<EyeOn> list= (List<EyeOn>) object;
                                            if (list!=null){
                                                StringBuffer sb=new StringBuffer();
                                                for (EyeOn eyeOn:list){
                                                    sb.append(eyeOn.getUsername());
                                                    sb.append(",");
                                                }
                                                helper.setText(R.id.tv_eyeon,sb.toString());
                                                notifyDataSetChanged();
                                            }else {
                                                helper.setText(R.id.tv_eyeon,"尚无");
                                            }

                                        }
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    ShowUtil.showSnack(v,e.getMessage());
                                }
                            });
                        }else {
                            notEyeOn(new JudgeListener() {
                                @Override
                                public void onSuccess() {
                                    ShowUtil.showSnack(v,"已取消关注");
                                    helper.setText(R.id.btn_follow,"关注");
                                    getEyeOn(context, new ResultListener() {
                                        @Override
                                        public void error(Exception e) {
                                            ShowUtil.showSnack(v,e.getMessage());
                                            helper.setText(R.id.tv_eyeon,"获取失败");
                                        }

                                        @Override
                                        public void parseJson(Object object) {
                                            List<EyeOn> list= (List<EyeOn>) object;
                                            if (list!=null){
                                                StringBuffer sb=new StringBuffer();
                                                for (EyeOn eyeOn:list){
                                                    sb.append(eyeOn.getUsername());
                                                    sb.append(",");
                                                }
                                                helper.setText(R.id.tv_eyeon,sb.toString());
                                                notifyDataSetChanged();
                                            }else {
                                                helper.setText(R.id.tv_eyeon,"尚无");
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    ShowUtil.showSnack(v,e.getMessage());
                                }
                            });
                        }

                    }
                });
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
                followerId=BeanUtil.getUserMessage().getId();
                username=BeanUtil.getUserMessage().getUsername();
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

    private void eyeOn(final JudgeListener listener){
        String data="{"+"\""+"userId"+"\""+":"+ Preference.getUserId(context)+","+"\""+"followerId"+"\""+":"+"\""+followerId+"\""+"}";
        ShowUtil.showLog("eyeOn",data);
        OkHttpUtils.postString()
                .url("http://119.23.142.44/api/user/follow")
                .addHeader("Authorization", Preference.getApiToken(context))
                .content(data)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
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
                        if (JsonUtil.judgeError(response)!=""){
                            listener.onSuccess();
                        }else {
                            listener.onError(new Exception(JsonUtil.judgeError(response)));
                        }
                    }
                });
    }

    private void notEyeOn(final JudgeListener listener){
        String data="{"+"\""+"userId"+"\""+":"+ Preference.getUserId(context)+","+"\""+"followerId"+"\""+":"+"\""+followerId+"\""+"}";
        ShowUtil.showLog("eyeOn",data);
        OkHttpUtils.postString()
                .url("http://119.23.142.44/api/user/unfollow")
                .addHeader("Authorization", Preference.getApiToken(context))
                .content(data)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
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
                        if (JsonUtil.judgeError(response)!=""){
                            listener.onSuccess();
                        }else {
                            listener.onError(new Exception(JsonUtil.judgeError(response)));
                        }
                    }
                });
    }

    private void getEyeOn(Context context, final ResultListener listener){
        Log.d("getEyeOn", "onStart ");
        OkHttpUtils.get()
                .url("http://119.23.142.44/api/user/follower/list?")
                .addHeader("Authorization", Preference.getApiToken(context))
                .addParams("username", username)
                .addParams("start", String.valueOf(0))
                .addParams("end", String.valueOf(10))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null){
                    listener.error(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("getEyeOn", "onResponse: "+response.toString());
                if (JsonUtil.judgeError(response)!=""){
                    listener.parseJson(JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),EyeOn.class));
                }else {
                    listener.error(new Exception(JsonUtil.judgeError(response)));
                }
            }
        });
    }
}