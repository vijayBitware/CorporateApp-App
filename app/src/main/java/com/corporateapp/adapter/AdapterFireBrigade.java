package com.corporateapp.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.corporateapp.R;
import com.corporateapp.models.home.FireBrigade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bitware on 24/10/17.
 */

public class AdapterFireBrigade extends RecyclerView.Adapter<AdapterFireBrigade.ViewHolder> {

    List<FireBrigade> emergencyContactsList = new ArrayList<>();
    private Context context;

    public AdapterFireBrigade(Context context, List<FireBrigade> arrAmbulance) {
        this.context = context;
        this.emergencyContactsList = arrAmbulance;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_emergency_contact_list, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.tv_emergency_contact_name.setText(emergencyContactsList.get(i).getName());
        viewHolder.tv_emergency_contact_number.setText(emergencyContactsList.get(i).getNumber());
        viewHolder.tv_address.setText(emergencyContactsList.get(i).getAddress());
        viewHolder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + emergencyContactsList.get(i).getNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emergencyContactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_emergency_contact_name;
        TextView tv_emergency_contact_number, tv_address;
        ImageView img_emgergency_contact, iv_call;

        public ViewHolder(View view) {
            super(view);
            tv_emergency_contact_name = (TextView) view.findViewById(R.id.tv_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_emergency_contact_number = (TextView) view.findViewById(R.id.tv_number);
            iv_call = (ImageView) view.findViewById(R.id.iv_call);

        }
    }

}
