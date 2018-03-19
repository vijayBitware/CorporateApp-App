package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.adapter.AdapterAllNews;
import com.corporateapp.models.home.NewsDatum;
import com.corporateapp.webservice.AppConstants;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class FragmentALlNews extends Fragment {


    View view;
    List<NewsDatum> arrNews;
    RecyclerView lv_allComplaints;
    EditText edt_search;
    TextView tv_noNews;
    AlphaInAnimationAdapter scaleInAnimationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_news, container, false);
        init();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrNews = new ArrayList<NewsDatum>();
                if (!(AppConstants.arrNews.size() == 0)) {
                    for (int i = 0; i < AppConstants.arrNews.size(); i++) {
                        if (AppConstants.arrNews.get(i).getHeadline().toLowerCase().contains(edt_search.getText().toString().toLowerCase())) {
                            arrNews.add(AppConstants.arrNews.get(i));
                        }
                    }
                    lv_allComplaints.setAdapter(new AdapterAllNews(getContext(), arrNews));
                } else {
                    AppUtil.showToastMsg(getContext(), "No News Available");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void init() {
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        tv_noNews = (TextView) view.findViewById(R.id.tv_noNews);
        lv_allComplaints = (RecyclerView) view.findViewById(R.id.lv_allComplaints);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        lv_allComplaints.setLayoutManager(layoutManager);

        if (AppConstants.arrNews.size() == 0) {
            tv_noNews.setText(getResources().getString(R.string.no_news));
            tv_noNews.setVisibility(View.VISIBLE);
            lv_allComplaints.setVisibility(View.INVISIBLE);
        } else {
            tv_noNews.setVisibility(View.INVISIBLE);
            lv_allComplaints.setVisibility(View.VISIBLE);
            lv_allComplaints.setAdapter(new AdapterAllNews(getContext(), AppConstants.arrNews));
            //lv_allComplaints.setItemAnimator(scaleInAnimationAdapter);
        }


    }
}
