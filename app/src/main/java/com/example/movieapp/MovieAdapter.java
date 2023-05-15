package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    // set the current movielist array to the received movies from the api
    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    //creating adapter giving it the context ,list of movies,and the on click event for each movie in the recycler view
    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener onMovieClickListener) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the movie item layout
        View view = LayoutInflater.from(context).inflate(R.layout.movie_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Bind movie data to view
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.releaseDateTextView.setText(movie.getReleaseDate());
        holder.overviewTextView.setText(movie.getOverview());
        String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Picasso.get().load(posterUrl).into(holder.posterImageView);
    }

    // return the amount of movies received
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        // variables
        public ImageView posterImageView;
        public TextView titleTextView;
        public TextView releaseDateTextView;
        public TextView overviewTextView;

        //getting the image views from the layout file
        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.MovieBanner);
            titleTextView = itemView.findViewById(R.id.MovieTitle);
            releaseDateTextView = itemView.findViewById(R.id.MovieRelease);
            overviewTextView = itemView.findViewById(R.id.MovieOverview);
        }
    }

    // Interface for movie click listener
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}

