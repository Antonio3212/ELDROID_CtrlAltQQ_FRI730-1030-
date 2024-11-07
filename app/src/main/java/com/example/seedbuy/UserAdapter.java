package com.example.seedbuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout if needed
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recent_chat, parent, false);
        }

        // Get the user data item for this position
        User user = getItem(position);

        // Lookup views in the layout
        ImageView userImage = convertView.findViewById(R.id.userImage);
        TextView userName = convertView.findViewById(R.id.userName);

        // Set data in views
        if (user != null) {
            userName.setText(user.getName());
            // Assuming `user.getProfileImageResId()` returns a drawable resource ID
            userImage.setImageResource(user.getProfileImageResId()); // You might use a placeholder drawable if needed
        }

        return convertView;
    }
}
