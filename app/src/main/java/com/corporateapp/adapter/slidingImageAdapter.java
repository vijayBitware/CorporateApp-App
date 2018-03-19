package com.corporateapp.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.corporateapp.R;

import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class slidingImageAdapter extends PagerAdapter {

    private List<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public slidingImageAdapter(Context context, List<String> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        //ImageView sliderImage;
        View itemView = inflater.inflate(R.layout.slidingimages, view, false);

        ImageView sliderImage = (ImageView) itemView.findViewById(R.id.iv_sliderImage);
        Glide.with(context).load(IMAGES.get(position)).placeholder(R.drawable.placeholder_banner).into(sliderImage);
        view.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
