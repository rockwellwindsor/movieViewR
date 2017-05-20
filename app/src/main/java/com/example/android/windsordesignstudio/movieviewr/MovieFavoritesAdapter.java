package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.windsordesignstudio.movieviewr.data.FavoritesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by rockwellrice on 5/19/17.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.FavoriteViewHolder> {

    private static final String TAG = MovieFavoritesAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public MovieFavoritesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class MovieFavoritesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mMovieTitle;
        ImageView mMoviePoster;

        public MovieFavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.viewr_movie_data_poster);
        }
    }

    public MovieFavoritesAdapter(FavoritesActivity favoritesActivity, Cursor cursor) {
        this.mContext = favoritesActivity;
        this.mCursor = cursor;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_favorite_item, parent, false);

        return new FavoriteViewHolder(view);
    }

    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String moviePoster = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_POSTER_FULL_PATH));
        ImageView poster = holder.favoriteMoviePoster;
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
