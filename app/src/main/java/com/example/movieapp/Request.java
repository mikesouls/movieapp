package com.example.movieapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Request {
    private static Request instance;
    private RequestQueue requestQueue;
    private static Context context;

    // found this solution that gets the request queue, makes me confused as to how it works.
    public <T> void addToRequestQueue(com.android.volley.Request<T> request) {
        getRequestQueue().add(request);
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
