package com.example.topg;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author mrohrer
 * This class in the join team activity on the top g application.
 * From here, a coach or athlete account type user can join a team of their preference by entering
 * in the team name, and the code for their respective account type. Coaches can also create a team
 * from here instead if they wish.
 */
public class JoinTeamActivity extends AppCompatActivity {

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is may fill out,
     * and has listeners and other methods for certain functionalities such as joining a team.
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String accountType = intent.getStringExtra("accountType");

        Button joinTeam = findViewById(R.id.joinTeamButton);
        Button createTeam = findViewById(R.id.createTeamButton);
        EditText teamName = findViewById(R.id.EnterTeamName);
        EditText teamInviteCode = findViewById(R.id.EnterTeamInviteCode);
        TextView errorMessage = findViewById(R.id.errorMessage);

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(accountType.equals("Coach")) {
                    Intent newIntent = new Intent(JoinTeamActivity.this, CreateTeamActivity.class);
                    newIntent.putExtra("username", username);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("accountType", accountType);

                    startActivity(newIntent);
                }
                else {
                    errorMessage.setText("You are not a Coach, join a team!");
                }
            }
        });

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = teamName.getText().toString();
                String code = teamInviteCode.getText().toString();

                String url = "";

                JSONObject payload = new JSONObject();
                try {
                    payload.put("teamName", name);
                    payload.put("inviteId", code);
                    payload.put("accountType", accountType);
                    if (accountType.equals("Coach")){
                        url = "http://coms-309-008.class.las.iastate.edu:8080/coach/joinTeam/" + username + "/" + name + "/" + code;
                    }
                    if (accountType.equals("Athlete")){
                        url = "http://coms-309-008.class.las.iastate.edu:8080/athlete/joinTeam/" + username + "/" + name + "/" + code;
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestQueue queue = Volley.newRequestQueue(JoinTeamActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, payload,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String isValid = response.getString("isValid");
                                    if (isValid.equals("True")) {
                                        if (accountType.equals("Athlete")){
                                            String height = intent.getStringExtra("height");
                                            String weight = intent.getStringExtra("weight");

                                            Intent newIntent = new Intent(JoinTeamActivity.this, AthleteHomeActivity.class);
                                            newIntent.putExtra("username", username);
                                            newIntent.putExtra("accountType", accountType);
                                            newIntent.putExtra("firstName", firstName);
                                            newIntent.putExtra("lastName", lastName);
                                            newIntent.putExtra("teamName", response.getString("teamName"));
                                            newIntent.putExtra("weight", weight);
                                            newIntent.putExtra("height", height);
                                            startActivity(newIntent);
                                        }
                                        if (accountType.equals("Coach")){
                                            Intent newIntent = new Intent(JoinTeamActivity.this, CoachHomeActivity.class);
                                            newIntent.putExtra("username", username);
                                            newIntent.putExtra("accountType", accountType);
                                            newIntent.putExtra("firstName", firstName);
                                            newIntent.putExtra("lastName", lastName);
                                            newIntent.putExtra("teamName", response.getString("teamName"));
                                            startActivity(newIntent);
                                        }
                                    } else {
                                        errorMessage.setText("Invalid Login. Retype Credentials or make an account");
                                    }
                                }
                                catch(JSONException e){
                                    e.printStackTrace();
                                    errorMessage.setText("Error parsing JSON response");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorMessage.setText("There was some error" + error);
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            }
        });


    }
}
