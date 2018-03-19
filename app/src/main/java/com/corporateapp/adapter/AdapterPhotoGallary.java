package com.corporateapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.HomeActivity;
import com.corporateapp.fragment.FragmentExpandedImage;
import com.corporateapp.models.home.Gallery;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class AdapterPhotoGallary extends RecyclerView.Adapter<AdapterPhotoGallary.ViewHolder> {
    ImageLoader imageLoader;
    private List<Gallery> galleryList;
    private Context context;

    public AdapterPhotoGallary(Context context, List<Gallery> galleryList) {
        this.galleryList = galleryList;
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        System.out.println("image urls > " + galleryList.size());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_gallary).cacheOnDisc().build();
        imageLoader.displayImage(galleryList.get(position).getThumbImage(), holder.img, options);
        //  Glide.with(context).load(galleryList.get(position).getImage()).placeholder(R.drawable.placeholder_gallary).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreference.putValue(context, "imagePosition", String.valueOf(position));
                AppSharedPreference.putValue(context, "gallaryImage", galleryList.get(position).getImage());
                AppUtil.replaceFragment(new FragmentExpandedImage(), ((HomeActivity) context).getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
