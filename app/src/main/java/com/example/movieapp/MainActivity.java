package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    //important string variables
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "5134b3f56c2cae575bb0ad435f0be5ee";
    private static final String API_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;

    //creating variables
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private EditText searchEditText;
    private Button searchButton;


    //creating list of movie objects
    private List<Movie> movieList = new ArrayList<>();

    // still have to implement
    private List<Movie> watchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting show watch list button

//        showWatchListButton = findViewById(R.id.bShowWatchList);

        searchEditText = findViewById(R.id.eSearchText);
        searchButton = findViewById(R.id.bSearch);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the adapter
        movieAdapter = new MovieAdapter(this, movieList, this);
        recyclerView.setAdapter(movieAdapter);

        // Make API call to fetch movies
        fetchMovies();


        //listener for if search button is clicked and user wants to search movies
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchButtonClick();
            }
        });
    }

    private void fetchMovies() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> {
                    try {
                        // create the parser and start parsing the data got from the json
                        Gson gson = new Gson();
                        JSONArray results = response.getJSONArray("results");
                        // loop for the amount of movies received
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject movieObject = results.getJSONObject(i);
                            Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                            movieList.add(movie);

                            // Notify adapter of data change
                            movieAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON: " + e.getMessage());
                    }
                },error -> {
                    Log.e(TAG, "Volley: " + error.getMessage());
                }
        );

        // Add request to Volley request queue
        Volley.getInstance(this).addToRequestQueue(request);

    }

    private void handleSearchButtonClick() {
        String query = searchEditText.getText().toString();
        if (query.isEmpty()) { // if the use entered nothing, show this toast message
            Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show();
            return;
        }

        //URL of the api call using to pull results based on user search
        String url = "https://api.themoviedb.org/3/search/movie?api_key=5134b3f56c2cae575bb0ad435f0be5ee&query=" + query;

        //getting the JSON response using Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Define a new List to hold the new data
                        List<Movie> newMovies = new ArrayList<>();
                        try {
                            //Using GSON to parse json response
                            Gson gson = new Gson();
                            //for every object in the list parse the JSON array and adding them to movie list
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                                movieList.add(movie);
                                //add new movies to the new search list
                                newMovies.add(movie);
                                // Set the new data in the adapter
                                movieAdapter.setMovies(newMovies);
                                // Notify adapter of data change (found this out with help)
                                movieAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {@Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        // Add request to Volley request queue
        Volley.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onMovieClick(Movie movie) {

    }
}

