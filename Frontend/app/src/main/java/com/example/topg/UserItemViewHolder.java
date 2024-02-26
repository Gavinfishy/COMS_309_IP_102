package com.example.topg;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView userName;
    private final TextView userEmail;
    final Button kickButton;


    public UserItemViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
        userEmail = itemView.findViewById(R.id.user_type);
        kickButton = itemView.findViewById(R.id.kick_button);
    }

    public void bind(JSONObject user) throws JSONException {
        userName.setText(user.getString("userName"));
        userEmail.setText(user.getString("accountType"));
        // Bind more user data if available
    }
}
