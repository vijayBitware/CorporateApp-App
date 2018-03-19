package com.corporateapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.models.login.LoginResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity implements APIRequest.ResponseHandler {

    TextView tv_forgotPassword, tv_login, tv_registration;
    EditText edt_phoneNumber, edt_password;
    String phoneNumber, passsword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(LoginActivity.this)) {
                    login();
                } else {
                    AppUtil.showToastMsg(LoginActivity.this, getResources().getString(R.string.no_internet));
                }
            }
        });
        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }

    private void init() {
        tv_forgotPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_registration = (TextView) findViewById(R.id.tv_registration);
        edt_phoneNumber = (EditText) findViewById(R.id.edt_phoneNumber);
        edt_password = (EditText) findViewById(R.id.edt_password);
    }

    private void login() {
        phoneNumber = edt_phoneNumber.getText().toString();
        passsword = edt_password.getText().toString();

        if (!phoneNumber.isEmpty()) {
            if (!(phoneNumber.length() < 10)) {
                if (!passsword.isEmpty()) {
                    callLoginApi();
                } else {
                    edt_password.setError(getResources().getString(R.string.enter_password));
                    edt_password.requestFocus();
                }
            } else {
                edt_phoneNumber.setError(getResources().getString(R.string.enter_valid_number));
                edt_phoneNumber.requestFocus();
            }
        } else {
            edt_phoneNumber.setError(getResources().getString(R.string.enter_number));
            edt_phoneNumber.requestFocus();
        }
    }

    private void callLoginApi() {
        String url = AppConstants.BASE_URL + "login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", URLEncoder.encode(phoneNumber, AppConstants.dataFormat));
            jsonObject.put("pwd", URLEncoder.encode(passsword, AppConstants.dataFormat));

            new APIRequest(LoginActivity.this, url, jsonObject, this, AppConstants.API_LOGIN, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        LoginResponce registrationResponce = (LoginResponce) response;
        AppUtil.showToastMsg(LoginActivity.this, registrationResponce.getDescription());
        if (registrationResponce.getStatus().equalsIgnoreCase("success")) {
            AppSharedPreference.putValue(LoginActivity.this, "isLogin", "yes");
            AppSharedPreference.putValue(LoginActivity.this, "user_id", registrationResponce.getUserData().getUserId());
            AppSharedPreference.putValue(LoginActivity.this, "token", registrationResponce.getUserData().getToken());
            AppSharedPreference.putValue(LoginActivity.this, "user_name", registrationResponce.getUserData().getName());
            AppSharedPreference.putValue(LoginActivity.this, "user_email", registrationResponce.getUserData().getEmail());
            AppSharedPreference.putValue(LoginActivity.this, "user_phonenumber", registrationResponce.getUserData().getMobile());
            AppSharedPreference.putValue(LoginActivity.this, "user_bloodgrp", registrationResponce.getUserData().getBloodGrp());
            String lang = AppSharedPreference.getValue(LoginActivity.this, "lang");
            if (lang.equalsIgnoreCase("")) {
                AppSharedPreference.putValue(LoginActivity.this, "lang", "english");
            }
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onBackPressed() {
        AppUtil.showExitDialog(LoginActivity.this);
    }
}
