package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corporateapp.R;

public class FragmentNewdetails extends Fragment {

    View view;
    TextView tv_screenTitle, txtDevHd, txtDevDesc;
    ImageView iv_image;
    Button btnBack;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_details, container, false);
        init();
        Bundle bundle = getArguments();
        if (bundle.getString("type").equalsIgnoreCase("news")) {
            tv_screenTitle.setText(getResources().getString(R.string.news_details));
            txtDevHd.setText(bundle.getString("title"));
            txtDevDesc.setText(bundle.getString("description"));
            Glide.with(getContext()).load(bundle.getString("image")).placeholder(R.drawable.placeholder_news).into(iv_image);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStackImmediate();
            }
        });
        return view;
    }

    private void init() {
        tv_screenTitle = (TextView) view.findViewById(R.id.tv_screenTitle);
        txtDevHd = (TextView) view.findViewById(R.id.txtDevHd);
        txtDevDesc = (TextView) view.findViewById(R.id.txtDevDesc);
        iv_image = (ImageView) view.findViewById(R.id.iv_image);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        fragmentManager = getFragmentManager();

    }
}
