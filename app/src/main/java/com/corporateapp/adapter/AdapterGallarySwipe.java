package com.corporateapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.corporateapp.AppUtils.TouchImageView;
import com.corporateapp.R;
import com.corporateapp.webservice.AppConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by bitware on 26/10/17.
 */

public class AdapterGallarySwipe extends PagerAdapter {
    ImageLoader imageLoader;
    TouchImageView imageView;
    private Context ctx;
    private LayoutInflater layoutInflater;


    public AdapterGallarySwipe(Context ctx) {
        this.ctx = ctx;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
    }


    @Override
    public int getCount() {
        return AppConstants.arrGallary.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == (LinearLayout) arg1);
    }


    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = layoutInflater.inflate(R.layout.row_gallary_array, container, false);

        imageView = (TouchImageView) item_view.findViewById(R.id.iv_image);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_gallary).cacheOnDisc().build();
        imageLoader.displayImage(AppConstants.arrGallary.get(position).getImage(), imageView, options);

        container.addView(item_view);

        return item_view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }

}
