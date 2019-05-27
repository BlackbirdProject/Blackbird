package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.madblackbird.blackbird.R;

public class AppIntroActivity extends AppIntro {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("blackbird", MODE_PRIVATE);
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle(getString(R.string.titulo_slider1));
        sliderPage.setImageDrawable(R.drawable.ic_blackbird_logo);
        sliderPage.setDescription(getString(R.string.descripcion_slider1));
        sliderPage.setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        showSkipButton(true);
        addSlide(AppIntroFragment.newInstance(sliderPage));

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getString(R.string.titulo_slider2));
        sliderPage1.setImageDrawable(R.drawable.place);
        sliderPage1.setDescription(getString(R.string.Descripcion_slider2));
        sliderPage1.setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(getString(R.string.titulo_slider3));
        sliderPage2.setImageDrawable(R.drawable.teamwork);
        sliderPage2.setDescription(getString(R.string.descripcion_slider3));
        sliderPage2.setBgColor(ContextCompat.getColor(getApplicationContext(), R.color.ic_launcher_back));
        addSlide(AppIntroFragment.newInstance(sliderPage2));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        preferences.edit().putBoolean("firstUse", true).apply();
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        preferences.edit().putBoolean("firstUse", true).apply();
        startActivity(intent);
    }

}
