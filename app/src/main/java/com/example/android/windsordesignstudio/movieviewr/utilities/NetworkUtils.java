package com.example.android.windsordesignstudio.movieviewr.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rockwell Rice on 4/5/17.
 *
 *  This class will be responsible for communicating with the movie service
 *
 *  The code in this file was heavily influenced by the course material.
 *
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    // The format we want our API to return
    private static final String format = "json";
    private static final String APIKey = "3c6092d60715cfad623d1afe909fe090";

    /**
     * Builds the URL used to talk to the movie API.
     *
     * @param typeOfQuery Will return either popular movies or top rated movies.
     * @return The URL to use to query the movie server.
     *
     * This is a simplified version of the buildUrl method from the course material.
     * I did not need as many parameters so I simplified it to just the type of query needed.
     */
    public static URL buildUrl(String typeOfQuery) {
        URL url = null;

        try {
            url = new URL("http://api.themoviedb.org/3/movie/" + typeOfQuery + "?api_key="+ APIKey +"");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     *
     * This code was taken directly from the course material
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
