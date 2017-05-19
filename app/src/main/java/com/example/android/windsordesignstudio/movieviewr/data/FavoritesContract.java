package com.example.android.windsordesignstudio.movieviewr.data;

import android.provider.BaseColumns;

/**
 * Created by rockwellrice on 5/19/17.
 */

public class FavoritesContract {
    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class FavoriteEntry implements BaseColumns {
        // Task table and column names
        public static final String TABLE_NAME = "favorites";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_POSTER_FULL_PATH = "posterFullPath";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}
