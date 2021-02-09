package com.example.newsx;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyRequest {

    private static MyRequest INSTANCE;
    private static RequestQueue requestQueue;
    private static Context CONTEXT;

    private MyRequest(Context context) {
        CONTEXT = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized MyRequest getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyRequest(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(CONTEXT.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
