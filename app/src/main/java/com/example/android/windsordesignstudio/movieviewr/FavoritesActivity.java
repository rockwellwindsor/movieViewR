package com.example.android.windsordesignstudio.movieviewr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract;
import com.example.android.windsordesignstudio.movieviewr.data.FavoritesDBHelper;

/**
 * Created by rockwellrice on 5/19/17.
 */

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = FavoritesActivity.class.getSimpleName();
    private MovieFavoritesAdapter mFavoritesMovieAdapter;
    private SQLiteDatabase mDb;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        /*
         *  Create a variable to reference the RecyclerView.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);

        /*
        *  This TextView is used to display errors and will be hidden if there are no errors
        */
        mErrorMessageDisplay = (TextView) findViewById(R.id.viewr_error_message_display);

        /*
         *
         */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        FavoritesDBHelper dbHelper = new FavoritesDBHelper(this);

        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getFavorites();

        /*
         * The MovieAdapter is responsible for linking our movie data with the Views that
         * will end up displaying our movie data.
         */
//        mFavoritesMovieAdapter = new MovieFavoritesAdapter(this);
        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
//        mRecyclerView.setAdapter(mFavoritesMovieAdapter);

    }

    private Cursor getFavorites() {
        // Call query on mDb passing in the table name and projection String [] order by ID
        return mDb.query(
                FavoritesContract.FavoriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoritesContract.FavoriteEntry._ID
        );
    }

    public void onClick(String movie) {

    }
}
