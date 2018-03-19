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

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.HomeActivity;
import com.corporateapp.fragment.FragmentNewdetails;
import com.corporateapp.models.home.NewsDatum;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by bitware on 17/10/17.
 */

public class AdapterAllNews extends RecyclerView.Adapter<AdapterAllNews.MyViewHolder> {
    Context context;
    List<NewsDatum> arrCorporator;
    ImageLoader imageLoader;

    public AdapterAllNews(Context context, List<NewsDatum> arrSelectStore) {
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
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_news).cacheOnDisc().build();
        imageLoader.displayImage(arrCorporator.get(position).getImage(), holder.img_store, options);
        //  Glide.with(context).load(arrCorporator.get(position).getImage()).placeholder(R.drawable.placeholder_news).into(holder.img_store);
        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "news");
                bundle.putString("title", arrCorporator.get(position).getHeadline());
                bundle.putString("description", arrCorporator.get(position).getDescription());
                bundle.putString("image", arrCorporator.get(position).getImage());
                AppUtil.replaceFragment(new FragmentNewdetails(), ((HomeActivity) context).getSupportFragmentManager(), bundle);
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
