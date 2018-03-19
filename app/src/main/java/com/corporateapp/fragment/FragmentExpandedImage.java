package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.R;
import com.corporateapp.adapter.AdapterGallarySwipe;

/**
 * Created by bitware on 20/10/17.
 */

public class FragmentExpandedImage extends Fragment {

    ImageView iv_gallaryImage, left_nav, right_nav;
    View view;
    ViewPager viewPager;
    AdapterGallarySwipe adapterGallarySwipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expandedimage, container, false);
        init();

        viewPager.setCurrentItem(Integer.parseInt(AppSharedPreference.getValue(getContext(), "imagePosition")));
        left_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        right_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });
        return view;
    }

    private void init() {
        iv_gallaryImage = (ImageView) view.findViewById(R.id.iv_gallaryImage);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        left_nav = (ImageView) view.findViewById(R.id.left_nav);
        right_nav = (ImageView) view.findViewById(R.id.right_nav);
        adapterGallarySwipe = new AdapterGallarySwipe(getContext());
        viewPager.setAdapter(adapterGallarySwipe);

    }
}
