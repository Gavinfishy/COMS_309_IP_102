package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author mrohrer and Franck
 * This class in the home page for the user of type "coach" on the top g application.
 * From here, a coach can view their team's progress, post announcements, join team chats, assign workouts,
 * and join a team.
 */
public class CoachHomeActivity extends AppCompatActivity {

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is may fill out,
     * and has listeners and other methods for certain functionalities such as join the announcement posting activity
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachhome);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String teamName = intent.getStringExtra("teamName");



        Button viewTeamButton = findViewById(R.id.ViewTeamButtonCoachHome);

        Button announcementsButton = findViewById(R.id.postAnnouncementCoachHome);
        Button assignWorkout =  findViewById(R.id.assignWorkoutsCoachHome);
        TextView helloCoach = findViewById(R.id.HelloUserCoachText);
        Button progess = findViewById(R.id.ViewProgressButtonCoachHome);
        helloCoach.setText("Hello, " + firstName);

        progess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(CoachHomeActivity.this, ViewUserProgressActivity.class);
                newIntent.putExtra("username", username);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                startActivity(newIntent);
            }
        });
        viewTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(CoachHomeActivity.this, ViewTeamActivity.class);
                newIntent.putExtra("username", username);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                startActivity(newIntent);
            }
        });
        announcementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(CoachHomeActivity.this, TeamAnnouncementsActivity.class);
                newIntent.putExtra("username", username);
                newIntent.putExtra("accountType", accountType);
                newIntent.putExtra("firstName", firstName);
                newIntent.putExtra("lastName", lastName);
                newIntent.putExtra("teamName", teamName);
                startActivity(newIntent);
            }
        });
        assignWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(CoachHomeActivity.this, AssignWorkoutsActivity.class);
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
