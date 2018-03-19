package com.corporateapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.activity.HomeActivity;
import com.corporateapp.fragment.FragmentAmbulance;
import com.corporateapp.fragment.FragmentBloodBanl;
import com.corporateapp.fragment.FragmentFireBrigade;
import com.corporateapp.fragment.FragmentHospital;
import com.corporateapp.fragment.FragmentMaternityHome;
import com.corporateapp.fragment.FragmentPoliceStation;
import com.corporateapp.fragment.FragmentWaterSupply;
import com.corporateapp.models.ModelEmergencyContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwini on 16/10/2017.
 */

public class AdapterEmergencyContacts extends RecyclerView.Adapter<AdapterEmergencyContacts.ViewHolder> {

    List<ModelEmergencyContacts> emergencyContactsList = new ArrayList<>();
    private Context context;


    public AdapterEmergencyContacts(Context context, List<ModelEmergencyContacts> emergencyContactsList) {
        this.emergencyContactsList = emergencyContactsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emergency_contacts_row, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        final ModelEmergencyContacts modelEmergencyContacts = emergencyContactsList.get(i);
        viewHolder.tv_emergency_contact_name.setText(modelEmergencyContacts.getEmergency_contact_name());
        viewHolder.img_emgergency_contact.setImageResource(emergencyContactsList.get(i).getEmergency_contact_img());
        viewHolder.ll_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (emergencyContactsList.get(i).getTag()) {
                    case "ambulance":
                        AppUtil.replaceFragment(new FragmentAmbulance(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "hospital":
                        AppUtil.replaceFragment(new FragmentHospital(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "police station":
                        AppUtil.replaceFragment(new FragmentPoliceStation(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "blood bank":
                        AppUtil.replaceFragment(new FragmentBloodBanl(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "fire brigade":
                        AppUtil.replaceFragment(new FragmentFireBrigade(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "water supply":
                        AppUtil.replaceFragment(new FragmentWaterSupply(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                    case "maternity home":
                        AppUtil.replaceFragment(new FragmentMaternityHome(), ((HomeActivity) context).getSupportFragmentManager(), null);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return emergencyContactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_emergency_contact_name;
        TextView tv_emergency_contact_number;
        ImageView img_emgergency_contact, iv_call;
        LinearLayout ll_row;

        public ViewHolder(View view) {
            super(view);
            img_emgergency_contact = (ImageView) view.findViewById(R.id.img_emgergency_contact);
            tv_emergency_contact_name = (TextView) view.findViewById(R.id.tv_emergency_contact_name);
            ll_row = (LinearLayout) view.findViewById(R.id.ll_row);
            //  tv_emergency_contact_number= (TextView)view.findViewById(R.id.tv_emergency_contact_number);
            // iv_call = (ImageView) view.findViewById(R.id.iv_call);

        }
    }

}




