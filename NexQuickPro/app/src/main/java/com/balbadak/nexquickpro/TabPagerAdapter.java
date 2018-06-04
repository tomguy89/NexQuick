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
                Fragment1 fragment1 = new Fragment1();
                return fragment1;
            case 1:
                Fragment2 fragment2 = new Fragment2();
                return fragment2;
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}


