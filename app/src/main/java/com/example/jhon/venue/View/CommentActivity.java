package com.example.jhon.venue.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jhon.venue.Adapter.CommmentAdapter;
import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.Comment;
import com.example.jhon.venue.Bean.UserMessage;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Interface.ResultListener;
import com.example.jhon.venue.Modle.CommentModle;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.Preference.CommentAction;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.TransitionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/4/11.
 */

public class CommentActivity extends BaseActivity implements CommentAction.GetStoryId {

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.srl_comment)
    SwipeRefreshLayout srlComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.btn_comment)
    Button btnComment;
    private CommentAction action;

    private CommmentAdapter adapter;
    private List<Comment> comments;

//    private String content;
//    private long storyId;

    @Override
    public void initView() {
        setContentView(R.layout.comment_activity);
        setupToolbar(true, true);
        ButterKnife.bind(this);
        if (action == null) {
            action = new CommentAction(this, this);
        }
    }

    @Override
    public void initOperation() {
        initData();
        initSRL();
        initRecycler();
    }

    private void initRecycler(){
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommmentAdapter(this,R.layout.detail_comment_item, comments);
        rvComment.setAdapter(adapter);
        adapter.bindToRecyclerView(rvComment);
        adapter.setEmptyView(new Button(this));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_comment_delete:
                        CommentModle.deleteComment(CommentActivity.this, comments.get(position).getId(), new JudgeListener() {
                            @Override
                            public void onSuccess() {
                                comments.remove(position);
                                adapter.notifyDataSetChanged();
                                ShowUtil.showToast(CommentActivity.this.getApplicationContext(),"DELETE SUCCESS");
                            }

                            @Override
                            public void onError(Exception e) {
                                ShowUtil.showToast(CommentActivity.this.getApplicationContext(),e.getMessage());
                            }
                        });
                        break;
                }
                return false;
            }
        });
    }

    private void initSRL() {
        srlComment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action.getComment(new ResultListener() {
                    @Override
                    public void error(Exception e) {
                        srlComment.setRefreshing(false);
                    }

                    @Override
                    public void parseJson(Object object) {
                        srlComment.setRefreshing(false);
                        comments.clear();
                        comments.addAll((List<Comment>) object);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initData() {
        if (comments==null){
            comments=new ArrayList<>();
        }
        srlComment.setRefreshing(true);
        action.getComment(new ResultListener() {
            @Override
            public void error(Exception e) {
                srlComment.setRefreshing(false);
            }

            @Override
            public void parseJson(Object object) {
                comments.clear();
                comments.addAll((List<Comment>) object);
                adapter.notifyDataSetChanged();
                srlComment.setRefreshing(false);
            }
        });
    }

    @Override
    public long getStoryId() {
        return getIntent().getLongExtra("storyId",-1);
    }

    @Override
    public String getContent() {
        return etComment.getText().toString();
    }

    @OnClick(R.id.btn_comment)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(etComment.getText().toString())){
            if (LoginModle.isLogin()){
                action.addCommentOne(new JudgeListener() {
                    @Override
                    public void onSuccess() {
                        hideSofe();
                        initData();
                        etComment.setText("");
                        ShowUtil.showToast(CommentActivity.this,"评论成功");
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowUtil.showToast(CommentActivity.this,e.getMessage());
                    }
                });
            }else {
                Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivity(new Intent(this, LoginActivity.class), transitionActivityOptions.toBundle());
            }
        }else {
            ShowUtil.showSnack(etComment,"请填写回复内容");
        }
    }

    private void hideSofe(){
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpean=imm.isActive();//判断软键盘状态
        if (isOpean){
            imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);//如果软键盘显示，则隐藏
        }
//        imm.hideSoftInputFromWindow(view,InputMethodManager.HIDE_NOT_ALWAYS);//隐藏软键盘
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);//强制显示软键盘
//        imm.hideSoftInputFromWindow(view.getWindowToken(),0)//强制隐藏软键盘
    }
}
