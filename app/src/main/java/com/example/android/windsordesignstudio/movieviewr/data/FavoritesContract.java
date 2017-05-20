package com.example.android.windsordesignstudio.movieviewr.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rockwellrice on 5/19/17.
 */

public class FavoritesContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.movieviewr";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

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
