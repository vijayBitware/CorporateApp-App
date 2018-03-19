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
import com.corporateapp.adapter.AdapterComplaints;
import com.corporateapp.models.complaints.ComplaintDatum;
import com.corporateapp.models.complaints.ComplaintResponce;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentAllComplaint extends Fragment implements APIRequest.ResponseHandler {

    View view;
    RecyclerView lv_allComplaints;
    AdapterComplaints adapterComplaints;
    TextView tv_noComplaintsAvailable;
    EditText edt_search;
    List<ComplaintDatum> arrComplaintList, arrComplaintListAfterSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allcomplaints, container, false);
        init();
        if (AppUtil.isConnectingToInternet(getContext())) {
            callAllComplaintsApi();
        } else {
            AppUtil.showToastMsg(getContext(), getResources().getString(R.string.no_internet));
        }
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrComplaintListAfterSearch = new ArrayList<ComplaintDatum>();
                if (arrComplaintList.size() == 0) {
                    //  AppUtil.showToastMsg(getContext(),"No Complaints Available");
                } else {
                    for (int i = 0; i < arrComplaintList.size(); i++) {
                        if (arrComplaintList.get(i).getTitle().toLowerCase().contains(edt_search.getText().toString().toLowerCase())) {
                            arrComplaintListAfterSearch.add(arrComplaintList.get(i));
                        }
                    }
                    lv_allComplaints.setAdapter(new AdapterComplaints(getContext(), arrComplaintListAfterSearch));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void callAllComplaintsApi() {
        String allComplaintAPI = AppConstants.BASE_URL + "show_all_complaints";
        System.out.println("All complaints api - " + allComplaintAPI);
        new APIRequest(getContext(), allComplaintAPI, new JSONObject(), this, AppConstants.API_ALLCOMPLAINTS, AppConstants.GET);
    }

    private void init() {
        lv_allComplaints = (RecyclerView) view.findViewById(R.id.rv_allComplaints);
        tv_noComplaintsAvailable = (TextView) view.findViewById(R.id.tv_noComplaintsAvailable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        lv_allComplaints.setLayoutManager(layoutManager);
        Fragment fragment = getParentFragment();
        edt_search = (EditText) fragment.getView().findViewById(R.id.edt_search);

    }

    @Override
    public void onSuccess(BaseResponse response) {
        ComplaintResponce responce = (ComplaintResponce) response;
        arrComplaintList = new ArrayList<>();
        if (responce.getStatus().equalsIgnoreCase("success")) {
            arrComplaintList = responce.getComplaintData();
            if (responce.getComplaintData().size() == 0) {
                tv_noComplaintsAvailable.setText(getResources().getString(R.string.no_complaints));
                tv_noComplaintsAvailable.setVisibility(View.VISIBLE);
                lv_allComplaints.setVisibility(View.INVISIBLE);
            } else {
                tv_noComplaintsAvailable.setVisibility(View.INVISIBLE);
                lv_allComplaints.setVisibility(View.VISIBLE);
                adapterComplaints = new AdapterComplaints(getContext(), responce.getComplaintData());
                lv_allComplaints.setAdapter(adapterComplaints);
            }
        } else {
            tv_noComplaintsAvailable.setText(getResources().getString(R.string.no_complaints));
            tv_noComplaintsAvailable.setVisibility(View.VISIBLE);
            lv_allComplaints.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
