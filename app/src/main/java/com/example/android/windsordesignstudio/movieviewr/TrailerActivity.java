package com.example.android.windsordesignstudio.movieviewr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils;
import com.example.android.windsordesignstudio.movieviewr.utilities.OpenMovieJsonUtils;

import java.net.URL;

import static com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils.buildTrailerUrl;

/**
 * Created by rockwellrice on 5/18/17.
 */

public class TrailerActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler {

    private static final String TAG = TrailerActivity.class.getSimpleName();
    public String mMovieID;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private VideoView mMovieTrailerView;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mErrorMessageDisplay = (TextView) findViewById(R.id.viewr_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_reviews);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mMovieTrailerAdapter = new MovieTrailerAdapter(this);

        mRecyclerView.setAdapter(mMovieTrailerAdapter);

        Intent intentThatStartedThisActivity = getIntent();
        mMovieID = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        // request the reviews from the URL /movie/:id/reviews

        loadMovieTraier(mMovieID);
    }

    @Override
    public void onClick(String movieTrailer) {

    }

    /**
     * This method will get the user's preferred for displaying the movies.  Currently
     * only two options available: popular movies and highest rated movies.
     */
    private void loadMovieTraier(String id) {
        showMovieTrailerDataView();
        String queryID = id; // get reviews for movie
        new FetchMovieTrailerTask().execute(queryID);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieTrailerDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTrailerTask extends AsyncTask<String, Void, String[]> {
        // Set the loader animation
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            String url = params[0].toString();
            URL movieTrailerRequestUrl = buildTrailerUrl(url);

            try {
                String jsonMovieTrailerResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieTrailerRequestUrl);

                String[] simpleJsonMovieTrailerData = OpenMovieJsonUtils
                        .getSimpleMovieTrailerFromJson(TrailerActivity.this, jsonMovieTrailerResponse);

                return simpleJsonMovieTrailerData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        /*
        *
        * movieReviewData is the value returned from line 130 above.
        *
         */
        protected void onPostExecute(String[] movieTrailerData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieTrailerData != null) {
                mMovieTrailerAdapter.setMovieTrailerData(movieTrailerData);
            } else {
                showErrorMessage();
            }
        }
    }

}
