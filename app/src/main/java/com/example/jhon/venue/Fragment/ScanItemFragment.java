package com.example.jhon.venue.Fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.example.jhon.venue.Adapter.QuickAdapter;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Story;
import com.example.jhon.venue.Bean.Test;
import com.example.jhon.venue.Entity.HotDataServer;
import com.example.jhon.venue.Interface.ParseListener;
import com.example.jhon.venue.Modle.GetModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;
import com.example.jhon.venue.Util.JsonUtil;
import com.example.jhon.venue.Util.TransitionHelper;
import com.example.jhon.venue.View.CommentActivity;
import com.example.jhon.venue.View.Detail_Activity;
import com.example.jhon.venue.View.LoginActivity;
import com.example.jhon.venue.View.MainActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2017/3/24.
 */

public class ScanItemFragment extends Fragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.srl_scan)
    SwipeRefreshLayout srlScan;
    private QuickAdapter quickAdapter;

    private List<Story> list;

    private AlphaAnimation alphaAnimation;
    private int page=1;
    private String lastData="";;

    public static ScanItemFragment getInstance() {
        ScanItemFragment fra = new ScanItemFragment();
        return fra;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scan_recycle_layout, container, false);
        ButterKnife.bind(this, v);
        if (alphaAnimation==null){
            alphaAnimation=new AlphaAnimation(0,1);
            alphaAnimation.setDuration(1000);
        }
        initData();
        initRecycleView();
        initSWL();
        return v;
    }

    private void initSWL() {
        srlScan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 模拟刷新数据
                GetModle.getRefreshData(getContext(), new ParseListener() {
                    @Override
                    public void error(Exception e) {
                        srlScan.setRefreshing(false);
                        ShowUtil.showSnack(recyclerview,e.getMessage());
                    }

                    @Override
                    public void parseJson(String response) {
                        BeanUtil.setList(JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class));
                        srlScan.setRefreshing(false);
                        list.clear();
                        list.addAll(BeanUtil.getList());
                        quickAdapter.notifyDataSetChanged();
                    }
                });
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.addAll(0, HotDataServer.getHotData(2));
//                        quickAdapter.notifyDataSetChanged();
//                        srlScan.setRefreshing(false);
//                    }
//                },2000);
            }
        });
    }

    public void initData(){
        if (list==null){
            list=new ArrayList<>();
        }
        srlScan.setRefreshing(true);
        GetModle.getRecentData(getContext(), new ParseListener() {
            @Override
            public void error(Exception e) {
                srlScan.setRefreshing(false);
                ShowUtil.showSnack(recyclerview,e.getMessage());
            }

            @Override
            public void parseJson(String response) {
                BeanUtil.setList(JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class));
                srlScan.setRefreshing(false);
                list.addAll(BeanUtil.getList());
            }
        });
    }

    private void initRecycleView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        quickAdapter = new QuickAdapter(getContext(),list);
        recyclerview.setAdapter(quickAdapter);
        quickAdapter.bindToRecyclerView(recyclerview);
        quickAdapter.isFirstOnly(false);
        quickAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                };
            }
        });
        quickAdapter.setEmptyView(R.layout.empty_view);
        quickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                img_detail_bg
                switch (view.getId()){
                    case R.id.img_prise:
                        if (TextUtils.equals(((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).getTag().toString()
                                ,"not_prised")){
                            Snackbar.make(view,"已点赞",Snackbar.LENGTH_SHORT).show();
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).setImageResource(R.drawable.button_favo_down);
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).setTag("prised");
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).startAnimation(alphaAnimation);
                        }else {
                            Snackbar.make(view,"取消点赞",Snackbar.LENGTH_SHORT).show();
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).setImageResource(R.drawable.button_favo_up);
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).setTag("not_prised");
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_prise)).startAnimation(alphaAnimation);
                        }
                        break;
                    case R.id.img_collect:
                        if (TextUtils.equals(((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).getTag().toString()
                                ,"not_collected")){
                            Snackbar.make(view,"已收藏",Snackbar.LENGTH_SHORT).show();
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).setImageResource(R.drawable.button_bookmark_down);
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).setTag("collected");
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).startAnimation(alphaAnimation);
                        }else {
                            Snackbar.make(view,"取消收藏",Snackbar.LENGTH_SHORT).show();
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).setImageResource(R.drawable.button_bookmark_up);
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).setTag("not_collected");
                            ((ImageView)adapter.getViewByPosition(position,R.id.img_collect)).startAnimation(alphaAnimation);
                        }
                        break;
                    case R.id.img_talk:
                        Intent intent=new Intent(getContext(), CommentActivity.class);
                        intent.putExtra("storyId",list.get(position).getId());
                        startActivity(intent);
                        break;
                    case R.id.tv_detail_title:
                        Snackbar.make(view,"科技",Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.card:
//                        ((KenBurnsView)adapter.getViewByPosition(position,R.id.imageKenBurns)).pause();
                        EventBus.getDefault().postSticky(list.get(position));
//                        ShowUtil.showToast(getContext(),list.get(position).getTitle());
                        Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true,
                            new Pair(view.findViewById(R.id.imageKenBurns),"img_detail_bg"));
                        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                        startActivity(new Intent(getContext(), Detail_Activity.class), transitionActivityOptions.toBundle());
                        break;

                }
                return false;
            }
        });


        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //TODO 模拟加载数据
                recyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetModle.getMoreData(getContext(), new ParseListener() {
                            @Override
                            public void error(Exception e) {
                                quickAdapter.loadMoreFail();
                                ShowUtil.showSnack(recyclerview,e.getMessage());
                            }

                            @Override
                            public void parseJson(String response) {
                                if (!TextUtils.isEmpty(JsonUtil.getEntity(response))&&!TextUtils.equals(lastData,JsonUtil.getEntity(response))){
                                    ShowUtil.showSnack(recyclerview,"loadMoreComplete");
                                    list.addAll(JsonUtil.stringToList(JsonUtil.getString("list",JsonUtil.getEntity(response.toString())),Story.class));
                                    quickAdapter.notifyDataSetChanged();
                                    quickAdapter.loadMoreComplete();
                                }else {
                                    ShowUtil.showSnack(recyclerview,"loadMoreEnd");
                                    quickAdapter.loadMoreEnd();
                                }
                                lastData=JsonUtil.getEntity(response);
                            }
                        },++page);

//                        List<Test> tests = new ArrayList<>();
//                        for (int i = 0; i < 5; i++) {
//                            Test status = new Test();
//                            status.setName("Chad" + i);
//                            tests.add(status);
//                        }
//                        list.addAll(tests);
//                        quickAdapter.notifyDataSetChanged();
//                        quickAdapter.loadMoreComplete();
                    }
                }, 1000);
            }
        }, recyclerview);
    }
}
