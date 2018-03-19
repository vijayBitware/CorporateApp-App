package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.R;
import com.corporateapp.adapter.AdapterAmbulance;

/**
 * Created by bitware on 24/10/17.
 */

public class FragmentMaternityHome extends Fragment {

    View view;
    RecyclerView rv_emergrncyContactList;
    EditText edt_search;
    AdapterAmbulance adapterEmergencyContactList;
    TextView tv_screenTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emergrncycontactlist, container, false);
        init();
        //   rv_emergrncyContactList.setAdapter(new AdapterBloodBank(getContext(), AppConstants.arrBloodBank));
        return view;
    }

    private void init() {
        tv_screenTitle = (TextView) view.findViewById(R.id.tv_screenTitle);
        tv_screenTitle.setText(getResources().getString(R.string.maternity_home));
        edt_search = (EditText) view.findViewById(R.id.edt_search);
        edt_search.setHint(getResources().getString(R.string.search_maternityhome));
        rv_emergrncyContactList = (RecyclerView) view.findViewById(R.id.rv_emergency_contact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_emergrncyContactList.setLayoutManager(layoutManager);

    }
}
