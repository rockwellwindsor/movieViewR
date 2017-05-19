package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by rockwellrice on 5/18/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {
    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();

    private String[] mMovieTrailerData;
    private final MovieTrailerAdapterOnClickHandler mClickHandler;


    public interface MovieTrailerAdapterOnClickHandler {
        void onClick(String movieTrailer);
    }

    public MovieTrailerAdapter(MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public VideoView mVideoView;
        public TextView mTrailerName;

        public MovieTrailerAdapterViewHolder(View view) {
            super(view);
            // Grabbing the ImageView and setting an onClick listener
            mVideoView = (VideoView) view.findViewById(R.id.viewr_trailer_view);
            mTrailerName = (TextView) view.findViewById(R.id.viewr_trailer_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            int adapterPosition = getAdapterPosition();
            String selectedMovieTrailer = mMovieTrailerData[adapterPosition];
            try {
                JSONArray jsonArray = new JSONArray(selectedMovieTrailer);
                mClickHandler.onClick(selectedMovieTrailer);
                if (jsonArray.getString(1) == "YouTube") {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + jsonArray.getString(1)));
                    context.startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.MovieTrailerAdapterViewHolder holder, int position) {
        String movie = mMovieTrailerData[position];

        Context nameContext = holder.mTrailerName.getContext();
//        Context reviewContext = holder.mReview.getContext();

        try {
            JSONArray jsonArray = new JSONArray(movie);
            try {
                // Set the text for the view here
                String text = "http://www.youtube.com/v/" + jsonArray.getString(1);
                holder.mTrailerName.setText(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (null == mMovieTrailerData) return 0;
        return mMovieTrailerData.length;
    }

    /**
     * This method is used to set the movie review data on the MovieReviewAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieTrailerData The review sent from onPostExecute in ReviewActivity.
     *
     * This code was taken directly from the course material
     */
    public void setMovieTrailerData(String[] movieTrailerData) {
        mMovieTrailerData = movieTrailerData;
        notifyDataSetChanged();
    }
}
