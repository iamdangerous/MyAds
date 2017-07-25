package com.redgingers.myads;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.inmobi.sdk.InMobiSdk;

/**
 * Created by rkrde on 21-07-2017.
 */

public class BaseActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, "df19afdaf27f4fb4a2c2b85e2c10bc6a");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }
}
