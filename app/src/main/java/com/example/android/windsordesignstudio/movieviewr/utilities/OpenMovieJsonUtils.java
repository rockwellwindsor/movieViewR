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

    public static String[] getSimpleMovieReviewFromJson(Context context, String movieReviewJsonStr)
            throws JSONException {
        final String OWM_MESSAGE_CODE = "cod";
        final String OWM_LIST = "list";

        String[] movieReviewData = null;

        JSONObject movieReviewJSON = new JSONObject(movieReviewJsonStr);
        /* Is there an error? */
        if (movieReviewJSON.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieReviewJSON.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieReviewArray = movieReviewJSON.getJSONArray("results");
        movieReviewData = new String[movieReviewArray.length()];
        // Loop through the array
        for (int i = 0; i < movieReviewArray.length(); i++) {

            JSONObject movieReviewDetails = movieReviewArray.getJSONObject(i);

            String reviewerName = movieReviewDetails.getString("author");
            String review = movieReviewDetails.getString("content");

            movieReviewData[i] = "[\"" + reviewerName + "\",\"" + review + "\"]";
        }

        return movieReviewData;
    }

    public static String[] getSimpleMovieTrailerFromJson(Context context, String movieTrailerJsonStr)
            throws JSONException {
        final String OWM_MESSAGE_CODE = "cod";
        final String OWM_LIST = "list";

        String[] movieTrailerData = null;

        JSONObject movieTrailerJSON = new JSONObject(movieTrailerJsonStr);

        /* Is there an error? */
        if (movieTrailerJSON.has(OWM_MESSAGE_CODE)) {
            int errorCode = movieTrailerJSON.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieTrailerArray = movieTrailerJSON.getJSONArray("results");
        movieTrailerData = new String[movieTrailerArray.length()];
        // Loop through the array
        for (int i = 0; i < movieTrailerArray.length(); i++) {

            JSONObject movieTrailerDetails = movieTrailerArray.getJSONObject(i);

            String trailerID = movieTrailerDetails.getString("id");
            String trailerKey = movieTrailerDetails.getString("key");
            String trailerName = movieTrailerDetails.getString("name");
            String trailerSite = movieTrailerDetails.getString("site");
            String trailerSize = movieTrailerDetails.getString("size");
            String trailerType = movieTrailerDetails.getString("type");

            movieTrailerData[i] = "[\"" + trailerID + "\",\"" + trailerKey + "\",\"" + trailerName + "\",\"" + trailerSite + "\",\"" + trailerSize + "\",\"" + trailerType + "\"]";
        }

        return movieTrailerData;
    }

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

            String movieID = movieDetails.getString("id");
            String moviePosterPath = movieDetails.getString("poster_path");
            String fullMoviePosterPath = "http://image.tmdb.org/t/p/w185//" + moviePosterPath;
            String movieTitle = movieDetails.getString("title");
            String movieVoteAverage = movieDetails.getString("vote_average");
            String moviePlot = movieDetails.getString("overview");
            String movieReleaseDate = movieDetails.getString("release_date");

            parsedMovieData[i] = "[\"" + movieTitle + "\",\"" + fullMoviePosterPath + "\",\"" + movieVoteAverage + "\",\"" + moviePlot + "\",\"" + movieReleaseDate + "\",\"" + movieID + "\"]";

        }

        return parsedMovieData;

    }
}
