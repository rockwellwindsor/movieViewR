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

/**
 * Created by rockwellrice on 5/19/17.
 */

public class MovieFavoritesAdapter extends RecyclerView.Adapter<MovieFavoritesAdapter.MovieFavoritesAdapterViewHolder> {

    private static final String TAG = MovieFavoritesAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public class MovieFavoritesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mMovieTitle;
        ImageView mMoviePoster;

        public MovieFavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.favorite_movie_title);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.viewr_movie_data_poster);
        }
    }

    public MovieFavoritesAdapter(FavoritesActivity favoritesActivity, Cursor cursor) {
        this.mContext = favoritesActivity;
        this.mCursor = cursor;

    }

    @Override
    public MovieFavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_favorite_item, parent, false);
        return new MovieFavoritesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieFavoritesAdapterViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String movieTitle = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_MOVIE_TITLE));
        String moviePoster = mCursor.getString(mCursor.getColumnIndex(FavoritesContract.FavoriteEntry.COLUMN_POSTER_FULL_PATH));
        // Display the guest name
        holder.mMovieTitle.setText(movieTitle);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
