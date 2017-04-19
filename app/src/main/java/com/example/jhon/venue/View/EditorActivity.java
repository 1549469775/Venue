package com.example.jhon.venue.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.BeanUtil;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Entity.MessageEvent;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.Preference.EditorAction;
import com.example.jhon.venue.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/3/27.
 */

public class EditorActivity extends BaseActivity {

    @BindView(R.id.btn_loginout)
    Button btnLoginout;
    @BindView(R.id.user_nickname)
    EditText userNickname;
    @BindView(R.id.user_number)
    EditText userNumber;
    @BindView(R.id.user_qq)
    EditText userQq;
    @BindView(R.id.user_birthday)
    EditText userBirthday;
    @BindView(R.id.user_blog)
    EditText userBlog;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private EditorAction action = new EditorAction(this);

    @Override
    public void initView() {
        setContentView(R.layout.editor_activity);
        ButterKnife.bind(this);
        setupToolbar(true, true);
        getSupportActionBar().setTitle("编辑资料");

        action.initData(Preference.getUserId(this),userNickname, userNumber, userQq, userBirthday, userBlog);

        //TODO 修改个人信息
    }

    @Override
    public void initOperation() {
        userNickname.setEnabled(false);
        userNumber.setEnabled(false);
        userQq.setEnabled(false);
        userBirthday.setEnabled(false);
        userBlog.setEnabled(false);
    }

    @OnClick(R.id.btn_loginout)
    public void onViewClicked() {
        LoginModle.setIsLogin(false);
        EventBus.getDefault().post(new MessageEvent("Venue", ""));
        finish();
    }
    @OnClick({R.id.btn_update, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                action.updateData(userNickname.getText().toString(),
                        userNumber.getText().toString(),
                        userQq.getText().toString(),
                        userBirthday.getText().toString(),
                        userBlog.getText().toString(),
                        new JudgeListener() {
                            @Override
                            public void onSuccess() {
                                EventBus.getDefault().post(new MessageEvent(userNickname.getText().toString(), ""));
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btn_cancel:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isEnable=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_grid:
                if (!isEnable){
                    userNickname.setEnabled(true);
                    userNumber.setEnabled(true);
                    userQq.setEnabled(true);
                    userBirthday.setEnabled(true);
                    userBlog.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "启用编辑", Toast.LENGTH_SHORT).show();
                    isEnable=!isEnable;
                }else {
                    userNickname.setEnabled(false);
                    userNumber.setEnabled(false);
                    userQq.setEnabled(false);
                    userBirthday.setEnabled(false);
                    userBlog.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "关闭编辑", Toast.LENGTH_SHORT).show();
                    isEnable=!isEnable;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
