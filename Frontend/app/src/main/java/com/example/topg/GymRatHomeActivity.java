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

/**
 * @author mrohrer and Franck
 *
 * This class in the home page for the user of type "gym rat" on the top g application.
 * From here, a gym rat can view their progress, log height/weight, track progress, assign workouts,
 * and join a team.
 */
public class GymRatHomeActivity extends AppCompatActivity {

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is may fill out,
     * and has listeners and other methods for certain functionalities such as saving their height
     * and weight.
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymrathome);
        Intent intent = getIntent();

        Button assignWorkoutsButton = findViewById(R.id.assignWorkoutsGymRatHome);
        Button viewProgressButton = findViewById(R.id.ViewProgressButtonGymRatHome);
        Button logWorkoutsButton = findViewById(R.id.LogWorkoutsGymRatHome);
        Button joinTeamButton = findViewById(R.id.JoinTeamButtonGymRatHome);
        Button saveHWButton = findViewById(R.id.saveGymRatStatsButton);
        EditText heightText = findViewById(R.id.gymratHomeEnterHeight);
        EditText weightText = findViewById(R.id.gymratHomeEnterWeight);
        TextView helloUser = (TextView) findViewById(R.id.HelloUserGymRatHomeText);

        String userName = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String teamName = intent.getStringExtra("teamName");




        if(intent.hasExtra("height")){
            heightText.setText(intent.getStringExtra("height"));
        }
        if(intent.hasExtra("weight")){
            weightText.setText(intent.getStringExtra("weight"));
        }
        if(intent.hasExtra("firstName")){
            helloUser.setText("Hello " + intent.getStringExtra("firstName"));
        }


        joinTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();

                Intent newIntent = new Intent(GymRatHomeActivity.this, JoinTeamActivity.class);
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
        assignWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();

                Intent newIntent = new Intent(GymRatHomeActivity.this, AssignWorkoutsActivity.class);
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
        logWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();

                Intent newIntent = new Intent(GymRatHomeActivity.this, LogWorkoutActivity.class);
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

        saveHWButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String height = heightText.getText().toString();
                String weight = weightText.getText().toString();
                if (isTextValid(height) && isTextValid(weight)) {
                    sendHeightAndWeight(height, weight);
                }
            }
        });

        viewProgressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String finalWeight = weightText.getText().toString();
                String finalHeight = heightText.getText().toString();
                Intent newIntent = new Intent(GymRatHomeActivity.this, ViewUserProgressActivity.class);
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

    }

    /**
     * This method will set the height and weight of a user to the database
     * @param h height of the user to get sent and saved to the database
     * @param w weight of the user to get sent and saved to the database
     */
    public void sendHeightAndWeight(String h, String w){
        JSONObject payload = new JSONObject();
        try {
            int height =  Integer.valueOf(h);
            int weight = Integer.valueOf(w);
            payload.put("height", height);
            payload.put("weight", weight);
            payload.put("userName", getIntent().getStringExtra("username"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestQueue queue = Volley.newRequestQueue(GymRatHomeActivity.this);
        String url = "http://coms-309-008.class.las.iastate.edu:8080/gymRats/setHeightWeight";

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
