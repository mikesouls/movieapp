package com.example.movieapp;

import com.google.gson.annotations.SerializedName;

public class Movie {

    // Constructor For Structuring the movies

    @SerializedName("id")
    private int id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBanner() {
        return posterPath;
    }

    public void setBanner(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //constructor to add the title
    public Movie(String title) {
        this.title = title;
    }



}
