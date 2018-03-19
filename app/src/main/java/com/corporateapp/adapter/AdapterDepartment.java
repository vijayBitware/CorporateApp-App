package com.corporateapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corporateapp.R;
import com.corporateapp.models.home.Department;

import java.util.ArrayList;

/**
 * Created by bitware on 20/10/17.
 */

public class AdapterDepartment extends ArrayAdapter<Department> {

    Context context;
    LayoutInflater inflater;
    ViewHolder holder;
    ArrayList<Department> arrDepartment;
    View view;
    int currentPosition;

    public AdapterDepartment(Context context, int resource, ArrayList<Department> arrCart) {
        super(context, resource);
        this.context = context;
        this.arrDepartment = arrCart;
    }

    @Override
    public int getCount() {
        return arrDepartment.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_department, null);
            holder = new ViewHolder();

            holder.tv_department = (TextView) convertView.findViewById(R.id.tv_department);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_department.setText(arrDepartment.get(position).getName());

        return convertView;
    }

    public static class ViewHolder {
        TextView tv_department;
    }
}
