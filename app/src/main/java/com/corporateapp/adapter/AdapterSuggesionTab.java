package com.corporateapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.corporateapp.fragment.FragmentAllSuggesions;
import com.corporateapp.fragment.FragmentMySuggesions;

/**
 * Created by bitware on 13/10/17.
 */

public class AdapterSuggesionTab extends FragmentPagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AdapterSuggesionTab(FragmentManager fm, int tabCount) {
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
                fragment = new FragmentMySuggesions();
                return fragment;
            case 1:
                fragment = new FragmentAllSuggesions();
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
