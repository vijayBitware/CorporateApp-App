package com.corporateapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.HomeActivity;
import com.corporateapp.fragment.FragmentDevelopmentDetails;
import com.corporateapp.models.home.Development;
import com.corporateapp.models.home.Image;
import com.corporateapp.webservice.AppConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 17/10/17.
 */

public class AdapterCurrentdevelopment extends RecyclerView.Adapter<AdapterCurrentdevelopment.MyViewHolder> {
    Context context;
    List<Development> arrCorporator;
    ImageLoader imageLoader;


    public AdapterCurrentdevelopment(Context context, List<Development> arrSelectStore) {
        this.context = context;
        this.arrCorporator = arrSelectStore;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allnews, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txt_complaint_name.setText(arrCorporator.get(position).getHeadline());
        holder.txt_complaint_date.setText(arrCorporator.get(position).getDate());
        holder.txt_complaint_description.setText(arrCorporator.get(position).getDescription());
        final List<Image> arrImagelist = arrCorporator.get(position).getImages();
        System.out.println("Development imgae > " + arrImagelist.get(0).getImage());
        Glide.with(context).load(arrImagelist.get(0).getImage()).placeholder(R.drawable.placeholder_currentdevelopment).into(holder.img_store);
        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.arrSliderImages = new ArrayList<String>();
                for (int i = 0; i < arrImagelist.size(); i++) {
                    AppConstants.arrSliderImages.add(arrImagelist.get(i).getImage());
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", arrCorporator.get(position).getHeadline());
                bundle.putString("description", arrCorporator.get(position).getDescription());
                AppUtil.replaceFragment(new FragmentDevelopmentDetails(), ((HomeActivity) context).getSupportFragmentManager(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCorporator.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_store;
        TextView tv_name, txt_complaint_name, txt_complaint_date, txt_complaint_description;
        LinearLayout ll_row;

        public MyViewHolder(View view) {
            super(view);
            img_store = (ImageView) view.findViewById(R.id.img_complaint_icon);
            txt_complaint_name = (TextView) view.findViewById(R.id.txt_complaint_name);
            txt_complaint_date = (TextView) view.findViewById(R.id.txt_complaint_date);
            txt_complaint_description = (TextView) view.findViewById(R.id.txt_complaint_description);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
        }
    }
}
