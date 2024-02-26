package com.example.topg;

import static com.example.topg.CreateAccountActivity.isTextValid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
/**
 * @author franck
 * this class provides the interface and functionalities
 * for the athlete's home screen in the application. This includes viewing progress, logging workouts,
 * saving athlete stats, participating in group chats, and viewing announcements.
 *
 */

public class AthleteHomeActivity extends AppCompatActivity{
    /**
     * Initializes the activity. Sets up the user interface by inflating the layout, initializing
     * buttons and input fields, and handling button click events.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently
     *                           supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletehome);

        Intent intent = getIntent();

        //Buttons


        Button viewProgress = findViewById(R.id.viewProgress);
        Button logWorkouts = findViewById(R.id.logWorkouts);
        Button saveButton = findViewById(R.id.saveAthleteStatsButton);
        Button groupChatButton = findViewById(R.id.groupChat);
        Button viewAnnouncements = findViewById(R.id.viewAnnouncements);



        //Input from user
        EditText heightText = findViewById(R.id.enterHeight);
        EditText weightText = findViewById(R.id.enterWeight);
        TextView userWelcome = (TextView) findViewById(R.id.helloUser);

        String height = "";
        String weight = "";


        if (intent.hasExtra("height")) {
            height = intent.getStringExtra("height");
            heightText.setText(height);
        }
        if (intent.hasExtra("weight")) {
            weight = intent.getStringExtra("weight");
            weightText.setText(weight);
        }
        if (intent.hasExtra("firstName")) {
            userWelcome.setText("Welcome " + intent.getStringExtra("firstName"));
        }
        String teamName = intent.getStringExtra("teamName");
        String userName = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");

        //LogWorkouts
        logWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();
                Intent newIntent = new Intent(AthleteHomeActivity.this, LogWorkoutActivity.class);
                newIntent.putExtra("username", userName);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                newIntent.putExtra("height", finalHeight);
                newIntent.putExtra("weight", finalWeight);

                startActivity(newIntent);
            }
        });




        //logWorkouts

        viewAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();
                Intent newIntent = new Intent(AthleteHomeActivity.this, TeamAnnouncementsActivity.class);
                newIntent.putExtra("username", userName);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                newIntent.putExtra("height", finalHeight);
                newIntent.putExtra("weight", finalWeight);


                startActivity(newIntent);
            }
        });

        viewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();
                Intent newIntent = new Intent(AthleteHomeActivity.this, ViewUserProgressActivity.class);
                newIntent.putExtra("username", userName);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                newIntent.putExtra("height", finalHeight);
                newIntent.putExtra("weight", finalWeight);


                startActivity(newIntent);
            }
        });

        groupChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AthleteHomeActivity.this,GroupChatActivity.class);
                intent.putExtra("username", userName);
                intent.putExtra("teamName", teamName);
                startActivity(intent);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String height = heightText.getText().toString();
                String weight = weightText.getText().toString();
                if (isTextValid(height) && isTextValid(weight)) {
                    sendHeightAndWeight(height, weight);
                }

            }
        });
    }
    /**
     * Sends the height and weight data of the athlete to the server.
     * Constructs a JSON object with the provided height and weight, along with the athlete's username,
     * and sends it to the server using a PUT request.
     *
     * @param h Height of the athlete.
     * @param w Weight of the athlete.
     */

    public void sendHeightAndWeight(String h, String w) {
        JSONObject payload = new JSONObject();
        try {
            int height = Integer.valueOf(h);
            int weight = Integer.valueOf(w);
            payload.put("height", height);
            payload.put("weight", weight);
            payload.put("userName", getIntent().getStringExtra("username"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestQueue queue = Volley.newRequestQueue(AthleteHomeActivity.this);
        String url = "http://coms-309-008.class.las.iastate.edu:8080/athletes/setHeightWeight";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("There was some error");
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

}



