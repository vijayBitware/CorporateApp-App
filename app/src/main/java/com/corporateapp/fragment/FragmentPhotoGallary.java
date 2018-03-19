package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corporateapp.R;
import com.corporateapp.adapter.AdapterPhotoGallary;
import com.corporateapp.webservice.AppConstants;

/**
 * Created by bitware on 17/10/17.
 */

public class FragmentPhotoGallary extends Fragment {

    View view;
    RecyclerView rv_photoGallary;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photogallary, container, false);
        init();
        return view;
    }

    private void init() {
        rv_photoGallary = (RecyclerView) view.findViewById(R.id.rv_photoGallary);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rv_photoGallary.setLayoutManager(layoutManager);
        rv_photoGallary.setAdapter(new AdapterPhotoGallary(getContext(), AppConstants.arrGallary));
    }
}
