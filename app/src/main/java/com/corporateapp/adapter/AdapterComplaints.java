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
import com.corporateapp.models.complaints.ComplaintDatum;
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

public class AdapterComplaints extends RecyclerView.Adapter<AdapterComplaints.MyViewHolder> implements APIRequest.ResponseHandler {
    Context context;
    List<ComplaintDatum> arrComplaintList;
    int curentPosition;

    public AdapterComplaints(Context context, List<ComplaintDatum> arrSelectStore) {
        this.context = context;
        this.arrComplaintList = arrSelectStore;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.txt_complaint_name.setText(URLDecoder.decode(arrComplaintList.get(position).getTitle(), "utf-8"));
            Log.e("TAG", "Complaint titles >" + URLDecoder.decode(arrComplaintList.get(position).getTitle(), "utf-8"));
            holder.txt_complaint_date.setText(URLDecoder.decode(arrComplaintList.get(position).getDate(), "utf-8"));
            holder.txt_complaint_description.setText(URLDecoder.decode(arrComplaintList.get(position).getDescription(), "utf-8"));
            Glide.with(context).load(arrComplaintList.get(position).getImage()).placeholder(R.drawable.placeholder_complaint).into(holder.img_complaint_icon);
            holder.txt_num_likes.setText("(" + arrComplaintList.get(position).getLikes() + ")");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreference.putValue(context, "type", "complaint");
                AppSharedPreference.putValue(context, "pincode", arrComplaintList.get(position).getPincode());
                Bundle bundle = new Bundle();
                bundle.putString("type", "complaint");
                bundle.putString("id", arrComplaintList.get(position).getId());
                bundle.putString("date", arrComplaintList.get(position).getDate());
                bundle.putString("title", arrComplaintList.get(position).getTitle());
                bundle.putString("ward", arrComplaintList.get(position).getWard());
                bundle.putString("location", arrComplaintList.get(position).getLocation());
                bundle.getString("pincode", arrComplaintList.get(position).getPincode());
                bundle.putString("description", arrComplaintList.get(position).getDescription());
                bundle.putString("department", arrComplaintList.get(position).getDepartment());
                bundle.putString("actiontaken", arrComplaintList.get(position).getReply());
                bundle.putString("complaintImage", arrComplaintList.get(position).getImage());
                bundle.putString("likes", arrComplaintList.get(position).getLikes());
                bundle.putString("status", arrComplaintList.get(position).getStatus());
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

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = arrComplaintList.get(position).getDescription();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Complaint");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        if (arrComplaintList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.tv_status.setText(context.getString(R.string.status_open));
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.open_bkg));
        } else if (arrComplaintList.get(position).getStatus().equalsIgnoreCase("2")) {
            holder.tv_status.setText(context.getString(R.string.status_inprogress));
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.inprogress_bkg));
        } else if (arrComplaintList.get(position).getStatus().equals("3")) {
            holder.tv_status.setText(context.getString(R.string.status_close));
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.closed_bkg));
        } else if (arrComplaintList.get(position).getStatus().equals("0")) {
            holder.tv_status.setText(context.getString(R.string.status_not_verifiedd));
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.notverified_bkg));
        }

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curentPosition = position;
                shareComment();
            }
        });
    }

    private void shareComment() {
        String content = "Title-" + arrComplaintList.get(curentPosition).getTitle() + "\nDescription-" + arrComplaintList.get(curentPosition).getDescription();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Complaint Via"));
    }

    private void callLikeComplaintApi(String id) {
        String likeComplainTurl = AppConstants.BASE_URL + "like_complaints";
        System.out.println("Like c url > " + likeComplainTurl);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", AppSharedPreference.getValue(context, "token"));
            jsonObject.put("cid", id);
            Log.e("TAg", "REquest >" + jsonObject);
            new APIRequest(context, likeComplainTurl, jsonObject, this, AppConstants.API_LIKECOMPLAINT, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrComplaintList.size();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_complaint_icon, img_like, img_share;
        private TextView txt_complaint_name, txt_complaint_description, txt_complaint_date, tv_status, txt_num_likes;
        private LinearLayout ll_row;

        public MyViewHolder(View view) {
            super(view);
            txt_complaint_name = (TextView) view.findViewById(R.id.txt_complaint_name);
            txt_complaint_date = (TextView) view.findViewById(R.id.txt_complaint_date);
            txt_complaint_description = (TextView) view.findViewById(R.id.txt_complaint_description);
            tv_status = (TextView) view.findViewById(R.id.tv_status);

            img_complaint_icon = (ImageView) view.findViewById(R.id.img_complaint_icon);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
            img_like = (ImageView) view.findViewById(R.id.img_like);
            txt_num_likes = (TextView) view.findViewById(R.id.txt_num_likes);
            img_share = (ImageView) view.findViewById(R.id.img_share);
        }
    }

}
