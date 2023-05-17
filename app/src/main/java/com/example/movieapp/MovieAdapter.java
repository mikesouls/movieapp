package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

        // set the movies in the List<> to the movies currently received
    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    // set what context to use this data in, and set the current movies list variable to the one
    // passed in the params.
    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    // during creation inflate the movie_layout.xml to the activity_main.xml
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_layout, parent, false);
        return new MovieViewHolder(view);
    }

    // set all the viewholders to the data in the MovieViewHolder
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.Title.setText(movie.getTitle());
        holder.ReleaseDate.setText("Released: "+movie.getReleaseDate());
        holder.Overview.setText(movie.getOverview());
        holder.Rating.setText("Rating: "+String.format("%.1f", movie.getRating())+"/10");

        String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getBanner();
        Picasso.get().load(posterUrl).into(holder.Banner);
    }

    // get the current amount of movies in the list
     @Override
    public int getItemCount() {
        return movieList.size();
    }


    // this whole class is used for setting movie data per viewholder
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        //creating variables
        public ImageView Banner;
        public TextView Title;
        public TextView ReleaseDate;
        public TextView Overview;

        public TextView Rating;

        // useful constructor :)
        public MovieViewHolder(View itemView) {
            super(itemView);
            Banner = itemView.findViewById(R.id.MovieBanner);
            Title = itemView.findViewById(R.id.MovieTitle);
            ReleaseDate = itemView.findViewById(R.id.MovieRelease);
            Overview = itemView.findViewById(R.id.MovieOverview);
            Rating = itemView.findViewById(R.id.MovieRating);
        }
    }
}


