package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class ViewTeamActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewteam);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String teamName = intent.getStringExtra("teamName");

        TextView teamLabel = findViewById(R.id.teamNameViewTeam);
        Button backToHome = findViewById(R.id.backToHomeViewTeam);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_team);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teamLabel.setText(teamName);

        final JSONArray[] usersJSONArray = new JSONArray[1];

        RequestQueue queue = Volley.newRequestQueue(ViewTeamActivity.this);
        String url = "http://coms-309-008.class.las.iastate.edu:8080/team/getUsers/" + teamName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        usersJSONArray[0] = response;
                        TeamAdapter teamAdapter = new TeamAdapter(usersJSONArray[0], ViewTeamActivity.this);
                        recyclerView.setAdapter(teamAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent newIntent = new Intent(ViewTeamActivity.this, CoachHomeActivity.class);
                    newIntent.putExtra("username", username);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", teamName);
                    startActivity(newIntent);
            }
        });



    }

}
