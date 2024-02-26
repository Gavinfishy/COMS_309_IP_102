package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * @author mrohrer
 *
 * Class for creating an Account on the Top G application.
 * A user is able choose their first name, last name, username, password, and account type when
 * creating their account. When they create their account, they can use their newly made username and
 * password credentials to login to their account, and they will be redirected to their respected home page.
 *
 */
public class CreateAccountActivity extends AppCompatActivity {

    /**
     * Stores the value for the users account type when they select an answer.
     */
    String accountTypeAnswer;

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is required to fill out,
     * and has listeners and other methods for certain functionalities such as selecting a choice
     * and submitting your account creation.
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button createNewAccountButton = findViewById(R.id.createANewAccountButton);
        Button backToLogin = findViewById(R.id.backToLoginButton);
        TextView errorMessage = findViewById(R.id.createAccountPageErrorMessage);
        TextView enterUsername = findViewById(R.id.createAccountEnterUsername);
        TextView enterPassword = findViewById(R.id.createAccountEnterPass);
        TextView enterFirstName = findViewById(R.id.createAccountPageEnterFirstName);
        TextView enterLastName = findViewById(R.id.createAccountPageEnterLastName);
        RadioGroup userTypeChoice = findViewById(R.id.createAccountSelectUser);

        userTypeChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    String selectedAnswer = radioButton.getText().toString();
                    accountTypeAnswer = selectedAnswer;
                }
            }
        });

        /**
         * Sends user back to login upon clicking the button.
         */
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Creates a new account with the users information in the database if all the text is valid.
         * User cam now use those credentials to log in.
         */
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = enterUsername.getText().toString();
                String password = enterPassword.getText().toString();
                String firstName = enterFirstName.getText().toString();
                String lastName = enterLastName.getText().toString();
                String accountType = accountTypeAnswer;

                if (isTextValid(username) && isTextValid(password) && isTextValid(firstName) && isTextValid(lastName) && isTextValid(accountType)){
                    JSONObject payload = new JSONObject();

                    try {
                        payload.put("userName", username);
                        payload.put("password", password);
                        payload.put("firstName", firstName);
                        payload.put("lastName", lastName);
                        payload.put("accountType", accountType);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    RequestQueue queue = Volley.newRequestQueue(CreateAccountActivity.this);
                    String url = "http://coms-309-008.class.las.iastate.edu:8080/users";

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, payload,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String isValid = response.getString("isValid");
                                        if (isValid.equals("True")) {
                                            String accountType = response.getString("accountType");
                                            if (accountType.equals("Gym Rat")){
                                                Intent intent = new Intent(CreateAccountActivity.this, GymRatHomeActivity.class);
                                                intent.putExtra("username", response.getString("userName"));
                                                intent.putExtra("accountType", accountType);
                                                intent.putExtra("firstName", response.getString("firstName"));
                                                intent.putExtra("lastName", response.getString("lastName"));
                                                intent.putExtra("weight","Enter Height (in)");
                                                intent.putExtra("height", "Enter Weight (lb)");
                                                startActivity(intent);
                                            }
                                            else if(accountType.equals("Athlete")) {
                                                Intent intent = new Intent(CreateAccountActivity.this, JoinTeamActivity.class);
                                                intent.putExtra("username", response.getString("userName"));
                                                intent.putExtra("accountType", accountType);
                                                intent.putExtra("firstName", response.getString("firstName"));
                                                intent.putExtra("lastName", response.getString("lastName"));
                                                intent.putExtra("weight","Enter Height (in)");
                                                intent.putExtra("height", "Enter Weight (lb)");
                                                startActivity(intent);
                                            }
                                            else if(accountType.equals("Coach")) {
                                                Intent intent = new Intent(CreateAccountActivity.this, JoinTeamActivity.class);
                                                intent.putExtra("username", response.getString("userName"));
                                                intent.putExtra("accountType", accountType);
                                                intent.putExtra("firstName", response.getString("firstName"));
                                                intent.putExtra("lastName", response.getString("lastName"));
                                                startActivity(intent);
                                            }
                                        } else {
                                            errorMessage.setText("The username you chose already exists");
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
                else{
                    errorMessage.setText("Not all fields are filled out");
                }
            }
        });


    }

    /**
     * Method that determines if a string s is valid for submission or not.
     * @param s a string that is checked to see if it is valid and able to be sent.
     * @return true or false boolean that deteremines if s is a "valid" string or not.
     */
    public static boolean isTextValid(String s){
        if (!s.equals("") && s != null){
            return true;
        }
        return false;
    }
}
