package com.example.jhon.venue.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

import com.example.jhon.venue.R;
import com.example.jhon.venue.UI.ShowUtil;

/**
 * Created by John on 2017/5/8.
 */

public class SettingActivity extends PreferenceActivity {

    private CheckBoxPreference checkBoxPreference;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        checkBoxPreference= (CheckBoxPreference) findPreference("AutoLoadImg");
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("AutoLoadImg")){

                    Boolean autoBack=sp.getBoolean("AutoLoadImg",false);

                    if (autoBack){
                        ShowUtil.showToast(SettingActivity.this,""+autoBack);
                        checkBoxPreference.setTitle(checkBoxPreference.getTitle()+"false");
                    }else {
                        ShowUtil.showToast(SettingActivity.this,""+autoBack);
                        checkBoxPreference.setTitle(checkBoxPreference.getTitle()+"true");
                    }
                }
            }
        });
    }
}
