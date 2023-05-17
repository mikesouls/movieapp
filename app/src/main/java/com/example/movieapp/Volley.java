package com.example.movieapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class Volley {

    // this is how volley gets data from the api

    private static Volley instance;
    private RequestQueue requestQueue;
    private static Context context;

    // volley specifies the context, and initializes a request queue.
    private Volley(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    // volley will create a new instance if one does not already exist (if there is no request made yet)
    public static synchronized Volley getInstance(Context context) {
        if (instance == null) {
            instance = new Volley(context);
        }
        return instance;
    }

    // setup a new GET Request
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    // Add what request should be made to volley's queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
