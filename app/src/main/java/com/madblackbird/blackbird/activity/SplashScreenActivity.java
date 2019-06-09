package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.service.LocationService;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences preferences = getSharedPreferences("blackbird", MODE_PRIVATE);
        new LocationService(this);
        Intent intent;
        if (!preferences.contains("firstUse")) {
            SharedPreferences.Editor mEditor = preferences.edit();
            mEditor.putBoolean("firstUse", true).apply();
            intent = new Intent(SplashScreenActivity.this, AppIntroActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        }
        new Handler().postDelayed(() ->
                startActivity(intent), 2000);
    }

}
