package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.windsordesignstudio.movieviewr.MovieAdapter.MovieAdapterOnClickHandler;
import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract;
import com.example.android.windsordesignstudio.movieviewr.data.FavoritesDBHelper;
import com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils;
import com.example.android.windsordesignstudio.movieviewr.utilities.OpenMovieJsonUtils;

import java.net.URL;

import static com.example.android.windsordesignstudio.movieviewr.utilities.NetworkUtils.buildUrl;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler,MovieFavoritesAdapter.MovieFavoritesAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private MovieFavoritesAdapter mMovieFavoritesAdapter;
    private Toolbar toolbar;
    private SQLiteDatabase mDb;
    private static final int TASK_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Add the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Popular")); // This returns 0 when clicked
        tabLayout.addTab(tabLayout.newTab().setText("Highest Rating")); // This returns 1 when clicked
        tabLayout.addTab(tabLayout.newTab().setText("Favorites")); // This returns 2 when clicked
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int mPosition = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                if (mPosition == 1) {
                    mRecyclerView.setAdapter(mMovieAdapter);
                    // Show highest rated movies
                    loadMovieData("top_rated");
                } else if (mPosition == 2) {
                    loadFavoritesData();
                } else {
                    mRecyclerView.setAdapter(mMovieAdapter);
                    // Show popular movies
                    loadMovieData("popular");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

        /*
         * The MovieAdapter is responsible for linking our movie data with the Views that
         * will end up displaying our movie data.
         */
        mMovieAdapter = new MovieAdapter(this);
        mMovieFavoritesAdapter = new MovieFavoritesAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMovieAdapter);

        FavoritesDBHelper dbHelper = new FavoritesDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData("popular"); // Setting popular movies as the default movie filter
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param movie The movie that was clicked
     */
    @Override
    public void onClick(String movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * This method will get the user's preferred for displaying the movies.  Currently
     * only tow options available: popular movies and highest rated movies.
     */
    private void loadMovieData(String preference) {
        showMovieDataView();
        String typeOfQuery = preference; // Popular and Top-Rated will need to be set onClick
        new FetchMovieTask().execute(typeOfQuery);
    }

    private void loadFavoritesData() {
        mRecyclerView.setAdapter(mMovieFavoritesAdapter);
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {
        // Set the loader animation
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            String preference = params[0].toString();

                URL movieRequestUrl = buildUrl(preference);

                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieRequestUrl);

                    String[] simpleJsonMovieData = OpenMovieJsonUtils
                            .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                    return simpleJsonMovieData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }


    /**
     * Instantiates and returns a new AsyncTaskLoader with the given ID.
     * This loader will return favorites data as a Cursor or null if an error occurs.
     *
     * Implements the required callbacks to take care of loading data at all stages of loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the favorites data
            Cursor mFavoritesData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mFavoritesData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoritesData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {

                // Query and load all task data in the background; sort by priority
                try {
                    return getContentResolver().query(FavoritesContract.FavoriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoritesContract.FavoriteEntry.COLUMN_MOVIE_ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mFavoritesData = data;
                super.deliverResult(data);
            }
        };
    }
    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        mMovieFavoritesAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieFavoritesAdapter.swapCursor(null);
    }
}
