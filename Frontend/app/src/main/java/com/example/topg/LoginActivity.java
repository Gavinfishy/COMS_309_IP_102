package com.example.topg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mrohrer
 * This class is for users logging into their account.
 * From here, a user can type in their username and password to their account.
 * If they do not have an account yet, they can click the "Create an account" button and make one.
 * Once they have made one, they are able to log in immediately with it.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is required to fill out
     * in order to log in. Once they hit login, an api call is made to the backend to verify
     * their credentials, and take them to the right home page.
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.LoginPageLoginButton);
        Button createAccountButton = findViewById(R.id.loginPageCreateAccountButton);
        TextView usernameText = (TextView) findViewById(R.id.loginPageEnterUsername);
        TextView passwordText = (TextView) findViewById(R.id.loginPageEnterPass);
        TextView errorMessage = (TextView) findViewById(R.id.loginPageErrorMessage);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                JSONObject payload = new JSONObject();
                try {
                    payload.put("userName", username);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                try {
                    payload.put("password", password);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url = "http://coms-309-008.class.las.iastate.edu:8080/users/login";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, payload,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String isValid = response.getString("isValid");
                                    if (isValid.equals("True")) {
                                        String accountType = response.getString("accountType");
                                        if (accountType.equals("Gym Rat")){
                                            Intent intent = new Intent(LoginActivity.this, GymRatHomeActivity.class);
                                            intent.putExtra("username", response.getString("userName"));
                                            intent.putExtra("accountType", accountType);
                                            intent.putExtra("firstName", response.getString("firstName"));
                                            intent.putExtra("lastName", response.getString("lastName"));
                                            intent.putExtra("weight", response.getString("weight"));
                                            intent.putExtra("height", response.getString("height"));
                                            startActivity(intent);
                                        } else if (accountType.equals("Athlete")){

                                            Intent intent = new Intent(LoginActivity.this, AthleteHomeActivity.class);
                                            intent.putExtra("username", response.getString("userName"));
                                            intent.putExtra("accountType", response.getString("accountType"));
                                            intent.putExtra("firstName", response.getString("firstName"));
                                            intent.putExtra("lastName", response.getString("lastName"));
                                            intent.putExtra("weight", response.getString("weight"));
                                            intent.putExtra("height", response.getString("height"));
                                            intent.putExtra("teamName", response.getString("teamName"));
                                            startActivity(intent);
                                        }
                                        if (accountType.equals("Coach")){
                                            Intent intent = new Intent(LoginActivity.this, CoachHomeActivity.class);
                                            intent.putExtra("username", response.getString("userName"));
                                            intent.putExtra("accountType", accountType);
                                            intent.putExtra("firstName", response.getString("firstName"));
                                            intent.putExtra("lastName", response.getString("lastName"));
                                            intent.putExtra("teamName", response.getString("teamName"));
                                            startActivity(intent);
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
                        errorMessage.setText("There was some error");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            }
        });
    }
}