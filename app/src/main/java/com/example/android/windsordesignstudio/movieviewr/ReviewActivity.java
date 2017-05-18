package com.example.android.windsordesignstudio.movieviewr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils;
import com.example.android.windsordesignstudio.movieviewr.utilities.OpenMovieJsonUtils;

import java.net.URL;

import static com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils.buildReviewUrl;

/**
 * Created by rockwellrice on 5/11/17.
 */

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    public String mMovieID;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private TextView mReviewView;
    private String[] mMovieReviewsData;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        mErrorMessageDisplay = (TextView) findViewById(R.id.viewr_error_message_display);
        mReviewView = (TextView) findViewById(R.id.review_movie);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_reviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        Intent intentThatStartedThisActivity = getIntent();
        mMovieID = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        // request the reviews from the URL /movie/:id/reviews

        loadMovieReview(mMovieID);
    }

    /**
     * This method will get the user's preferred for displaying the movies.  Currently
     * only two options available: popular movies and highest rated movies.
     */
    private void loadMovieReview(String id) {
        showMovieReviewDataView();
        String queryID = id; // get reviews for movie
        new FetchMovieReviewTask().execute(queryID);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mReviewView.setVisibility(View.INVISIBLE);
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
    private void showMovieReviewDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mReviewView.setVisibility(View.VISIBLE);
    }

    public class FetchMovieReviewTask extends AsyncTask<String, Void, String[]> {
        // Set the loader animation
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            String url = params[0].toString();
            URL movieReviewRequestUrl = buildReviewUrl(url);

            try {
                String jsonMovieReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieReviewRequestUrl);

                String[] simpleJsonMovieReviewData = OpenMovieJsonUtils
                        .getSimpleMovieReviewFromJson(ReviewActivity.this, jsonMovieReviewResponse);

                return simpleJsonMovieReviewData;

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
        protected void onPostExecute(String[] movieReviewData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieReviewData != null) {
                showMovieReviewDataView();
                mMovieReviewsData = movieReviewData;

                mReviewView.setText(mMovieReviewsData[0]);

            } else {
                showErrorMessage();
            }
        }
    }
}
