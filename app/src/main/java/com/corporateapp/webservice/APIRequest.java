package com.corporateapp.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.models.Registration.RegistrationResponce;
import com.corporateapp.models.allSuggestion.AllSuggesionResponce;
import com.corporateapp.models.complaints.ComplaintResponce;
import com.corporateapp.models.forgotpass.ForgotPassResponce;
import com.corporateapp.models.home.HomeResponce;
import com.corporateapp.models.like.LikeResponce;
import com.corporateapp.models.login.LoginResponce;
import com.corporateapp.models.suggestion.SuggesionResponce;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by bitware on 2/9/17.
 */

public class APIRequest extends AppCompatActivity {

    ProgressDialog progressDialog;
    BaseResponse baseResponse;
    private JSONObject mJsonObject;
    private String mUrl;
    private ResponseHandler responseHandler;
    private int API_NAME;
    private Context mContext;

    public APIRequest(Context context, String url, JSONObject jsonObject, ResponseHandler responseHandler, int api, String methodName) {
        this.responseHandler = responseHandler;
        this.API_NAME = api;
        this.mUrl = url;
        this.mJsonObject = jsonObject;
        this.mContext = context;


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Processing..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        if (methodName.equals(AppConstants.GET)) {
            apiGetRequest();
        } else {
            apiPostRequest();
        }

    }

    private void apiPostRequest() {
        String REQUEST_TAG = String.valueOf(API_NAME);
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(mUrl, mJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "in post service");
                        System.out.println(" >>> API RESPONSE " + response);
                        setResponseToBody(response);
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error > " + error);
                AppUtil.showToastMsg(mContext, "Something went wrong.Please try again later.");
                progressDialog.dismiss();
            }
        });

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(mContext).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    private void apiGetRequest() {
        String REQUEST_TAG = String.valueOf(API_NAME);
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(mUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", "in get service");
                        System.out.println("Response is >> " + response);
                        setResponseToBody(response);
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error > " + error);
                AppUtil.showToastMsg(mContext, "Something went wrong.Please try again later.");
                progressDialog.dismiss();
            }
        });

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppSingleton.getInstance(mContext).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    private void setResponseToBody(JSONObject response) {
        Gson gson = new Gson();
        System.out.println("***********name*" + API_NAME);
        switch (API_NAME) {
            case AppConstants.API_REGISTRATION:
                baseResponse = gson.fromJson(response.toString(), RegistrationResponce.class);
                break;
            case AppConstants.API_LOGIN:
                baseResponse = gson.fromJson(response.toString(), LoginResponce.class);
                break;
            case AppConstants.API_FORGOTPASS:
                baseResponse = gson.fromJson(response.toString(), ForgotPassResponce.class);
                break;
            case AppConstants.API_ALLCOMPLAINTS:
                baseResponse = gson.fromJson(response.toString(), ComplaintResponce.class);

                break;
            case AppConstants.API_SUGGETION:
                baseResponse = gson.fromJson(response.toString(), SuggesionResponce.class);
                System.out.println("*******base*********" + response.toString());
                break;

            case AppConstants.API_ALL_SUGGETION:
                baseResponse = gson.fromJson(response.toString(), AllSuggesionResponce.class);
                System.out.println("*******base*********" + response.toString());
                break;
            case AppConstants.API_MYCOMPLAINTS:
                baseResponse = gson.fromJson(response.toString(), ComplaintResponce.class);
                break;
            case AppConstants.API_HOME:
                baseResponse = gson.fromJson(response.toString(), HomeResponce.class);
                break;
            case AppConstants.API_LIKECOMPLAINT:
                baseResponse = gson.fromJson(response.toString(), LikeResponce.class);
                break;
            case AppConstants.API_LIKESUGGESION:
                baseResponse = gson.fromJson(response.toString(), LikeResponce.class);
                break;

        }
        baseResponse.setApiName(API_NAME);
        responseHandler.onSuccess(baseResponse);
        responseHandler.onFailure(baseResponse);
    }

    public interface ResponseHandler {
        public void onSuccess(BaseResponse response);

        public void onFailure(BaseResponse response);
    }

}
