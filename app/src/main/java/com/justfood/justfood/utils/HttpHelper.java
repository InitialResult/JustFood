package com.justfood.justfood.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpHelper {
    private String res;

    public String connectToHttp(Context context) {
        res = "";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://www.food2fork.com/api/search?key=60ff572d8972cfd0d4ce0cdc4fc52b38&q=shredded%20chicken";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        res = "Response is: "+ response.substring(0,500);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                res = "That didn't work!";
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return res;
    }


}
