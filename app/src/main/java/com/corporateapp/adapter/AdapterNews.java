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
 * Created by bitware on 13/10/17.
 */

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {

    ImageLoader imageLoader;
    private List<NewsDatum> arrNews;
    private Context context;

    public AdapterNews(Context context, List<NewsDatum> arrNews) {
        this.arrNews = arrNews;
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_newsTitle.setText(arrNews.get(position).getHeadline());
        holder.tv_newDate.setText(arrNews.get(position).getDate());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_news).cacheOnDisc().build();
        imageLoader.displayImage(arrNews.get(position).getImage(), holder.img, options);
        // Glide.with(context).load(arrNews.get(position).getImage()).placeholder(R.drawable.placeholder_newshome).into(holder.img);
        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "news");
                bundle.putString("title", arrNews.get(position).getHeadline());
                bundle.putString("description", arrNews.get(position).getDescription());
                bundle.putString("image", arrNews.get(position).getImage());
                AppUtil.replaceFragment(new FragmentNewdetails(), ((HomeActivity) context).getSupportFragmentManager(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_row;
        private ImageView img;
        private TextView tv_newsTitle, tv_newDate;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.iv_newsPic);
            tv_newsTitle = (TextView) view.findViewById(R.id.tv_newsTitle);
            tv_newDate = (TextView) view.findViewById(R.id.tv_newDate);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
        }
    }
}