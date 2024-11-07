package com.example.seedbuy;

// ChatActivity.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String userId = getIntent().getStringExtra("userId");

        TextView messageEditText = findViewById(R.id.messageEditText);
        messageEditText.setText("Chat with User ID: " + userId); // Displaying userId for now
    }
}

