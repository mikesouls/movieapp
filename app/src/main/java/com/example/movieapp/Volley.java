package com.example.movieapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class Volley {

    private RequestQueue requestQueue;
    private static Volley instance;

    private static Context context;

    // have volley queue the new request and get data back from it
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    // have the current instance set the data to the layouts
    public static synchronized Volley getInstance(Context context) {
        if (instance == null) {
            instance = new Volley(context);
        }
        return instance;
    }


    private Volley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
