package com.example.android.windsordesignstudio.movieviewr;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract;
import com.example.android.windsordesignstudio.movieviewr.data.FavoritesDBHelper;
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
    public Button mSaveAsFavoriteButton;
    public TextView mDisplayReviewsTextView;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    Log.d(TAG, "HERE : 1");
        // Creating variables for each of the items we need to display
        mMovieTitle = (TextView) findViewById(R.id.viewr_display_movie_title);
        mMovieRating = (TextView) findViewById(R.id.viewr_display_movie_rating);
        mMovieRelease = (TextView) findViewById(R.id.viewr_display_movie_release);
        mMovieDescription = (TextView) findViewById(R.id.viewr_display_movie_desc);
        mMovieImage = (ImageView) findViewById(R.id.viewr_display_movie_image);
        mViewTrailerButton = (Button) findViewById(R.id.view_trailer_button);
        mViewReviewsButton = (Button) findViewById(R.id.view_reviews_button);
        mSaveAsFavoriteButton = (Button) findViewById(R.id.view_add_favorite_button);
        mDisplayReviewsTextView = (TextView) findViewById(R.id.viewr_display_movie_review);

        FavoritesDBHelper dbHelper = new FavoritesDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Log.d(TAG, "HERE : 2");
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            Log.d(TAG, "HERE : 3");
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                Log.d(TAG, "HERE : 4");
                /*
                * Getting the context to use picasso to display the image was a little confusing for me.
                * This solution came from: http://stackoverflow.com/questions/28754499/how-to-get-context-in-an-intent-service
                * and appeared to work, although admittedly I am a little unsure exactly how it solved the issue.
                */

                Context context = getApplicationContext();

                mMovie = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                Log.d(TAG, "HERE : 5" + mMovie);
                try {
                    Log.d(TAG, "HERE : 6" + mMovie.getClass().getName());
                    JSONArray jsonArray = new JSONArray(mMovie);
                    Log.d(TAG, "HERE : 7" + jsonArray);
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

        mSaveAsFavoriteButton = (Button) findViewById(R.id.view_add_favorite_button);
        mSaveAsFavoriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    JSONArray jsonArray = new JSONArray(mMovie);
                    addToFavorites(mMovie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addToFavorites(String movie) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(movie);
            String movieTitle = jsonArray.getString(0);
            String posterPath = jsonArray.getString(1);
            String averageRating = jsonArray.getString(2);
            String plot = jsonArray.getString(3);
            String releaseDate = jsonArray.getString(4);
            String movieID = jsonArray.getString(5);

            // Enter into database
            ContentValues cv = new ContentValues();
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_ID, movieID);
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE, movieTitle);
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_POSTER_FULL_PATH, posterPath);
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, averageRating);
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_PLOT, plot);
            cv.put(FavoritesContract.FavoriteEntry.COLUMN_RELEASE_DATE, releaseDate);

            Uri uri = getContentResolver().insert(FavoritesContract.FavoriteEntry.CONTENT_URI, cv);

//            long rowInserted = mDb.insert(FavoritesContract.FavoriteEntry.TABLE_NAME, null, cv);
            Context context = getApplicationContext();
//
            if(uri != null) {
                Toast.makeText(context, "" + movieTitle + " has been added to your favorites.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadMovieReviews(String id) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    private void loadMovieTrailer(String id) {
        Intent intent = new Intent(this, TrailerActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }
}
