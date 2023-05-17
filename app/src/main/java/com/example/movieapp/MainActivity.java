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

public class MainActivity extends AppCompatActivity {

    // setup all the use case variables and URL+API_KEY
    private static final String API_KEY = "a94768291556ab422a5f956c6b5c7d56";
    private static final String API_URL = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private EditText searchEditText;
    private Button searchButton;

    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initalize the resources used in the mainactivity
        searchEditText = findViewById(R.id.eSearchText);
        searchButton = findViewById(R.id.bSearch);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);

        //fetch movies on app start
        fetchMovies();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchButtonClick();
            }
        });
    }

    // fetch a total of 10 movies on load
    private void fetchMovies() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> {
                        // try formatting the requested json into a readable and parse-able format
                    try {
                        Gson gson = new Gson();
                        JSONArray results = response.getJSONArray("results");
                        // loop through the amount of movies received
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject movieObject = results.getJSONObject(i);
                            Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                            movieList.add(movie);
                            movieAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) { // JSON ERROR
                        Log.e(TAG, "JSON: " + e.getMessage());
                    }
                },
                error -> { // VOLLEY REQUEST ERROR
                    Log.e(TAG, "Volley: " + error.getMessage());
                }
        );
        // have volley add the request to the queue
        Volley.getInstance(this).addToRequestQueue(request);

    }

    // using relatively similar code, do the same thing but with
    private void handleSearchButtonClick() {
        String query = searchEditText.getText().toString();
        if (query.isEmpty()) {
            Toast.makeText(this, "No input detected, cancelling request...", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&query="+query;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Movie> newMovies = new ArrayList<>();
                        try {
                            Gson gson = new Gson();
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movieObject = results.getJSONObject(i);
                                // add all new queried movie data to all the fragments in the layout
                                Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                                movieList.add(movie);
                                newMovies.add(movie);
                                movieAdapter.setMovies(newMovies);
                                movieAdapter.notifyDataSetChanged();
                            }
                            // if any json error or exception happens, throw a toast error message
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "(J)ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                // If the new request fails, volley will throw an error.
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "(V) ERROR"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.getInstance(this).addToRequestQueue(request);
    }
}

