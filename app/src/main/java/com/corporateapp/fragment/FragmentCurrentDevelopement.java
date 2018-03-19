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
import com.corporateapp.adapter.AdapterCurrentdevelopment;
import com.corporateapp.models.home.Development;
import com.corporateapp.webservice.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentCurrentDevelopement extends Fragment {
    View view;
    RecyclerView lv_allComplaints;
    TextView tv_screenTitle;
    EditText edt_search;
    List<Development> arrDevelopment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_news, container, false);
        init();
        tv_screenTitle.setText(getResources().getString(R.string.current_development));
        edt_search.setHint(getResources().getString(R.string.search_development));

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrDevelopment = new ArrayList<Development>();
                if (!(AppConstants.arrDevelopment.size() == 0)) {
                    for (int i = 0; i < AppConstants.arrDevelopment.size(); i++) {
                        if (AppConstants.arrDevelopment.get(i).getHeadline().toLowerCase().contains(edt_search.getText().toString().toLowerCase())) {
                            arrDevelopment.add(AppConstants.arrDevelopment.get(i));
                        }
                    }
                    lv_allComplaints.setAdapter(new AdapterCurrentdevelopment(getContext(), arrDevelopment));
                } else {
                    AppUtil.showToastMsg(getContext(), "No Developments Available");
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
        lv_allComplaints = (RecyclerView) view.findViewById(R.id.lv_allComplaints);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        lv_allComplaints.setLayoutManager(layoutManager);
        lv_allComplaints.setAdapter(new AdapterCurrentdevelopment(getContext(), AppConstants.arrDevelopment));
        tv_screenTitle = (TextView) view.findViewById(R.id.tv_screenTitle);
    }

}
