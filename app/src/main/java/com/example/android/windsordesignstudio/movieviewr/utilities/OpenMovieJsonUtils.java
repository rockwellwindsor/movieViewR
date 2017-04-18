package com.example.android.windsordesignstudio.movieviewr.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Rockwell Rice on 4/5/17.
 *
 * This code was relied heavily on the code from the course material.
 */
public final class OpenMovieJsonUtils {

    private static final String TAG = OpenMovieJsonUtils.class.getSimpleName();

    public static String[] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String OWM_MESSAGE_CODE = "cod";
        final String OWM_LIST = "list";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        /* Is there an error? */
        if (movieJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
//                    Log.d(TAG, "OK ?");
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
//                    Log.d(TAG, "NOT OK ?");
                    return null;
                default:
                    /* Server probably down */
//                    Log.d(TAG, "REALLY NOT OK ?");
                    return null;
            }
        }

        // Needs to know what to grab, in this case it was labelled "results"
        JSONArray movieArray = movieJson.getJSONArray("results");

        parsedMovieData = new String[movieArray.length()];

        // Loop through the array
        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movieDetails = movieArray.getJSONObject(i);

            String moviePosterPath = movieDetails.getString("poster_path");
            String fullMoviePosterPath = "http://image.tmdb.org/t/p/w185//" + moviePosterPath;
            String movieTitle = movieDetails.getString("title");
            String movieVoteAverage = movieDetails.getString("vote_average");
            String moviePlot = movieDetails.getString("overview");
            String movieReleaseDate = movieDetails.getString("release_date");

            parsedMovieData[i] = "[\"" + movieTitle + "\",\"" + fullMoviePosterPath + "\",\"" + movieVoteAverage + "\",\"" + moviePlot + "\",\"" + movieReleaseDate + "\"]";

        }

        return parsedMovieData;

    }
}
