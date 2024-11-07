package com.example.seedbuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecentChatsActivity extends AppCompatActivity {

    private ListView recentChatsListView;
    private ArrayList<User> recentChats;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chats);

        recentChatsListView = findViewById(R.id.recentChatsListView);

        // Load recent chats with sample data (replace this with database logic later)
        recentChats = loadRecentChats();

        // Set up custom adapter with profile images and names
        adapter = new UserAdapter(this, recentChats);
        recentChatsListView.setAdapter(adapter);

        // Handle click event to open chat with the selected user
        recentChatsListView.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = recentChats.get(position);
            openChatWithUser(selectedUser);
        });
    }

    private ArrayList<User> loadRecentChats() {
        // Sample data (replace with actual data fetching logic)
        ArrayList<User> sampleUsers = new ArrayList<>();
        sampleUsers.add(new User(1, "Alice", R.drawable.ic_user_profile));
        sampleUsers.add(new User(2, "Bob", R.drawable.ic_user_profile));
        sampleUsers.add(new User(3, "Charlie", R.drawable.ic_user_profile));
        return sampleUsers;
    }

    private void openChatWithUser(User user) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }
}
