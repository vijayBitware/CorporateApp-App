package com.corporateapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.models.Registration.RegistrationResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by bitware on 12/10/17.
 */

public class RegistrationActivity extends AppCompatActivity implements APIRequest.ResponseHandler {

    EditText edt_email, edt_phoneNumber, edt_bloodGroup, edt_password, edt_confirmPassword, edt_name;
    TextView tv_createAccount, tv_alreadyUser;
    String email, phoneNumber, bloodGrp, password, confirmPassword, name;
    Context context = RegistrationActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(RegistrationActivity.this)) {
                    createAccount();
                } else {
                    AppUtil.showToastMsg(RegistrationActivity.this, getResources().getString(R.string.no_internet));
                }
            }
        });
        tv_alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void init() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_phoneNumber = (EditText) findViewById(R.id.edt_phoneNumber);
        edt_bloodGroup = (EditText) findViewById(R.id.edt_bloodGroup);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirmPassword = (EditText) findViewById(R.id.edt_confirmPassword);
        tv_createAccount = (TextView) findViewById(R.id.tv_createAccount);
        tv_alreadyUser = (TextView) findViewById(R.id.tv_alreadyUser);
    }

    private void createAccount() {
        name = edt_name.getText().toString();
        email = edt_email.getText().toString();
        phoneNumber = edt_phoneNumber.getText().toString();
        bloodGrp = edt_bloodGroup.getText().toString();
        password = edt_password.getText().toString();
        confirmPassword = edt_confirmPassword.getText().toString();

        if (!name.isEmpty()) {
            if (!email.isEmpty()) {
                if (email.matches(AppConstants.EMAIL_REGEX)) {
                    if (!phoneNumber.isEmpty()) {
                        if (!(phoneNumber.length() < 10)) {
                            if (!password.isEmpty()) {
                                if (!confirmPassword.isEmpty()) {
                                    if (password.equals(confirmPassword)) {
                                        callRegistrationApi();
                                    } else {
                                        AppUtil.showToastMsg(context, getResources().getString(R.string.password_dosent_match));
                                    }
                                } else {
                                    edt_confirmPassword.setError(getResources().getString(R.string.enter_confirmPassword));
                                    edt_confirmPassword.requestFocus();
                                }
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
                } else {
                    edt_email.setError(getResources().getString(R.string.enter_valid_email));
                    edt_email.requestFocus();
                }
            } else {
                edt_email.setError(getResources().getString(R.string.enter_email));
                edt_email.requestFocus();
            }
        } else {
            edt_name.setError(getResources().getString(R.string.enter_name));
            edt_name.requestFocus();
        }
    }

    private void callRegistrationApi() {
        String url = AppConstants.BASE_URL + "register_new_customer";
        System.out.println("Registration Url > " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", URLEncoder.encode(name, AppConstants.dataFormat));
            jsonObject.put("email", URLEncoder.encode(email, AppConstants.dataFormat));
            jsonObject.put("mobile", URLEncoder.encode(phoneNumber, AppConstants.dataFormat));
            jsonObject.put("pwd", URLEncoder.encode(password, AppConstants.dataFormat));
            jsonObject.put("b_group", URLEncoder.encode(bloodGrp, AppConstants.dataFormat));
            Log.e("TAG", "request > " + jsonObject);

            new APIRequest(context, url, jsonObject, this, AppConstants.API_REGISTRATION, AppConstants.POST);
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AppUtil.showExitDialog(context);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        RegistrationResponce registrationResponce = (RegistrationResponce) response;
        if (registrationResponce.getStatus().equalsIgnoreCase("success")) {
            AppUtil.showToastMsg(context, registrationResponce.getDescription());
            AppSharedPreference.putValue(RegistrationActivity.this, "isRegistered", "yes");
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public void onFailure(BaseResponse response) {
        RegistrationResponce registrationResponce = (RegistrationResponce) response;
        AppUtil.showToastMsg(context, registrationResponce.getDescription());
    }
}
