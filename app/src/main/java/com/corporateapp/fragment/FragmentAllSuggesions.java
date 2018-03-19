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
import com.corporateapp.adapter.AdapterAllSuggestion;
import com.corporateapp.models.allSuggestion.AllSuggesionResponce;
import com.corporateapp.models.allSuggestion.AllSuggestionDatum;
import com.corporateapp.webservice.APIRequest;
import com.corporateapp.webservice.AppConstants;
import com.corporateapp.webservice.BaseResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentAllSuggesions extends Fragment implements APIRequest.ResponseHandler {

    View view;
    RecyclerView lv_allComplaints;
    AdapterAllSuggestion adapterComplaints;
    TextView tv_noComplaintsAvailable;
    EditText edt_search;
    List<AllSuggestionDatum> arrSuggesionList, arrSuggesionListAfterSearch;

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
                arrSuggesionListAfterSearch = new ArrayList<AllSuggestionDatum>();
                if (arrSuggesionList.size() == 0) {
                    // AppUtil.showToastMsg(getContext(),"No Suggesions Available");
                } else {
                    for (int i = 0; i < arrSuggesionList.size(); i++) {
                        if (arrSuggesionList.get(i).getTitle().toLowerCase().contains(edt_search.getText().toString().toLowerCase())) {
                            arrSuggesionListAfterSearch.add(arrSuggesionList.get(i));
                        }
                    }
                    lv_allComplaints.setAdapter(new AdapterAllSuggestion(getContext(), arrSuggesionListAfterSearch));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void callAllComplaintsApi() {
        String allComplaintAPI = AppConstants.BASE_URL + "show_all_suggestions";
        System.out.println("All complaints api - " + allComplaintAPI);
        new APIRequest(getContext(), allComplaintAPI, new JSONObject(), this, AppConstants.API_ALL_SUGGETION, AppConstants.GET);
    }

    private void init() {
        tv_noComplaintsAvailable = (TextView) view.findViewById(R.id.tv_noComplaintsAvailable);
        lv_allComplaints = (RecyclerView) view.findViewById(R.id.rv_allComplaints);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        lv_allComplaints.setLayoutManager(layoutManager);
        Fragment fragment = getParentFragment();
        edt_search = (EditText) fragment.getView().findViewById(R.id.edt_search);
    }

    @Override
    public void onSuccess(BaseResponse response) {
        AllSuggesionResponce responce = (AllSuggesionResponce) response;
        arrSuggesionList = new ArrayList<>();
        if (responce.getStatus().equalsIgnoreCase("success")) {
            //AppUtil.showToastMsg(getContext(),responce.getDescription());
            if (responce.getSuggestionData().size() == 0) {
                tv_noComplaintsAvailable.setVisibility(View.VISIBLE);
                lv_allComplaints.setVisibility(View.INVISIBLE);
            } else {
                tv_noComplaintsAvailable.setVisibility(View.INVISIBLE);
                lv_allComplaints.setVisibility(View.VISIBLE);
                arrSuggesionList = responce.getSuggestionData();
                adapterComplaints = new AdapterAllSuggestion(getContext(), responce.getSuggestionData());
                lv_allComplaints.setAdapter(adapterComplaints);
            }
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
