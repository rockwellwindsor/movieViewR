package com.example.android.windsordesignstudio.movieviewr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.windsordesignstudio.movieviewr.tabs.HighestRatingTab;
import com.example.android.windsordesignstudio.movieviewr.tabs.PopularTab;

/**
 * Created by Rockwell Rice on 4/7/17.
 *
 * The beginnings of this code was taken from a blog post
 * at: http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/ and adapted
 * to fit the needs of this application.
 *
 * This file is responsible for the two tabs below the toolbar that allow a user to switch between
 * the most popular movies and the highest rated movies.
 *
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
