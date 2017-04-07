package com.example.android.windsordesignstudio.movieviewr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rockwell Rice on 4/5/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private String[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;


    public interface MovieAdapterOnClickHandler {
        void onClick(String movie);
    }
    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }
    /**
     * Cache of the children views for a forecast list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final TextView mMovieTextView;
        public final ImageView mMoviePoster;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.viewr_movie_data);
            mMoviePoster = (ImageView) view.findViewById(R.id.viewr_movie_data_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mMovieData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }
    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }
    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movie = mMovieData[position];
//        Log.d(TAG, "HERE : " + movie);
//        Picasso.load(movie.get("profile_image")).into(R.id.viewr_movie_data_poster);
        movieAdapterViewHolder.mMovieTextView.setText(movie);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }
    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param movieData The new weather data to be displayed.
     */
    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
//        Log.d(TAG, "HERE : " + Arrays.toString(mMovieData));
        notifyDataSetChanged();
    }
}
