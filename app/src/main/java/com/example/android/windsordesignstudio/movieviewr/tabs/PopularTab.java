package com.example.android.windsordesignstudio.movieviewr.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.windsordesignstudio.movieviewr.R;

/**
 * Created by Rockwell Rice on 4/7/17.
 *
 * This file is responsible for the tab to display the most popular movies
 *
 * It may be overkill to devote a whole file just to this.
 * I decided to create this file because I was unsure how
 * much more I would need to develop these tabs
 * and I wanted the code to remain as agile as possible.
 *
 */

public class PopularTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popular_tab, container, false);
    }
}
