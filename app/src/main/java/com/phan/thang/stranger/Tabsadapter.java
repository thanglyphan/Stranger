package com.phan.thang.stranger;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by thang on 14.08.2016.
 */
public class Tabsadapter  extends FragmentStatePagerAdapter {

    private int TOTAL_TABS = 3;

    public Tabsadapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
                return new ChannelsFragment();

            case 1:
                return new MyAccountFragment();

            case 2:
                return new SettingsFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}