package com.example.android.windsordesignstudio.movieviewr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private String mMovie;
    private TextView mMovieDisplay;
    public ImageView mMovieImage;
    public TextView mMovieRating;
    public TextView mMovieRelease;
    public TextView mMovieTitle;
    public TextView mMovieDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = (TextView) findViewById(R.id.viewr_display_movie_title);
        mMovieRating = (TextView) findViewById(R.id.viewr_display_movie_rating);
        mMovieRelease = (TextView) findViewById(R.id.viewr_display_movie_release);
        mMovieDescription = (TextView) findViewById(R.id.viewr_display_movie_desc);
        mMovieImage = (ImageView) findViewById(R.id.viewr_display_movie_image);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovie = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                try {
                    JSONArray jsonArray = new JSONArray(mMovie);
                    mMovieTitle.setText("Title : " + jsonArray.getString(0));
                    mMovieRating.setText("Rating : " + jsonArray.getString(2));
                    mMovieDescription.setText("Synopsis : " + jsonArray.getString(3));
                    mMovieRelease.setText("Released : " +jsonArray.getString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
