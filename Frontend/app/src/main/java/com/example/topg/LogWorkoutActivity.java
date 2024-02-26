package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LogWorkoutActivity extends AppCompatActivity {

    private EditText logWeight, logReps;
    private Spinner workoutType;

    private Button submitButton,backButton;
    private TextView heyUser;
    private String URL = "http://coms-309-008.class.las.iastate.edu:8080/athletes/logWorkout)";


    private void fetchWorkoutHistory(String username,String accountType) {
        String historyUrl = "http://coms-309-008.class.las.iastate.edu:8080/" + accountType+  "/" +username +"/workoutHistory"; // Replace USERNAME
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, historyUrl, null,
                response -> {
                    try {
                        ArrayList<String> workoutNames = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject workout = response.getJSONObject(i);
                            String workoutName = workout.getString("workoutName"); // Adjust field names as per your JSON structure
                            workoutNames.add(workoutName);
                        }

                        // Populate the spinner
                        Spinner workoutSpinner = findViewById(R.id.workoutSpinnerFromAth);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workoutNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        workoutSpinner.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LogWorkoutActivity.this, "Error parsing workout history", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(LogWorkoutActivity.this, "Error fetching workout history", Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logworkouts);
        Intent intent = getIntent();

        // Initialize
        workoutType= findViewById(R.id.workoutSpinnerFromAth);
        logWeight = findViewById(R.id.logWeight);
        logReps = findViewById(R.id.logReps);
        submitButton = findViewById(R.id.submitWorkoutLogButton);
        heyUser = findViewById(R.id.heyUser);
        backButton = findViewById(R.id.backButton);





        heyUser.setText("Hey, "+  intent.getStringExtra("firstName"));
        String nameUser = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String account = "";
        Log.d("AssignWorkoutsActivity", "Teamtype entered: " + accountType);

        if (accountType.equals("Gym Rat")) {
            account = "gymRats";
            fetchWorkoutHistory(nameUser,account);
        }else {

            account = "athletes";
            fetchWorkoutHistory(nameUser,account);
        }


        // Set up the buttons click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitWorkoutLog();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void submitWorkoutLog() {
        // Retrieve user inputs
        String workoutTypes = workoutType.getSelectedItem().toString();
        String actualWeight = logWeight.getText().toString();
        String actualReps = logReps.getText().toString();

        // Simple validation
        if(workoutTypes.isEmpty() || actualWeight.isEmpty() || actualReps.isEmpty()) {
            Toast.makeText(this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
            return;
        }



        // Construct the JSON payload
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", getIntent().getStringExtra("username"));
            payload.put("workoutName", workoutType);
            payload.put("exerciseName",getIntent().getStringExtra("exerciseName"));
            payload.put("actualWeight", Integer.parseInt(actualWeight));
            payload.put("actualReps", Integer.parseInt(actualReps));
        } catch (JSONException e) {
            e.printStackTrace(); // Proper error handling
        }

        // Volley request for PUT method
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL, payload,
                response -> {
                    Toast.makeText(LogWorkoutActivity.this, "Workout logged successfully!", Toast.LENGTH_SHORT).show();
                    logWeight.setText("");
                    logReps.setText("");
                },
                error -> Toast.makeText(LogWorkoutActivity.this, "Good JOB!", Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonObjectRequest);
    }
}