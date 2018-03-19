package com.corporateapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.R;
import com.corporateapp.models.like.LikeResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class FragmentComplaintDetails extends Fragment implements APIRequest.ResponseHandler {

    View view;
    TextView txtCompHd, txtDate, txtDeaprtment, txtWard, txtLocation, txtPincode, txtDescription, txtActionTaken, tvStatus, txt_num_likes, tv_screenTitle;
    ImageView iv_complaintImage, img_like, img_share;
    LinearLayout ll_actionTaken;
    Bundle bundle;
    Button btnBack;
    FragmentManager fragmentManager;
    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complaint_details, container, false);
        init();
        bundle = getArguments();
        if (bundle.getString("type").equalsIgnoreCase("suggesion")) {
            tv_screenTitle.setText(getResources().getString(R.string.txt_suggesiondetail));
            ll_actionTaken.setVisibility(View.GONE);
            tvStatus.setVisibility(View.GONE);

            try {
                txtCompHd.setText(URLDecoder.decode(bundle.getString("title"), AppConstants.dataFormat));
                txtDate.setText(URLDecoder.decode(bundle.getString("date"), AppConstants.dataFormat));
                txtDeaprtment.setText(URLDecoder.decode(bundle.getString("department"), AppConstants.dataFormat));
                txtWard.setText(URLDecoder.decode(bundle.getString("ward"), AppConstants.dataFormat));
                txtLocation.setText(URLDecoder.decode(bundle.getString("location"), AppConstants.dataFormat));
                txtPincode.setText(URLDecoder.decode(AppSharedPreference.getValue(getContext(), "pincode"), AppConstants.dataFormat));
                txtDescription.setText(URLDecoder.decode(bundle.getString("description"), AppConstants.dataFormat));
                Glide.with(getContext()).load(bundle.getString("complaintImage")).placeholder(R.drawable.placeholder_suggesion).into(iv_complaintImage);
                txt_num_likes.setText("(" + URLDecoder.decode(bundle.getString("likes"), AppConstants.dataFormat) + ")");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            ll_actionTaken.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
            try {
                txtCompHd.setText(URLDecoder.decode(bundle.getString("title"), AppConstants.dataFormat));
                txtDate.setText(URLDecoder.decode(bundle.getString("date"), AppConstants.dataFormat));
                txtDeaprtment.setText(URLDecoder.decode(bundle.getString("department"), AppConstants.dataFormat));
                txtWard.setText(URLDecoder.decode(bundle.getString("ward"), AppConstants.dataFormat));
                txtLocation.setText(URLDecoder.decode(bundle.getString("location"), AppConstants.dataFormat));
                txtPincode.setText(URLDecoder.decode(AppSharedPreference.getValue(getContext(), "pincode"), AppConstants.dataFormat));
                txtDescription.setText(URLDecoder.decode(bundle.getString("description"), AppConstants.dataFormat));
                String actionTaken = bundle.getString("actiontaken");
                if (actionTaken.equalsIgnoreCase("")) {
                    txtActionTaken.setText(getResources().getString(R.string.no_action_detail));
                } else {
                    txtActionTaken.setText(bundle.getString("actiontaken"));
                }
                Glide.with(getContext()).load(bundle.getString("complaintImage")).placeholder(R.drawable.placeholder_complaint).into(iv_complaintImage);
                txt_num_likes.setText("(" + bundle.getString("likes") + ")");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (bundle.getString("status").equalsIgnoreCase("1")) {
                tvStatus.setText(getResources().getString(R.string.status_open));
                tvStatus.setBackgroundColor(getResources().getColor(R.color.open_bkg));
            } else if (bundle.getString("status").equalsIgnoreCase("2")) {
                tvStatus.setText(getResources().getString(R.string.status_inprogress));
                tvStatus.setBackgroundColor(getResources().getColor(R.color.inprogress_bkg));
            } else if (bundle.getString("status").equals("3")) {
                tvStatus.setText(getResources().getString(R.string.status_close));
                tvStatus.setBackgroundColor(getResources().getColor(R.color.closed_bkg));
            } else if (bundle.getString("status").equals("0")) {
                tvStatus.setText(getResources().getString(R.string.status_not_verifiedd));
                tvStatus.setBackgroundColor(getResources().getColor(R.color.notverified_bkg));
            }
        }

        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppSharedPreference.getValue(getContext(), "type").equalsIgnoreCase("suggesion")) {
                    System.out.println("***********sugge like********" + bundle.getString("id"));
                    callLikeSuggesonApi(bundle.getString("id"));
                } else if (AppSharedPreference.getValue(getContext(), "type").equalsIgnoreCase("complaint")) {
                    callLikeComplaintApi(bundle.getString("id"));
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStackImmediate();
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSuggesion();
            }
        });
        return view;
    }

    private void shareSuggesion() {
        String content = "Title-" + bundle.getString("title") + "\nDescription-" + bundle.getString("description");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }

    private void init() {
        txtCompHd = (TextView) view.findViewById(R.id.txtCompHd);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtDeaprtment = (TextView) view.findViewById(R.id.txtDeaprtment);
        txtWard = (TextView) view.findViewById(R.id.txtWard);
        txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        txtPincode = (TextView) view.findViewById(R.id.txtPincode);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtActionTaken = (TextView) view.findViewById(R.id.txtActionTaken);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        iv_complaintImage = (ImageView) view.findViewById(R.id.iv_complaintImage);
        ll_actionTaken = (LinearLayout) view.findViewById(R.id.ll_actionTaken);
        txt_num_likes = (TextView) view.findViewById(R.id.txt_num_likes);
        img_like = (ImageView) view.findViewById(R.id.img_like);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        fragmentManager = getFragmentManager();
        img_share = (ImageView) view.findViewById(R.id.img_share);
        tv_screenTitle = (TextView) view.findViewById(R.id.tv_screenTitle);
    }

    private void callLikeComplaintApi(String id) {
        String likeComplainTurl = AppConstants.BASE_URL + "like_complaints";
        System.out.println("Like c url > " + likeComplainTurl);
        jsonObject = new JSONObject();
        try {
            jsonObject.put("token", AppSharedPreference.getValue(getContext(), "token"));
            jsonObject.put("cid", id);
            Log.e("TAg", "REquest >" + jsonObject);
            new APIRequest(getContext(), likeComplainTurl, jsonObject, this, AppConstants.API_LIKECOMPLAINT, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callLikeSuggesonApi(String sid) {
        String likeComplainTurl = AppConstants.BASE_URL + "like_complaints";
        System.out.println("Like c url > " + likeComplainTurl);
        jsonObject = new JSONObject();
        try {
            jsonObject.put("token", AppSharedPreference.getValue(getContext(), "token"));
            jsonObject.put("cid", sid);
            Log.e("TAg", "REquest >" + jsonObject);
            new APIRequest(getContext(), likeComplainTurl, jsonObject, this, AppConstants.API_LIKECOMPLAINT, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        LikeResponce likeResponce = (LikeResponce) response;
        if (likeResponce.getStatus().equalsIgnoreCase("success")) {
            txt_num_likes.setText("(" + likeResponce.getLikes() + ")");
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }
}
