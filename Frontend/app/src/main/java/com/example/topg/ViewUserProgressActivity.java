package com.example.topg;

import static com.example.topg.CreateAccountActivity.isTextValid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewUserProgressActivity extends AppCompatActivity {

    private ImageView imageView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuserprogress);
        Intent intent = getIntent();

        Log.d("ViewUserProgressActivity", "Intent extras: " + intent.getExtras()); // Log all extras
        Log.d("ViewUserProgressActivity", "Account Type: " + intent.getStringExtra("accountType"));
        String accountType = intent.getStringExtra("accountType");
        String teamName = "";
        if (accountType.equals("Athlete")){
            teamName = intent.getStringExtra("teamName");
        }
        String userName = intent.getStringExtra("username");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");

        String ifCoach = "";

        EditText enterWorkout = findViewById(R.id.enterWorkout);




        Button saveButton = findViewById(R.id.saveViewUserProgress);
        Button backToHome = findViewById(R.id.backToHomeViewUserProgress);

        String finalUserName = userName;
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String workout = enterWorkout.getText().toString();

                String IMAGE_URL = "http://coms-309-008.class.las.iastate.edu:8080/charts/" + finalUserName + "/" + workout;


                imageView = findViewById(R.id.imageView);
                requestQueue = Volley.newRequestQueue(ViewUserProgressActivity.this);

                // Fetching the image using Volley ImageRequest
                ImageRequest imageRequest = new ImageRequest(IMAGE_URL,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e("Volley Error", volleyError.toString());
                            }
                        });
                requestQueue.add(imageRequest);
            }
        });


        String finalIfCoach = ifCoach;
        String finalTeamName = teamName;
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals("Coach")) {
                    Intent newIntent = new Intent(ViewUserProgressActivity.this, CoachHomeActivity.class);
                    newIntent.putExtra("username", userName);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", finalTeamName);
                    startActivity(newIntent);
                }
                if (accountType.equals("Athlete")) {
                    Intent newIntent = new Intent(ViewUserProgressActivity.this, AthleteHomeActivity.class);
                    newIntent.putExtra("username", userName);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", finalTeamName);
                    if (intent.hasExtra("height")){
                        newIntent.putExtra("height", intent.getStringExtra("height"));
                    }
                    if (intent.hasExtra("weight")){
                        newIntent.putExtra("weight", intent.getStringExtra("weight"));
                    }
                    startActivity(newIntent);
                }
                if (accountType.equals("Gym Rat")) {
                    Intent newIntent = new Intent(ViewUserProgressActivity.this, GymRatHomeActivity.class);
                    newIntent.putExtra("username", userName);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", finalTeamName);
                    if (intent.hasExtra("height")){
                        newIntent.putExtra("height", intent.getStringExtra("height"));
                    }
                    if (intent.hasExtra("weight")){
                        newIntent.putExtra("weight", intent.getStringExtra("weight"));
                    }
                    startActivity(newIntent);
                }
            }
        });

    }

//    private void fillOutGraphSpinner(String username, String accountType) {
//        String historyUrl = ""; // Replace USERNAME
//        if (accountType.equals("Athlete")) {
//            historyUrl = "http://coms-309-008.class.las.iastate.edu:8080/athletes/"+ username +"/workoutHistory";
//        }
//        else if (accountType.equals("Gym Rat")){
//            historyUrl = "http://coms-309-008.class.las.iastate.edu:8080/gymRats/"+ username +"/workoutHistory";
//        }
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, historyUrl, null,
//                response -> {
//                    try {
//                        ArrayList<String> workoutNames = new ArrayList<>();
//                        for (int i = 0; i < response.length(); i++) {
//                            JSONObject workout = response.getJSONObject(i);
//                            String workoutName = workout.getString("exerciseName"); // Adjust field names as per your JSON structure
//                            workoutNames.add(workoutName);
//                        }
//
//                        // Populate the spinner
//                        Spinner workoutSpinner = findViewById(R.id.getGraphSpinnerForUser);
//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workoutNames);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        workoutSpinner.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(ViewUserProgressActivity.this, "Error parsing workout history", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> Toast.makeText(ViewUserProgressActivity.this, "Error fetching workout history", Toast.LENGTH_SHORT).show()
//        );
//
//        queue.add(jsonArrayRequest);
//    }

}
