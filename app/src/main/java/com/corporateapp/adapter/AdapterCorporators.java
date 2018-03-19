package com.corporateapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.corporateapp.fragment.FragmentMemberDetails;
import com.corporateapp.models.home.CorporatorDatum;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bitware on 13/10/17.
 */

public class AdapterCorporators extends RecyclerView.Adapter<AdapterCorporators.MyViewHolder> {
    Context context;
    List<CorporatorDatum> arrCorporator;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageLoader imageLoader;

    public AdapterCorporators(Context context, List<CorporatorDatum> arrSelectStore) {
        this.context = context;
        this.arrCorporator = arrSelectStore;

        sharedPreferences = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coporator, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(arrCorporator.get(position).getName());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_corporator).cacheOnDisc().build();
        imageLoader.displayImage(arrCorporator.get(position).getProfile(), holder.img_store, options);
        //  Glide.with(context).load(arrCorporator.get(position).getProfile()).placeholder(R.drawable.placeholder_corporator).skipMemoryCache(false).into(holder.img_store);
        holder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("member_name", arrCorporator.get(position).getName());
                bundle.putString("member_email", arrCorporator.get(position).getEmail());
                bundle.putString("member_address", arrCorporator.get(position).getCity());
                bundle.putString("member_phone", arrCorporator.get(position).getMobile());
                bundle.putString("member_description", arrCorporator.get(position).getDescription());
                bundle.putString("member_image", arrCorporator.get(position).getProfile());
                AppUtil.replaceFragment(new FragmentMemberDetails(), ((HomeActivity) context).getSupportFragmentManager(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCorporator.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_store;
        TextView tv_name;
        LinearLayout ll_row;

        public MyViewHolder(View view) {
            super(view);
            img_store = (ImageView) view.findViewById(R.id.iv_coporatorImage);
            tv_name = (TextView) view.findViewById(R.id.tv_coporatorName);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
        }
    }
}
