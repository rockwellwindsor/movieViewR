package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public Button mViewTrailerButton;
    public Button mViewReviewsButton;
    public TextView mDisplayReviewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Creating variables for each of the items we need to display
        mMovieTitle = (TextView) findViewById(R.id.viewr_display_movie_title);
        mMovieRating = (TextView) findViewById(R.id.viewr_display_movie_rating);
        mMovieRelease = (TextView) findViewById(R.id.viewr_display_movie_release);
        mMovieDescription = (TextView) findViewById(R.id.viewr_display_movie_desc);
        mMovieImage = (ImageView) findViewById(R.id.viewr_display_movie_image);
        mViewTrailerButton = (Button) findViewById(R.id.view_trailer_button);
        mViewReviewsButton = (Button) findViewById(R.id.view_reviews_button);
        mDisplayReviewsTextView = (TextView) findViewById(R.id.viewr_display_movie_review);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                /*
                * Getting the context to use picasso to display the image was a little confusing for me.
                * This solution came from: http://stackoverflow.com/questions/28754499/how-to-get-context-in-an-intent-service
                * and appeared to work, although admittedly I am a little unsure exactly how it solved the issue.
                */

                Context context = getApplicationContext();

                mMovie = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                try {
                    JSONArray jsonArray = new JSONArray(mMovie);
                    // Display title
                    mMovieTitle.setText(jsonArray.getString(0));
                    // Display the Poster
                    // Using Picasso to set the poster image in the ImageView
                    Picasso.with(context).load(jsonArray.getString(1)).resize(600, 900).into(mMovieImage);
                    // Display the Rating
                    mMovieRating.setText("Rating : " + jsonArray.getString(2));
                    // Display the plot synopsis
                    mMovieDescription.setText("Synopsis : " + jsonArray.getString(3));
                    // Display the release date
                    mMovieRelease.setText(jsonArray.getString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // set onclick listener for the buttons
        mViewTrailerButton = (Button) findViewById(R.id.view_trailer_button);

        mViewTrailerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    JSONArray jsonArray = new JSONArray(mMovie);
                    String movieID = jsonArray.getString(5);
                    loadMovieTrailer(movieID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mViewReviewsButton = (Button) findViewById(R.id.view_reviews_button);
        mViewReviewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    JSONArray jsonArray = new JSONArray(mMovie);
                    String movieID = jsonArray.getString(5);
                    loadMovieReviews(movieID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void loadMovieReviews(String id) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    private void loadMovieTrailer(String id) {
        //
    }
}
