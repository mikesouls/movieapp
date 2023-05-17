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

        searchEditText = findViewById(R.id.eSearchText);
        searchButton = findViewById(R.id.bSearch);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);
        fetchMovies();

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
                        Gson gson = new Gson();
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject movieObject = results.getJSONObject(i);
                            Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                            movieList.add(movie);
                            movieAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON error: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e(TAG, "Volley error: " + error.getMessage());
                }
        );
        Volley.getInstance(this).addToRequestQueue(request);

    }

    private void handleSearchButtonClick() {
        String query = searchEditText.getText().toString();
        if (query.isEmpty()) {
            Toast.makeText(this, "Nothing Queried, Please Try Again.", Toast.LENGTH_SHORT).show();
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
                                Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                                movieList.add(movie);
                                newMovies.add(movie);
                                movieAdapter.setMovies(newMovies);
                                movieAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error fetching movies", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.getInstance(this).addToRequestQueue(request);
    }
}

