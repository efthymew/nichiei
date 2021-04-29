package com.efthymew.nichiei.ui.slideshow;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efthymew.nichiei.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SlideshowFragment extends Fragment {

    private ListView mangaList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        // Instantiate the RequestQueue.
        mangaList = new ListView(getContext());
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://kitsu.io/api/edge/manga?page[limit]=10&page[offset]=0&sort=-favoritesCount,-favoritesCount";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response: ", response);
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject respObj = new JSONObject(response);
                            JSONArray mangas = respObj.getJSONArray("data");
                            JSONObject links = respObj.getJSONObject("links");
                            Log.i("manga list length: ", String.valueOf(mangas.length()));
                            for (int i = 0; i < mangas.length(); i++) {
                                JSONObject manga = (JSONObject) mangas.get(i);
                                Log.i("manga found: ", manga.toString());
                            }
                            Log.i("links: ", links.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return root;
    }
}