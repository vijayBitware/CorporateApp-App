package com.corporateapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.corporateapp.fragment.FragmentAllComplaint;
import com.corporateapp.fragment.FragmentMyComplaints;
import com.corporateapp.webservice.AppConstants;

/**
 * Created by bitware on 13/10/17.
 */

public class AdapterCompalintTabs extends FragmentPagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AdapterCompalintTabs(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new FragmentMyComplaints();
                return fragment;
            case 1:
                AppConstants.callService = 0;
                fragment = new FragmentAllComplaint();
                return fragment;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
