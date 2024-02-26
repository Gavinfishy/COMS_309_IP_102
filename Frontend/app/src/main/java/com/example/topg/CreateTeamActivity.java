package com.example.topg;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author franck
 *
 * This class is for the user to create a team.
 * Let's say the User is a Coach.
 * The user has the ability to create a team by entering the team name, the athelete invitation code and coach invitation code.
 * When creating a team, it the team name as to be unique.
 * The Athele invitation and coach invitation has no restriction, just as long as it's a number.
 * When the user is done, the user could click on Save or Cancel.
 * Save would save all the informations entered.
 * Cancel would cancel all the informations entered.
 */


/**
 * @author franck
 * This method is for the user to input their team name, invitation for the athetlte and the invitation for the
 * coach. Once done, the user can click save and it'll saved the information entered.
 */

public class CreateTeamActivity extends AppCompatActivity {
//    private TextInputEditText teamName;
//    private TextInputEditText coachInvite;
//    private TextInputEditText athleteInvite;
//    private RequestQueue mQueue;


    /**
     * This initializes the activity. This includes setting the content view and referencing
     * the various UI elements like buttons and text input fields. Listeners for actions
     * (like button presses) should also be initialized here.
     *
     * @param savedInstanceState A mapping from String keys to various Parcelable values.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createteam);

        //reference to the saving button in the layout
        Button saveButton = findViewById(R.id.savingButton);

        //reference to the exist button in the layout
        Button existButton = findViewById(R.id.exitButton);

        //TextView to display error messages to the user
        final TextView errorMessage = (TextView) findViewById(R.id.error);

        //Input field for the team name
        TextInputEditText teamNaming = findViewById(R.id.teamNameUnique);

        //Input field for the coachID
       TextInputEditText coachId = findViewById(R.id.coachCode);

       //Input field for the atheletID
        TextInputEditText athleteId = findViewById(R.id.athCode);

        saveButton.setOnClickListener(new View.OnClickListener() {


            /**
             * Handles the button click event. This method collects the team name, coach ID,
             * and athlete ID from the input fields, creates a JSON object with these details,
             * and initializes a request queue for further processing.
             *
             * @param view The view that triggered this onClick event.
             */
            @Override
            public void onClick(View view) {
                String teamNameStr = teamNaming.getText().toString();
                String codeCoach = coachId.getText().toString();
                String atlID = athleteId.getText().toString();
                // 1. Create the JSON object
                JSONObject postParams = new JSONObject();
                try {
                    postParams.put("teamName", teamNameStr);
                    postParams.put("coachId",codeCoach);
                    postParams.put("athleteId", atlID);
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMessage.setText(R.string.error_creating_json);

                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(CreateTeamActivity.this);
                String url = "http://coms-309-008.class.las.iastate.edu:8080/teams";

                // 2. Make the POST request using Volley
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        postParams,
                        new Response.Listener<JSONObject>() {

                            /**
                             * Handles the successful response from the server. This method parses the
                             * received JSON, checks for a "success" message, and either navigates to
                             * the HomeActivity or displays an error based on the response.
                             *
                             * @param response The JSON object returned from the server.
                             */
                            @Override
                            public void onResponse(JSONObject response) {
                                // 3. Handle the response
                                try {
                                    String message = response.getString("message");
                                    if (message.equals("success")) {
                                        Intent intent = new Intent(CreateTeamActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        errorMessage.setText(R.string.error_invalid_login);

                                    }
                                }
                                catch(JSONException e){
                                    e.printStackTrace();
                                    errorMessage.setText(R.string.error_parsing_json);
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            /**
                             * Handles any errors that occur during the request.
                             *
                             * @param error The error details provided by Volley.
                             */
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle the error
                                errorMessage.setText(getString(R.string.error_message, error.getMessage()));
                            }
                        }
                );
                // Add the request to the Volley request queue
                queue.add(jsonObjReq);
            }
        });
        existButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for the "existButton". This method creates an intent
             * to navigate the user back to the LoginActivity. It ensures that the LoginActivity
             * is brought to the front, and any activities on top of it in the stack are cleared.
             *
             * @param view The view that triggered this onClick event.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // This clears all activities on top of the target activity
                startActivity(intent);
            }
        });
    }
}