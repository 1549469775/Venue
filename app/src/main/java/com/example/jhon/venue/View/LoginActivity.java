package com.example.jhon.venue.View;

import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Operation.AppLogin;
import com.example.jhon.venue.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/3/24.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.root_view)
    RelativeLayout rootView;
    @BindView(R.id.register_nickname)
    EditText registerNickname;
    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.register_confrom)
    EditText registerConfrom;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private boolean isLogin = true, isRegister = false;

    @Override
    public void initView() {
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setupAnim();
    }

    @Override
    public void initOperation() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                isRegister = false;
                etSetVisibility(1);
                if (isLogin) {

                    AppLogin.login();//测试

                    if (checkIsEmpty(1)){
                        //TODO 登陆操作
                    }
                }else {
                    initText();
                }
                isLogin = true;
                break;
            case R.id.btn_register:
                isLogin = false;
                etSetVisibility(0);
                if (isRegister) {

                    AppLogin.loginOut();//测试

                    if (checkIsEmpty(0)) {
                        if (TextUtils.equals(loginPassword.getText().toString(), registerConfrom.getText().toString())) {
                            //TODO 注册操作
                        } else {
                            initText();
                        }
                        isRegister = true;
                        break;
                    }
                }
        }
    }

    private void initText(){
        registerNickname.setText("");
        registerConfrom.setText("");
    }

    public boolean checkIsEmpty(int type){
        if (type==0){
            if (TextUtils.isEmpty(registerNickname.getText().toString())||
                    TextUtils.isEmpty(registerConfrom.getText().toString())||
                    TextUtils.isEmpty(loginEmail.getText().toString())||
                    TextUtils.isEmpty(loginPassword.getText().toString())){
                return false;
            }else
                return true;
        }else {
            if (TextUtils.isEmpty(loginEmail.getText().toString())||
                    TextUtils.isEmpty(loginPassword.getText().toString())){
                return false;
            }else
                return true;
        }
    }

    private void etSetVisibility(int type){
        TransitionManager.beginDelayedTransition(rootView);
        if (type==0){
            registerNickname.setVisibility(View.VISIBLE);
            registerConfrom.setVisibility(View.VISIBLE);
        }else {
            registerNickname.setVisibility(View.GONE);
            registerConfrom.setVisibility(View.GONE);
        }
    }

    private void setupAnim() {
        //TODO 动画
//        final Fade fade = new Fade();
//        fade.setDuration(100);
//        getWindow().setEnterTransition(fade);
//        getWindow().setExitTransition(fade);
//        fade.addListener(new Transition.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//                fade.removeListener(this);
//                int cx = (rootView.getLeft() + rootView.getRight()) / 2;
//                int cy = (rootView.getTop() + rootView.getBottom()) / 2;
//                int finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());
//                Log.d("xyx", finalRadius + "as");
//                Animator anim = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
//                anim.setDuration(500);
//                anim.setInterpolator(new AccelerateInterpolator());
//                anim.start();
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//            }
//
//            @Override
//            public void onTransitionCancel(Transition transition) {
//
//            }
//
//            @Override
//            public void onTransitionPause(Transition transition) {
//
//            }
//
//            @Override
//            public void onTransitionResume(Transition transition) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
//
//        int cx = (rootView.getLeft() + rootView.getRight()) / 2;
//        int cy = (rootView.getTop() + rootView.getBottom()) / 2;
//        int finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());
//        Log.d("xyx", finalRadius + "as");
//        Animator anim = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, finalRadius, 0);
//        anim.setDuration(500);
//        anim.setInterpolator(new AccelerateInterpolator());
//        anim.start();
        super.onBackPressed();
    }
}
