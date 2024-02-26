package com.example.topg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocketListener;
import okio.ByteString;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


/**
 * @author mrohrer
 * This class in the team announcements activity on the top g application.
 * From here, a coach can post and view announcements on their team. Athletes can view announcements
 * (the most recent 3) from their coach, but they cannot post announcements themselves. Announcements have
 * a 50 character limit.
 */
public class TeamAnnouncementsActivity extends AppCompatActivity {

    /**
     * This is the websocket that will be used to send announcements from the
     * client side application to the server. It also receives announcements from the server
     * posted by others so they can be displayed.
     */
    private WebSocket webSocket;

    /**
     * This method is called when another screen calls this one, and it initializes the new
     * content view. This method sets variables to the fields the user is may fill out,
     * and has listeners and other methods for certain functionalities such as posting
     * an announcement.
     *
     * @param savedInstanceState This parameter contains the previously saved state and can be used
     *                           to return values from the previous state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_announcements);
        Intent intent = getIntent();
        EditText announcementInput = findViewById(R.id.enterAnnouncement);
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String teamName = intent.getStringExtra("teamName");
        Button postAnnouncement = findViewById(R.id.postAnnouncementButton);
        Button backToHome = findViewById(R.id.backToHomeButtonAnnouncements);
        TextView announcement1 = findViewById(R.id.announcement1);
        TextView announcement2 = findViewById(R.id.announcement2);
        TextView announcement3 = findViewById(R.id.announcement3);
        TextView errorMessage = findViewById(R.id.errorMessageAnnouncements);


        postAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals("Coach")) {
                    String newAnnouncement = announcementInput.getText().toString();
                    if (newAnnouncement.length() <= 50){
                        webSocket.send(newAnnouncement);
                    }
                    else {
                        errorMessage.setText("Message must be <= 50 characters.");
                    }
                } else {
                    errorMessage.setText("You are not a Coach!");
                }
            }
        });

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType.equals("Coach")) {
                    Intent newIntent = new Intent(TeamAnnouncementsActivity.this, CoachHomeActivity.class);
                    newIntent.putExtra("username", username);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", teamName);

                    if (webSocket != null) {
                        webSocket.close(1000, "User left the page");
                        webSocket = null;
                    }
                    startActivity(newIntent);
                }
                if (accountType.equals("Athlete")) {
                    Intent newIntent = new Intent(TeamAnnouncementsActivity.this, AthleteHomeActivity.class);
                    newIntent.putExtra("username", username);
                    newIntent.putExtra("accountType", accountType);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("teamName", teamName);
                    if (intent.hasExtra("height")){
                        newIntent.putExtra("height", intent.getStringExtra("height"));
                    }
                    if (intent.hasExtra("weight")){
                        newIntent.putExtra("weight", intent.getStringExtra("weight"));
                    }

                    if (webSocket != null) {
                        webSocket.close(1000, "User left the page");
                        webSocket = null;
                    }
                    startActivity(newIntent);
                }
            }
        });

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://coms-309-008.class.las.iastate.edu:8080/announce/" + teamName + "/" + username).build();
        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                // Handle connection opened
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI or perform other actions upon connection open
                    }
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, String newAnnouncement) {
                // Handle incoming text message (announcement) from the server
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String ann1 = announcement1.getText().toString();
                        String ann2 = announcement2.getText().toString();
                        announcement3.setText(ann2);
                        announcement2.setText(ann1);
                        announcement1.setText(newAnnouncement);
                    }
                });
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                // Handle connection closed
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI or perform other actions upon connection open
                    }
                });
            }
        };

        webSocket = client.newWebSocket(request, listener);
    }
}
