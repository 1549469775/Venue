package com.example.jhon.venue.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jhon.venue.BaseActivity;
import com.example.jhon.venue.Bean.Preference;
import com.example.jhon.venue.Fragment.MapFragment;
import com.example.jhon.venue.Fragment.PersonFragment;
import com.example.jhon.venue.Fragment.ScanFragment;
import com.example.jhon.venue.Interface.JudgeListener;
import com.example.jhon.venue.Modle.LoginModle;
import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.UIProgressDialog;
import com.example.jhon.venue.Util.TransitionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private FragmentTransaction fragmentTransaction = null;

    private MapFragment mapFragment = null;
    private ScanFragment scanFragment = null;
    private PersonFragment personFragment=null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setupToolbar(false,true);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment();
        fragmentTransaction.show(mapFragment);
        fragmentTransaction.commit();

        //BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Preference.getAutoLogin(this)){
            LoginModle.loginByApiToken(this, new JudgeListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(),"已登陆",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getApplicationContext(),"登陆失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void initOperation() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            hideFragment();
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    if (mapFragment == null) {
//                        mapFragment = MapFragment.newInstance();
//                        fragmentTransaction.add(R.id.content, mapFragment);
//                    } else {
                        fragmentTransaction.show(mapFragment);
//                    }
                    break;
//                    return true;
                case R.id.navigation_dashboard:
//                    if (scanFragment == null) {
//                        scanFragment = new ScanFragment();
//                        fragmentTransaction.add(R.id.content, scanFragment);
//                    } else {
                        fragmentTransaction.show(scanFragment);
//                    }
                    break;
//                    return true;
                case R.id.navigation_person:
//                    if (personFragment == null) {
//                        personFragment = PersonFragment.newInstance();
//                        fragmentTransaction.add(R.id.content, personFragment);
//                    } else {
                        fragmentTransaction.show(personFragment);
//                    }
                    break;
//                    return true;
            }
            fragmentTransaction.commit();
            return true;
        }

    };

    private void hideFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (scanFragment == null) {
            scanFragment = new ScanFragment();
            fragmentTransaction.add(R.id.content, scanFragment);
        }
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fragmentTransaction.add(R.id.content, mapFragment);
        }
        if (personFragment == null) {
            personFragment = PersonFragment.newInstance();
            fragmentTransaction.add(R.id.content, personFragment);
        }
        if (mapFragment != null) {
            fragmentTransaction.hide(mapFragment);
        }
        if (scanFragment != null) {
            fragmentTransaction.hide(scanFragment);
        }
        if (personFragment != null) {
            fragmentTransaction.hide(personFragment);
        }
    }

    @OnClick(R.id.fab)
    public void onClick() {
        if (LoginModle.isLogin()){
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pairs);
//                new Pair<View, String>(fab,"login"));
            startActivity(new Intent(MainActivity.this,SubmitActivity.class),transitionActivityOptions.toBundle());
        }else {
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pairs);
//                new Pair<View, String>(fab,"login"));
            startActivity(new Intent(MainActivity.this,LoginActivity.class),transitionActivityOptions.toBundle());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
