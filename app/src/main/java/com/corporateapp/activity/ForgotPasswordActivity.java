package com.corporateapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.models.forgotpass.ForgotPassResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bitware on 16/10/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements APIRequest.ResponseHandler {

    EditText edt_forgotPassword;
    TextView tv_submit;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        init();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_forgotPassword.getText().toString();
                if (!email.isEmpty()) {
                    if (email.matches(AppConstants.EMAIL_REGEX)) {
                        if (AppUtil.isConnectingToInternet(ForgotPasswordActivity.this)) {
                            callForgotPasswordApi();
                        } else {
                            AppUtil.showToastMsg(ForgotPasswordActivity.this, getResources().getString(R.string.no_internet));
                        }
                    }
                } else {
                    edt_forgotPassword.setError(getResources().getString(R.string.enter_email));
                    edt_forgotPassword.requestFocus();
                }
            }
        });

    }

    private void callForgotPasswordApi() {
        String url = AppConstants.BASE_URL + "forgot_password";
        System.out.println(" forgot pass url > " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            Log.e("Reqest > ", String.valueOf(jsonObject));

            new APIRequest(ForgotPasswordActivity.this, url, jsonObject, this, AppConstants.API_FORGOTPASS, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        edt_forgotPassword = (EditText) findViewById(R.id.edt_email);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        ForgotPassResponce forgotPassResponce = (ForgotPassResponce) response;
        if (forgotPassResponce.getStatus().equalsIgnoreCase("success")) {
            AppUtil.showToastMsg(ForgotPasswordActivity.this, forgotPassResponce.getDescription());
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            finish();
        } else {
            AppUtil.showToastMsg(ForgotPasswordActivity.this, forgotPassResponce.getDescription());
        }

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        finish();
    }
}
