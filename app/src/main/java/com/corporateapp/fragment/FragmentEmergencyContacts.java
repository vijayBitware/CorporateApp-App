package com.corporateapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.corporateapp.R;
import com.corporateapp.adapter.AdapterEmergencyContacts;
import com.corporateapp.models.ModelEmergencyContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwini on 16/10/2017.
 */

public class FragmentEmergencyContacts extends Fragment {

    AdapterEmergencyContacts adapterEmergencyContacts;
    List<ModelEmergencyContacts> modelEmergencyContactsList, arrEmergrencyContact;
    RecyclerView recyclerView;
    View view;
    Context context;
    EditText edit_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);
        init();
        /*edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edit_search.getWindowToken(), 0);

                    arrEmergrencyContact = new ArrayList<ModelEmergencyContacts>();
                    for (int i = 0; i< modelEmergencyContactsList.size(); i++){
                        if (modelEmergencyContactsList.get(i).getEmergency_contact_name().contains(edit_search.getText().toString())){
                            arrEmergrencyContact.add(modelEmergencyContactsList.get(i));
                        }
                    }
                    recyclerView.setAdapter(new AdapterEmergencyContacts(getContext(),arrEmergrencyContact));
                }
                return false;
            }
        });*/

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrEmergrencyContact = new ArrayList<ModelEmergencyContacts>();
                for (int i = 0; i < modelEmergencyContactsList.size(); i++) {
                    if (modelEmergencyContactsList.get(i).getEmergency_contact_name().contains(edit_search.getText().toString())) {
                        arrEmergrencyContact.add(modelEmergencyContactsList.get(i));
                    }
                }
                recyclerView.setAdapter(new AdapterEmergencyContacts(getContext(), arrEmergrencyContact));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void init() {
        edit_search = (EditText) view.findViewById(R.id.edt_search);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_emergency_contact);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        modelEmergencyContactsList = new ArrayList<>();
        setEmergencyContactData();
    }

    private void setEmergencyContactData() {

        ModelEmergencyContacts modelEmergencyContacts = new ModelEmergencyContacts();
        modelEmergencyContacts.setEmergency_contact_name(getResources().getString(R.string.ambulance));
        modelEmergencyContacts.setEmergency_contact_number("9999999999");
        modelEmergencyContacts.setEmergency_contact_img(R.mipmap.icon_ambulance);
        modelEmergencyContacts.setTag("ambulance");
        modelEmergencyContactsList.add(modelEmergencyContacts);

        ModelEmergencyContacts modelEmergencyContacts1 = new ModelEmergencyContacts();
        modelEmergencyContacts1.setEmergency_contact_name(getResources().getString(R.string.police_station));
        modelEmergencyContacts1.setEmergency_contact_number("9999999999");
        modelEmergencyContacts1.setEmergency_contact_img(R.mipmap.icon_policestation);
        modelEmergencyContacts1.setTag("police station");
        modelEmergencyContactsList.add(modelEmergencyContacts1);

        ModelEmergencyContacts modelEmergencyContacts2 = new ModelEmergencyContacts();
        modelEmergencyContacts2.setEmergency_contact_name(getResources().getString(R.string.hospital));
        modelEmergencyContacts2.setEmergency_contact_number("9999999999");
        modelEmergencyContacts2.setEmergency_contact_img(R.mipmap.icon_hospital);
        modelEmergencyContacts2.setTag("hospital");
        modelEmergencyContactsList.add(modelEmergencyContacts2);

        ModelEmergencyContacts modelEmergencyContacts3 = new ModelEmergencyContacts();
        modelEmergencyContacts3.setEmergency_contact_name(getResources().getString(R.string.blood_bank));
        modelEmergencyContacts3.setEmergency_contact_number("9999999999");
        modelEmergencyContacts3.setEmergency_contact_img(R.mipmap.icon_blood);
        modelEmergencyContacts3.setTag("blood bank");
        modelEmergencyContactsList.add(modelEmergencyContacts3);


        ModelEmergencyContacts modelEmergencyContacts4 = new ModelEmergencyContacts();
        modelEmergencyContacts4.setEmergency_contact_name(getResources().getString(R.string.fire_brigade));
        modelEmergencyContacts4.setEmergency_contact_number("9999999999");
        modelEmergencyContacts4.setEmergency_contact_img(R.mipmap.icon_firebrigade);
        modelEmergencyContacts4.setTag("fire brigade");
        modelEmergencyContactsList.add(modelEmergencyContacts4);

        ModelEmergencyContacts modelEmergencyContacts5 = new ModelEmergencyContacts();
        modelEmergencyContacts5.setEmergency_contact_name(getResources().getString(R.string.water_supply));
        modelEmergencyContacts5.setEmergency_contact_number("9999999999");
        modelEmergencyContacts5.setEmergency_contact_img(R.mipmap.ic_water);
        modelEmergencyContacts5.setTag("water supply");
        modelEmergencyContactsList.add(modelEmergencyContacts5);

        ModelEmergencyContacts modelEmergencyContacts6 = new ModelEmergencyContacts();
        modelEmergencyContacts6.setEmergency_contact_name(getResources().getString(R.string.maternity_home));
        modelEmergencyContacts6.setEmergency_contact_number("9999999999");
        modelEmergencyContacts6.setEmergency_contact_img(R.mipmap.ic_maternityhome);
        modelEmergencyContacts6.setTag("maternity home");
        modelEmergencyContactsList.add(modelEmergencyContacts6);


        adapterEmergencyContacts = new AdapterEmergencyContacts(getActivity(), modelEmergencyContactsList);
        recyclerView.setAdapter(adapterEmergencyContacts);
    }


}
