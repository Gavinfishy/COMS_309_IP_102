package com.example.topg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author franck
 * GroupChatActivity provides a user interface for a group chat feature within a team.
 * It allows users to connect to a WebSocket server, send messages, and view the chat history.
 * This class implements WebSocketListener for handling WebSocket events.
 */


public class GroupChatActivity extends AppCompatActivity implements WebSocketListener {
    private static final String BASE_URL = "ws://coms-309-008.class.las.iastate.edu:8080/team/chat/";

    private Button connectBtn, sendBtn;
    private EditText usernameEtx, teamNameEtx, msgEtx;
    private ListView msgLv;
    private ArrayAdapter<String> adapter;
    private List<String> messageList;
    private String userName, teamName, accountType;
    /**
     * Initializes the activity. Sets up the user interface and handles intent data.
     * Establishes the WebSocket connection for the group chat.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently
     *                           supplied. Otherwise, it is null.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        teamName = intent.getStringExtra("teamName");
        Button backHome = findViewById(R.id.backHomeButton);
        Button leaveHome= findViewById(R.id.leaveTeam);




        //connectBtn = findViewById(R.id.connectUsername);
        sendBtn = findViewById(R.id.sendButton);
//        usernameEtx = findViewById(R.id.usernameInput);
//        teamNameEtx = findViewById(R.id.teamNameConnect);
        msgEtx = findViewById(R.id.messageInput);
        msgLv = findViewById(R.id.listViewMessages);

        TextView chatTitlee = findViewById(R.id.teamName);



        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);
        msgLv.setAdapter(adapter);
        chatTitlee.setText("Team: "+ teamName);

        connectToWebSocket();

        leaveHome.setOnClickListener(view -> {
            leaveChat();
        });

        backHome.setOnClickListener(view -> {

            finish();
        });


        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
                msgEtx.setText("");
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage());
            }
        });
    }
    /**
     * Handles leaving the chat. Closes the WebSocket connection and navigates back to the
     * AthleteHomeActivity.
     */

    private void leaveChat() {
        // Close the WebSocket connection
        WebSocketManager.getInstance().closeWebSocket();

        // Navigate back to a previous activity or home screen
        Intent intentBack = new Intent(GroupChatActivity.this, AthleteHomeActivity.class);
        startActivity(intentBack);

        // Optionally, if you want to pass some message or data back to the previous activity, you can use intent extras
    }
    /**
     * Establishes a connection to the WebSocket server using the team name and user name.
     */
    private void connectToWebSocket() {
        String serverUrl = BASE_URL + teamName + "/" + userName;

        // Establish WebSocket connection and set listener
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(GroupChatActivity.this);
    }

    /**
     * Handles incoming messages from the WebSocket. Adds messages to the chat history and updates the UI.
     *
     * @param message The received message from the WebSocket.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            messageList.add(message);
            adapter.notifyDataSetChanged();
        });
    }
    /**
     * Handles the event when the WebSocket connection is closed.
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason The reason for closure.
     * @param remote Indicates if the closure was initiated by the remote host.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            messageList.add("---\nconnection closed by " + closedBy + "\nreason: " + reason);
            adapter.notifyDataSetChanged();
        });
    }
    /**
     * Placeholder for handling WebSocket connection opening. Currently not implemented.
     *
     * @param handshakedata The handshake data from the server.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}
    /**
     * Placeholder for handling WebSocket errors. Currently not implemented.
     *
     * @param ex The exception related to the WebSocket error.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
}
