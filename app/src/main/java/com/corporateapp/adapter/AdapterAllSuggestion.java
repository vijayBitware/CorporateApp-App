package com.corporateapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.HomeActivity;
import com.corporateapp.fragment.FragmentComplaintDetails;
import com.corporateapp.models.allSuggestion.AllSuggestionDatum;
import com.corporateapp.models.like.LikeResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class AdapterAllSuggestion extends RecyclerView.Adapter<AdapterAllSuggestion.ViewHolder> implements APIRequest.ResponseHandler {

    int curentPosition;
    private List<AllSuggestionDatum> arrComplaintList;
    private Context context;

    public AdapterAllSuggestion(Context context, List<AllSuggestionDatum> galleryList) {
        this.arrComplaintList = galleryList;
        this.context = context;
    }

    @Override
    public AdapterAllSuggestion.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suggestion_list_row, viewGroup, false);
        return new AdapterAllSuggestion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.txt_complaint_name.setText(URLDecoder.decode(arrComplaintList.get(position).getTitle(), AppConstants.dataFormat));
            holder.txt_complaint_date.setText(URLDecoder.decode(arrComplaintList.get(position).getDate(), AppConstants.dataFormat));
            holder.txt_complaint_description.setText(URLDecoder.decode(arrComplaintList.get(position).getDescription(), AppConstants.dataFormat));
            Glide.with(context).load(arrComplaintList.get(position).getImage()).placeholder(R.drawable.placeholder_suggesion).into(holder.img_complaint_icon);
            holder.txt_num_likes.setText("(" + URLDecoder.decode(arrComplaintList.get(position).getLikes(), AppConstants.dataFormat) + ")");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreference.putValue(context, "type", "suggesion");
                AppSharedPreference.putValue(context, "pincode", arrComplaintList.get(position).getPincode());
                Bundle bundle = new Bundle();
                bundle.putString("type", "suggesion");
                bundle.putString("date", arrComplaintList.get(position).getDate());
                bundle.putString("title", arrComplaintList.get(position).getTitle());
                bundle.putString("ward", arrComplaintList.get(position).getWard());
                bundle.putString("location", arrComplaintList.get(position).getLocation());
                bundle.getString("pincode", arrComplaintList.get(position).getPincode());
                bundle.putString("description", arrComplaintList.get(position).getDescription());
                bundle.putString("department", arrComplaintList.get(position).getDepartment());
                // bundle.putString("actiontaken",arrComplaintList.get(position).getReply());
                bundle.putString("complaintImage", arrComplaintList.get(position).getImage());
                bundle.putString("likes", arrComplaintList.get(position).getLikes());
                AppUtil.replaceFragment(new FragmentComplaintDetails(), ((HomeActivity) context).getSupportFragmentManager(), bundle);
            }
        });

        holder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curentPosition = position;
                if (AppUtil.isConnectingToInternet(context)) {
                    callLikeComplaintApi(arrComplaintList.get(position).getId());
                } else {
                    AppUtil.showToastMsg(context, context.getResources().getString(R.string.no_internet));
                }
            }
        });

        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curentPosition = position;
                shareSuggesion();
            }
        });

    }

    private void shareSuggesion() {
        String content = "Title-" + arrComplaintList.get(curentPosition).getTitle() + "\nDescription-" + arrComplaintList.get(curentPosition).getDescription();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Suggesion Via"));
    }

    @Override
    public int getItemCount() {
        return arrComplaintList.size();
    }

    private void callLikeComplaintApi(String id) {
        String likeComplainTurl = AppConstants.BASE_URL + "like_suggestion";
        System.out.println("Like c url > " + likeComplainTurl);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", AppSharedPreference.getValue(context, "token"));
            jsonObject.put("sid", id);
            Log.e("TAg", "REquest >" + jsonObject);
            new APIRequest(context, likeComplainTurl, jsonObject, this, AppConstants.API_LIKESUGGESION, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(BaseResponse response) {
        LikeResponce responce = (LikeResponce) response;
        if (responce.getStatus().equalsIgnoreCase("success")) {
            arrComplaintList.get(curentPosition).setLikes(responce.getLikes());
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_row;
        private ImageView img_complaint_icon, img_like, iv_share;
        private TextView txt_complaint_name, txt_complaint_description, txt_complaint_date, tv_status, txt_num_likes;

        public ViewHolder(View view) {
            super(view);
            txt_complaint_name = (TextView) view.findViewById(R.id.txt_complaint_name);
            txt_complaint_date = (TextView) view.findViewById(R.id.txt_complaint_date);
            txt_complaint_description = (TextView) view.findViewById(R.id.txt_complaint_description);
            tv_status = (TextView) view.findViewById(R.id.tv_status);

            img_complaint_icon = (ImageView) view.findViewById(R.id.img_complaint_icon);
            txt_num_likes = (TextView) view.findViewById(R.id.txt_num_likes);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
            img_like = (ImageView) view.findViewById(R.id.img_like);
            iv_share = (ImageView) view.findViewById(R.id.iv_share);
        }
    }
}