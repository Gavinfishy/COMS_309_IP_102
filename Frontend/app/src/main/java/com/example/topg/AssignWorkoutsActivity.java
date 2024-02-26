package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class AssignWorkoutsActivity extends AppCompatActivity {
    private EditText athleteUsernameEditText, workoutNameEditText, repsEditText,weightEditText;
    private Spinner exerciseSpinner;
    private ArrayList<String> exerciseNames;
    private Button assignWorkoutButton,backButton;
    private String accountType, username;
    private String teamName;
    private String URL = "http://coms-309-008.class.las.iastate.edu:8080/coaches/assignWorkout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignworkouts);
        Intent intent = getIntent();
        // Initialize
        athleteUsernameEditText = findViewById(R.id.athleteUsernameEditText);
        workoutNameEditText = findViewById(R.id.workoutNameEditText);
        assignWorkoutButton = findViewById(R.id.assignWorkoutButton);
        backButton = findViewById(R.id.backCoachHomeButton);
        weightEditText = findViewById(R.id.weightEditText);
        exerciseSpinner = findViewById(R.id.exerciseSpinner);
        assignWorkoutButton = findViewById(R.id.assignWorkoutButton);
        repsEditText = findViewById(R.id.repsEditText);
        accountType = intent.getStringExtra("accountType");
        username = intent.getStringExtra("username");
        teamName = intent.getStringExtra("teamName");
        fetchExercises();
        assignWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignWorkout();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void fetchExercises() {
        String url = "http://coms-309-008.class.las.iastate.edu:8080/exercises";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        exerciseNames = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            String exerciseName = response.getJSONObject(i).getString("exerciseName");
                            exerciseNames.add(exerciseName);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        exerciseSpinner.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error fetching exercises", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }


    private void assignWorkout() {
        String athleteUsername = athleteUsernameEditText.getText().toString();
        String workoutName = workoutNameEditText.getText().toString();
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        int reps = Integer.parseInt(repsEditText.getText().toString());
        int weight = Integer.parseInt(weightEditText.getText().toString());

        //debug
        Log.d("AssignWorkoutsActivity", "Username entered: " + athleteUsername);

        if(accountType.equals("gymRats")) {
            if(!athleteUsername.equals(username)) {
                Toast.makeText(this, "You can only assign workouts to yourself", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(athleteUsername.isEmpty() || workoutName.isEmpty()) {
            Toast.makeText(this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct the JSON payload
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", athleteUsername);
            payload.put("workoutName", workoutName);
            payload.put("exerciseName", exerciseName);
            payload.put("reps", reps);
            payload.put("weight", weight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Volley request for POST method
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, payload,
                response -> {
                    Toast.makeText(AssignWorkoutsActivity.this, "Workout assigned successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {if(accountType.equals("gymRat")) {
                    if(!athleteUsername.equals(username)) {
                        Toast.makeText(this, "You can only assign workouts to yourself", Toast.LENGTH_SHORT).show();
                    }
                }}
        );

        queue.add(jsonObjectRequest);
    }
}

