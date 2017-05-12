package com.example.android.windsordesignstudio.movieviewr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by rockwellrice on 5/11/17.
 */

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    public String mMovieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intentThatStartedThisActivity = getIntent();
        mMovieID = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        Log.d(TAG, "HERE : " + mMovieID + "");
    }
}
