package com.corporateapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.corporateapp.R;
import com.corporateapp.adapter.slidingImageAdapter;
import com.corporateapp.webservice.AppConstants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bitware on 17/10/17.
 */

public class FragmentDevelopmentDetails extends Fragment {

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    View view;
    TextView txtDevHd, txtDevDesc;
    ViewPager bannaerPager;
    Button btnBack;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_development_detail, container, false);
        init();
        Bundle bundle = getArguments();
        txtDevHd.setText(bundle.getString("title"));
        txtDevDesc.setText(bundle.getString("description"));
        moveSlider();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStackImmediate();
            }
        });
        return view;
    }

    private void init() {
        txtDevHd = (TextView) view.findViewById(R.id.txtDevHd);
        txtDevDesc = (TextView) view.findViewById(R.id.txtDevDesc);
        bannaerPager = (ViewPager) view.findViewById(R.id.pager);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        fragmentManager = getFragmentManager();
    }

    private void moveSlider() {
        System.out.println("array list size in move slider > " + AppConstants.arrSliderImages.size());
        bannaerPager.setAdapter(new slidingImageAdapter(getContext(), AppConstants.arrSliderImages));
        NUM_PAGES = AppConstants.arrSliderImages.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                bannaerPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }
}
