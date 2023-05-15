package com.example.movieapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WatchList extends Service {

    private static WatchList watchListInstance; // create the watchlist
    private ArrayList<String> WatchList = new ArrayList<>(); // array for the watchlist movies

    private WatchList(){}; // used for constructing


    // create an instance if one does not exist
    public static WatchList getInstance(){
        if (watchListInstance == null){
            watchListInstance = new WatchList();
        }
        return watchListInstance;
    }

    // show the current watchlist, this may be updated after each reload or new json info
    public List<String> showWatchList(){
        return WatchList;
    }

    // add the movie's title to the watchlist array
    public void addMovieToWatchListArray(String title){
        WatchList.add(title);
    }

    public ArrayList<String> getWatchList(){
        return WatchList;
    }

    @Nullable
    @Override
    // binder should return null as no data will be bound
    public IBinder onBind(Intent intent){
        return null;
    }
}
