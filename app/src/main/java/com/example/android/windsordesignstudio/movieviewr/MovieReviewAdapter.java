package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

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

        public TextView mReviewAuthor;
        public TextView mReview;

        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            // Grabbing the ImageView and setting an onClick listener
            mReviewAuthor = (TextView) view.findViewById(R.id.review_author);
            mReview = (TextView) view.findViewById(R.id.review_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String selectedMovieReview = mMovieReviewData[adapterPosition];
            mClickHandler.onClick(selectedMovieReview);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapter.MovieReviewAdapterViewHolder holder, int position) {
        String movie = mMovieReviewData[position];

        Context authorContext = holder.mReviewAuthor.getContext();
        Context reviewContext = holder.mReview.getContext();

        try {
            JSONArray jsonArray = new JSONArray(movie);
            try {
                // Set the text for the view here
                holder.mReviewAuthor.setText(jsonArray.getString(0));
                holder.mReview.setText(jsonArray.getString(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviewData) return 0;
        return mMovieReviewData.length;
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
        mMovieReviewData = movieReviewData;
        notifyDataSetChanged();
    }
}
