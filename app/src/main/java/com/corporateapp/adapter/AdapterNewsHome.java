package com.corporateapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corporateapp.R;
import com.corporateapp.models.home.NewsDatum;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by bitware on 27/10/17.
 */

public class AdapterNewsHome extends PagerAdapter {
    ImageLoader imageLoader;
    LinearLayout ll_row;
    List<NewsDatum> arrNews;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private ImageView img;
    private TextView tv_newsTitle, tv_newDate;


    public AdapterNewsHome(Context ctx, List<NewsDatum> arrNews) {
        this.ctx = ctx;
        this.arrNews = arrNews;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
    }


    @Override
    public int getCount() {
        return arrNews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == (LinearLayout) arg1);
    }


    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = layoutInflater.inflate(R.layout.row_news, container, false);

        img = (ImageView) item_view.findViewById(R.id.iv_newsPic);
        tv_newsTitle = (TextView) item_view.findViewById(R.id.tv_newsTitle);
        tv_newDate = (TextView) item_view.findViewById(R.id.tv_newDate);
        ll_row = (LinearLayout) item_view.findViewById(R.id.ll_row);

        tv_newsTitle.setText(arrNews.get(position).getHeadline());
        tv_newDate.setText(arrNews.get(position).getDate());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.placeholder_news).cacheOnDisc().build();
        imageLoader.displayImage(arrNews.get(position).getImage(), img, options);

        container.addView(item_view);

        return item_view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}

