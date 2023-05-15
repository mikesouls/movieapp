package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AllMovieAdapter.OnMovieClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY = "a94768291556ab422a5f956c6b5c7d56";
    private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="+KEY;
    private RecyclerView recyclerView;
    private AllMovieAdapter allMovieAdapter;
    private EditText SearchQuery;
    private Button Search, ShowWatchList;

    private List<MovieStruct> ListMovies = new ArrayList<>();
    private List<MovieStruct> MovieWatchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the view elements
        ShowWatchList = findViewById(R.id.bShowWatchList);
        SearchQuery = findViewById(R.id.eSearchText);
        Search = findViewById(R.id.bSearch);
        recyclerView = findViewById(R.id.recycler_view);

        // setup view and the movies adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allMovieAdapter = new AllMovieAdapter(this, ListMovies, (AllMovieAdapter.OnMovieClickListener) this);
        recyclerView.setAdapter(allMovieAdapter);

        // request movies
        fetch();

        ShowWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WatchList watchList = WatchList.getInstance();
                List<String> watch = watchList.getWatchList();

                StringBuilder stringBuilder = new StringBuilder();
                for(String Movie : watch) {
                    stringBuilder.append(Movie + "\n");
                }
            }
        });
    }
    private void fetch(){
        // request api data from db
        JsonObjectRequest JOR = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                Gson gson = new Gson();
                JSONArray array = response.getJSONArray("results");
                // loop through all the parse code
                for(int i=0; i<array.length();++i){
                    JSONObject movieObject = array.getJSONObject(i);
                    MovieStruct movie = gson.fromJson(movieObject.toString(), MovieStruct.class);
                    ListMovies.add(movie);
                }
            }
            catch (Exception e){ // log error on JSON
                Log.e("Error in JSON: ", e.getMessage());
            }
        },
                error -> { // log error on volley
                    Log.e(TAG, "VOLLEY ERR: " + error.getMessage());
                });
    }

    @Override
    public void onMovieClick(MovieStruct movie) {

    }
}