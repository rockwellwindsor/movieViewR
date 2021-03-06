package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Rockwell Rice on 4/5/17.
 *
 * This code was inspired by, sometimes taken directly from, the course material for the first project
 *
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private String[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;


    public interface MovieAdapterOnClickHandler {
        void onClick(String movie);
    }


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final ImageView mMoviePoster;

        public MovieAdapterViewHolder(View view) {
            super(view);
            // Grabbing the ImageView and setting an onClick listener
            mMoviePoster = (ImageView) view.findViewById(R.id.viewr_movie_data_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String selectedMovie = mMovieData[adapterPosition];
            mClickHandler.onClick(selectedMovie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movie = mMovieData[position];

        Context context = movieAdapterViewHolder.mMoviePoster.getContext();

        try {
            JSONArray jsonArray = new JSONArray(movie);
            try {
                // Using Picasso to set the poster image in the ImageView
                Picasso.with(context).load(jsonArray.getString(1)).resize(600, 900).into(movieAdapterViewHolder.mMoviePoster);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our movie
     *
     * This code was taken directly from the course material
     */
    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }
    /**
     * This method is used to set the movie data on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new weather data to be displayed.
     *
     * This code was taken directly from the course material
     */
    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
