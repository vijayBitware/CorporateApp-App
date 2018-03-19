package com.corporateapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.R;

import java.util.Locale;

/**
 * Created by bitware on 16/10/17.
 */

public class SplashActivity extends AppCompatActivity {

    int SPLASH_TIME_OUT = 2000;
    Context context = SplashActivity.this;
    Locale locale;
    Configuration config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (AppSharedPreference.getValue(SplashActivity.this, "lang").equals("marathi")) {
            locale = new Locale("hi");
            Locale.setDefault(locale);
            config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else if (AppSharedPreference.getValue(SplashActivity.this, "lang").equals("english")) {
            locale = new Locale("en");
            Locale.setDefault(locale);
            config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        String lang = AppSharedPreference.getValue(SplashActivity.this, "lang");
        if (lang.equalsIgnoreCase("")) {
            AppSharedPreference.putValue(SplashActivity.this, "lang", "english");
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (AppSharedPreference.getValue(context, "isLogin").equalsIgnoreCase("yes")) {
                    startActivity(new Intent(context, HomeActivity.class));
                    finish();
                } else if (AppSharedPreference.getValue(context, "isRegistered").equalsIgnoreCase("yes")) {
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(context, RegistrationActivity.class));
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
