package com.example.android.windsordesignstudio.movieviewr.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.windsordesignstudio.movieviewr.R;

/**
 * Created by rockwellrice on 5/11/17.
 */

public class FavoritesTab extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_tab, container, false);
    }
}
