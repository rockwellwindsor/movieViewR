package com.example.android.windsordesignstudio.movieviewr.utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rockwell Rice on 4/5/17.
 *
 *  This class will be responsible for communicating with the movie serice
 *
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";

    final static String TOP_RATED_MOVIES = "top_rated?api_key=";
    final static String POPULAR_MOVIES = "popular?api_key=";

    // The format we want our API to return
    private static final String format = "json";
    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param typeOfQuery Will return either popular movies or top rated movies.
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl(String typeOfQuery) {
//        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
//                .appendQueryParameter(POPULAR_MOVIES, typeOfQuery)
//                .appendQueryParameter(API_KEY, apiKey)
//                .build();

        URL url = null;

        try {
            url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=3c6092d60715cfad623d1afe909fe090");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Built URI " + url);

        return url;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
