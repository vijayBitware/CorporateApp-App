package com.corporateapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.corporateapp.AppUtils.AppUtil;
import com.corporateapp.R;
import com.corporateapp.adapter.AdapterCompalintTabs;
import com.corporateapp.webservice.AppConstants;

/**
 * Created by bitware on 13/10/17.
 */

public class FragmentSuggesions extends Fragment implements TabLayout.OnTabSelectedListener {
    View view;
    TabLayout tabLayout;
    AdapterCompalintTabs adapterCompalint;
    EditText edtSearch;
    // ViewPager viewPager;
    private int mPreviousScrollState;
    private int mScrollState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaints, container, false);
        init();
        return view;
    }

    private void init() {
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        edtSearch.setHint(getResources().getString(R.string.search_suggesion));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_mysuggesions)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_allsuggesions)));

        tabLayout.setOnTabSelectedListener(this);

        if (AppConstants.curentFragmentPositionInSuggesion == 0) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            AppUtil.replaceFragmentForTab(new FragmentMySuggesions(), getChildFragmentManager(), null);
        } else if (AppConstants.curentFragmentPositionInSuggesion == 1) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
            AppUtil.replaceFragmentForTab(new FragmentAllSuggesions(), getChildFragmentManager(), null);

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e("Tag", "tab position >" + tab.getPosition());
        if (tab.getPosition() == 0) {
            AppConstants.curentFragmentPositionInSuggesion = 0;
            AppUtil.replaceFragmentForTab(new FragmentMySuggesions(), getChildFragmentManager(), null);
        } else if (tab.getPosition() == 1) {
            AppConstants.curentFragmentPositionInSuggesion = 1;
            AppUtil.replaceFragmentForTab(new FragmentAllSuggesions(), getChildFragmentManager(), null);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
