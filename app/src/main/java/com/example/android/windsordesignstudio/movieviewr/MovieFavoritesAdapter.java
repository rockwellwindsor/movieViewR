package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by rockwellrice on 5/19/17.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieFavoritesAdapterViewHolder> {

    private static final String TAG = MovieFavoritesAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;
    private final MovieFavoritesAdapterOnClickHandler mClickHandler;

    public MovieFavoritesAdapter(MovieFavoritesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public interface MovieFavoritesAdapterOnClickHandler {
        void onClick(String movie);
    }

    public class MovieFavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePoster;

        public MovieFavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.viewr_movie_data_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get movie Title
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);

            int movieI = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_ID);
            int movieT = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE);
            int moviePO = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_POSTER_FULL_PATH);
            int movieV = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_VOTE_AVERAGE);
            int moviePL = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_PLOT);
            int movieR = mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_RELEASE_DATE);

            String movieID = mCursor.getString(movieI);
            String movieTitle = mCursor.getString(movieT);
            String moviePoster = mCursor.getString(moviePO);
            String movieVoteAverage = mCursor.getString(movieV);
            String moviePlot = mCursor.getString(moviePL);
            String movieReleaseDate = mCursor.getString(movieR);

            String mMovieData = "[\"" + movieTitle + "\",\"" + moviePoster + "\",\"" + movieVoteAverage + "\",\"" + moviePlot + "\",\"" + movieReleaseDate + "\",\"" + movieID + "\",\" is_favorite\"]";

            mClickHandler.onClick(mMovieData);
        }
    }

    @Override
    public MovieFavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.movie_favorite_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieFavoritesAdapterViewHolder(view);
    }

    public void onBindViewHolder(MovieFavoritesAdapterViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        Context context = holder.mMoviePoster.getContext();
        String moviePoster = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_POSTER_FULL_PATH));
        ImageView poster = holder.mMoviePoster;
        Picasso.with(mContext).load(moviePoster).resize(600, 900).into(poster);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    // Inner class for creating ViewHolders
    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        ImageView favoriteMoviePoster;

        /**
         * Constructor for the FavoriteViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FavoriteViewHolder(View itemView) {
            super(itemView);
            favoriteMoviePoster = (ImageView) itemView.findViewById(R.id.viewr_movie_data_poster);
        }
    }

}
