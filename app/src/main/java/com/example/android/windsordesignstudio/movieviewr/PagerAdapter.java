package com.example.android.windsordesignstudio.movieviewr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.windsordesignstudio.movieviewr.tabs.HighestRatingTab;
import com.example.android.windsordesignstudio.movieviewr.tabs.PopularTab;

/**
 * Created by rockwellrice on 4/7/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    // Tab count
    int mNumberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PopularTab tab1 = new PopularTab();
                return tab1;
            case 1:
                HighestRatingTab tab2 = new HighestRatingTab();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
