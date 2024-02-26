package com.example.topg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamAdapter extends RecyclerView.Adapter<UserItemViewHolder> {
    private final JSONArray userList;

    private final Context context;

    public TeamAdapter(JSONArray userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        try {
            JSONObject user = userList.getJSONObject(position);
            holder.bind(user);

            holder.kickButton.setOnClickListener(v -> {
                // Perform the action when the "Kick" button is clicked
                try {
                    kickUser(position);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void kickUser(int position) throws JSONException {
        JSONObject userInfo = userList.getJSONObject(position);
        userList.remove(position);

        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "";

        if (userInfo.getString("accountType").equals("Coach")){
            url = "http://coms-309-008.class.las.iastate.edu:8080/coach/leaveTeam/" + userInfo.getString("userName");
        }
        if (userInfo.getString("accountType").equals("Athlete")){
            url = "http://coms-309-008.class.las.iastate.edu:8080/athlete/leaveTeam/" + userInfo.getString("userName");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

        for (int i = position; i < userList.length(); i++) {
            try {
                JSONObject user = userList.getJSONObject(i);
                user.put("position", i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Notify the adapter of the changes
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.length();
    }
}
