package com.corporateapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.R;
import com.corporateapp.webservice.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

/**
 * Created by bitware on 16/10/17.
 */

public class UserProfileActivity extends AppCompatActivity {

    TextView tv_name, tv_email, tv_phoneNumber, tv_bloodGrp, tv_logout;
    RadioButton radioEnglish, radioMarathi;
    String whichLanguageSelected = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        init();

        if (AppSharedPreference.getValue(UserProfileActivity.this, "lang").equals("marathi")) {
            System.out.println("******ma*****");
            radioMarathi.setChecked(true);
            radioEnglish.setChecked(false);
        } else if (AppSharedPreference.getValue(UserProfileActivity.this, "lang").equals("english")) {
            System.out.println("******en*****");
            radioEnglish.setChecked(true);
            radioMarathi.setChecked(false);
        }

        try {
            tv_name.setText(URLDecoder.decode(AppSharedPreference.getValue(UserProfileActivity.this, "user_name"), AppConstants.dataFormat));
            tv_email.setText(URLDecoder.decode(AppSharedPreference.getValue(UserProfileActivity.this, "user_email"), AppConstants.dataFormat));
            tv_phoneNumber.setText(URLDecoder.decode(AppSharedPreference.getValue(UserProfileActivity.this, "user_phonenumber"), AppConstants.dataFormat));
            //  System.out.println("blood grp > " +AppSharedPreference.getValue(UserProfileActivity.this,"user_bloodgrp"));
            tv_bloodGrp.setText(URLDecoder.decode(AppSharedPreference.getValue(UserProfileActivity.this, "user_bloodgrp"), AppConstants.dataFormat));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogout();
            }
        });


        radioMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale;
                Configuration config;
                if (radioMarathi.isChecked()) {
                    whichLanguageSelected = "marathi";
                    AppSharedPreference.putValue(UserProfileActivity.this, "lang", "marathi");
                    locale = new Locale("hi");
                    Locale.setDefault(locale);
                    config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    finish();
                    Intent i = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                    startActivity(i);
                }
            }
        });
        radioEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale;
                Configuration config;
                if (radioEnglish.isChecked()) {
                    whichLanguageSelected = "english";
                    AppSharedPreference.putValue(UserProfileActivity.this, "lang", "english");
                    locale = new Locale("en");
                    Locale.setDefault(locale);
                    config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    finish();
                    Intent i = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void showLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.logout_msg));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AppSharedPreference.clearPreference(UserProfileActivity.this);
                AppSharedPreference.putValue(UserProfileActivity.this, "isRegistered", "yes");
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                finish();
            }
        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void init() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
        tv_bloodGrp = (TextView) findViewById(R.id.tv_bloodGrp);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        radioEnglish = (RadioButton) findViewById(R.id.radioEnglish);
        radioMarathi = (RadioButton) findViewById(R.id.radioMarathi);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserProfileActivity.this, HomeActivity.class));
        finish();
    }
}
