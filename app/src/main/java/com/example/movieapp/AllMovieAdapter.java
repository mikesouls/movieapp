package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AllMovieAdapter extends RecyclerView.Adapter<AllMovieAdapter.Holder> {

    private Context context;
    private List<MovieStruct> movies;
    private OnMovieClickListener onMovieClickListener;
    private OnWatchListClickListener onWatchListClickListener;

    public AllMovieAdapter(Context context, List<MovieStruct> movies, OnMovieClickListener onMovieClickListener) {
        this.context = context;
        this.movies = movies;
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_layout, parent, false);
        return new Holder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            MovieStruct movie = movies.get(position);
            holder.title.setText(movie.getTitle());
            holder.overview.setText(movie.getOverview());
            holder.release.setText(movie.getReleaseDate());

            // get the movie banner form the directory of movie banners
            String bannerURL = "https://image.tmdb.org/t/p/w500" + movie.getBanner();
            Picasso.get().load(bannerURL).into(holder.bannerImage);

            holder.itemView.setOnClickListener(v -> {
                if(onMovieClickListener != null){
                    onMovieClickListener.onMovieClick(movie);
                }
            });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView release;
        public TextView overview;
        public ImageView bannerImage;
        public Button watchlist;

        public Holder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.MovieTitle);
            release = itemView.findViewById(R.id.MovieRelease);
            overview = itemView.findViewById(R.id.MovieOverview);
            bannerImage = itemView.findViewById(R.id.MovieBanner);
        }
    }

    // interfaces
    public interface OnMovieClickListener {
        void onMovieClick(MovieStruct movie);
    }
    public interface WatchListClickListener{
        void onWatchListClicked(MovieStruct movieStruct);
    }
    public interface OnWatchListClickListener {
        void onWatchlistClicked(MovieStruct movie);
    }
}
