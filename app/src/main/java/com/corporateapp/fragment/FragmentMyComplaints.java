package com.corporateapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppSharedPreference;
import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.SubmitComplaintFormActivity;
import com.corporateapp.adapter.AdapterComplaints;
import com.corporateapp.models.complaints.ComplaintDatum;
import com.corporateapp.models.complaints.ComplaintResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentMyComplaints extends Fragment implements APIRequest.ResponseHandler {

    View view;
    TextView tv_noComplaintsAvailable;
    FloatingActionButton fab_submitComplaint;
    RecyclerView rv_mycomplaints;
    AdapterComplaints adapterComplaints;
    EditText edt_search;
    List<ComplaintDatum> arrMyComplaintList, arrComplaintListAfterSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mycomplaint, container, false);
        init();
        fab_submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SubmitComplaintFormActivity.class));
            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrComplaintListAfterSearch = new ArrayList<ComplaintDatum>();
                if (arrMyComplaintList.size() == 0) {
//                    AppUtil.showToastMsg(getContext(),"No Complaints Available");
                } else {
                    for (int i = 0; i < arrMyComplaintList.size(); i++) {
                        if (arrMyComplaintList.get(i).getTitle().contains(edt_search.getText().toString())) {
                            arrComplaintListAfterSearch.add(arrMyComplaintList.get(i));
                        }
                    }
                    rv_mycomplaints.setAdapter(new AdapterComplaints(getContext(), arrComplaintListAfterSearch));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void callMyComplaintsApi() {
        String mycompaintApi = AppConstants.BASE_URL + "show_my_complaints";
        System.out.println("my complaints api - " + mycompaintApi);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", AppSharedPreference.getValue(getContext(), "token"));
            Log.e("Tag", String.valueOf(jsonObject));
            new APIRequest(getContext(), mycompaintApi, jsonObject, this, AppConstants.API_ALLCOMPLAINTS, AppConstants.POST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        AppUtil.flagSuggestioComplaint = "complaint";
        tv_noComplaintsAvailable = (TextView) view.findViewById(R.id.tv_noComplaintsAvailable);
        tv_noComplaintsAvailable.setVisibility(View.VISIBLE);
        rv_mycomplaints = (RecyclerView) view.findViewById(R.id.rv_myComplaints);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_mycomplaints.setLayoutManager(layoutManager);
        fab_submitComplaint = (FloatingActionButton) view.findViewById(R.id.fab_submitForm);
        Fragment fragment = getParentFragment();
        edt_search = (EditText) fragment.getView().findViewById(R.id.edt_search);

    }

    @Override
    public void onSuccess(BaseResponse response) {
        ComplaintResponce complaintsResponce = (ComplaintResponce) response;
        arrMyComplaintList = new ArrayList<>();
        if (complaintsResponce.getStatus().equalsIgnoreCase("success")) {
            arrMyComplaintList = complaintsResponce.getComplaintData();
            tv_noComplaintsAvailable.setVisibility(View.INVISIBLE);
            rv_mycomplaints.setVisibility(View.VISIBLE);
            adapterComplaints = new AdapterComplaints(getContext(), arrMyComplaintList);
            rv_mycomplaints.setAdapter(adapterComplaints);
        } else {
            tv_noComplaintsAvailable.setText(getResources().getString(R.string.no_complaints));
            tv_noComplaintsAvailable.setVisibility(View.VISIBLE);
            rv_mycomplaints.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppUtil.isConnectingToInternet(getContext())) {
            callMyComplaintsApi();
        } else {
            AppUtil.showToastMsg(getContext(), getContext().getString(R.string.no_internet));
        }
    }
}
