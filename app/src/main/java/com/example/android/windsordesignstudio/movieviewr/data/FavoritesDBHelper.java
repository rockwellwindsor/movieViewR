package com.example.android.windsordesignstudio.movieviewr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract.FavoriteEntry;
/**
 * Created by rockwellrice on 5/19/17.
 */

public class FavoritesDBHelper extends SQLiteOpenHelper {
    // The name of the database
    private static final String DATABASE_NAME = "favoritesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 2;


    // Constructor
    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "   + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID                     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_MOVIE_ID         + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_MOVIE_TITLE      + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_POSTER_FULL_PATH + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_VOTE_AVERAGE     + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_PLOT             + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_RELEASE_DATE     + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

}
