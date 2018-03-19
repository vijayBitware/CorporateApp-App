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
import com.corporateapp.adapter.AdapterAmbulance;
import com.corporateapp.adapter.AdapterBloodBank;
import com.corporateapp.models.home.BloodBank;
import com.corporateapp.webservice.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 24/10/17.
 */

public class FragmentBloodBanl extends Fragment {

    View view;
    RecyclerView rv_emergrncyContactList;
    EditText edt_search;
    AdapterAmbulance adapterEmergencyContactList;
    TextView tv_screenTitle;
    List<BloodBank> arrBloodBank;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emergrncycontactlist, container, false);
        init();
        rv_emergrncyContactList.setAdapter(new AdapterBloodBank(getContext(), AppConstants.arrBloodBank));

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrBloodBank = new ArrayList<BloodBank>();
                if (!(AppConstants.arrBloodBank.size() == 0)) {
                    for (int i = 0; i < AppConstants.arrBloodBank.size(); i++) {
                        if (AppConstants.arrBloodBank.get(i).getName().toLowerCase().contains(edt_search.getText().toString().toLowerCase())) {
                            arrBloodBank.add(AppConstants.arrBloodBank.get(i));
                        }
                    }
                    rv_emergrncyContactList.setAdapter(new AdapterBloodBank(getContext(), arrBloodBank));
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
        tv_screenTitle = (TextView) view.findViewById(R.id.tv_screenTitle);
        tv_screenTitle.setText(getResources().getString(R.string.blood_bank));
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        edt_search.setHint(getResources().getString(R.string.search_bloodbank));
        rv_emergrncyContactList = (RecyclerView) view.findViewById(R.id.rv_emergency_contact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_emergrncyContactList.setLayoutManager(layoutManager);

    }
}
