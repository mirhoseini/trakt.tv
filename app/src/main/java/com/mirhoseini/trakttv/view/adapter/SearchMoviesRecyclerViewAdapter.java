package com.mirhoseini.trakttv.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirhoseini.trakttv.BR;
import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.view.fragment.SearchMoviesFragment;

import java.util.ArrayList;
import java.util.Arrays;

import tv.trakt.api.model.Movie;
import tv.trakt.api.model.SearchMovieResult;

/**
 * Created by Mohsen on 19/07/16.
 */

public class SearchMoviesRecyclerViewAdapter extends RecyclerView.Adapter<SearchMoviesRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SearchMovieResult> searchMovieResults;
    private final SearchMoviesFragment.OnListFragmentInteractionListener listener;

    public SearchMoviesRecyclerViewAdapter(SearchMovieResult[] searchMovieResults, SearchMoviesFragment.OnListFragmentInteractionListener listener) {
        this.searchMovieResults = new ArrayList<>(Arrays.asList(searchMovieResults));
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie movie = searchMovieResults.get(position).getMovie();

        holder.movie = movie;
        holder.getBinding().setVariable(BR.movie, movie);
        holder.getBinding().executePendingBindings();

        holder.view.setOnClickListener(v -> {
            if (null != listener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                listener.onListFragmentInteraction(holder.movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchMovieResults.size();
    }

    public void addMoreMovies(SearchMovieResult[] searchMovieResults) {
        this.searchMovieResults.addAll(new ArrayList<>(Arrays.asList(searchMovieResults)));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        Movie movie;
        private ViewDataBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            binding = DataBindingUtil.bind(view);

        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }
}
