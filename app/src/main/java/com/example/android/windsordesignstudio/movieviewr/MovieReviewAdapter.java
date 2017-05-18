package com.example.android.windsordesignstudio.movieviewr;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rockwellrice on 5/17/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private static final String TAG = MovieReviewAdapter.class.getSimpleName();

    private String[] mMovieReviewData;
    private final MovieReviewAdapterOnClickHandler mClickHandler;

    public interface MovieReviewAdapterOnClickHandler {
        void onClick(String movieReview);
    }

    public MovieReviewAdapter(MovieReviewAdapter.MovieReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieReviews;

        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            // Grabbing the ImageView and setting an onClick listener
            mMovieReviews = (TextView) view.findViewById(R.id.viewr_movie_data_review);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String selectedMovie = mMovieReviewData[adapterPosition];
            mClickHandler.onClick(selectedMovie);
        }
    }

    @Override
    public MovieReviewAdapter.MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapter.MovieReviewAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * This method is used to set the movie review data on the MovieReviewAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieReviewData The review sent from onPostExecute in ReviewActivity.
     *
     * This code was taken directly from the course material
     */
    public void setMovieReviewData(String[] movieReviewData) {
        Log.d(TAG, "HERE : " + movieReviewData.length);
        mMovieReviewData = movieReviewData;
        notifyDataSetChanged();
    }
}
