package com.balbadak.nexquickpro;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter   {


    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                fragment_order_list fragmentorderlist = new fragment_order_list();
                return fragmentorderlist;
            case 1:
                fragment_route fragmentroute = new fragment_route();
                return fragmentroute;
            case 2:
                fragment_calculate fragmentcalculate = new fragment_calculate();
                return fragmentcalculate;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}


