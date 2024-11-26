package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FloatingActionButton addButton;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of 50 users
        userList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            userList.add(new User("Name" + i, "LastName" + i, "Hobby" + i, null));
        }

        // Set up the adapter
        userAdapter = new UserAdapter(this, userList, (position, user) -> {
            // Start MainActivity3 for editing
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            intent.putExtra("isEdit", true);
            intent.putExtra("position", position);
            intent.putExtra("firstName", user.getFirstName());
            intent.putExtra("lastName", user.getLastName());
            intent.putExtra("hobby", user.getHobby());
            intent.putExtra("imageUri", user.getImageUri() != null ? user.getImageUri().toString() : null);
            startActivityForResult(intent, 2);  // Use a different request code for edit
        });

        recyclerView.setAdapter(userAdapter);

        // Set up the adapter
//        userAdapter = new UserAdapter(this, userList);
//        recyclerView.setAdapter(userAdapter);
        // Add button setup
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            intent.putExtra("isEdit", false);  // Adding a new user
            startActivityForResult(intent, 1);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            // Get the new user data from MainActivity2
            String firstName = data.getStringExtra("firstName");
            String lastName = data.getStringExtra("lastName");
            String hobby = data.getStringExtra("hobby");
            String imageUriString = data.getStringExtra("imageUri");
            Uri imageUri = imageUriString != null ? Uri.parse(imageUriString) : null;

            if (requestCode == 1) {
                // Add new user
                userList.add(new User(firstName, lastName, hobby, imageUri));
                userAdapter.notifyItemInserted(userList.size() - 1);
            } else if (requestCode == 2) {
                // Edit existing user
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    User user = userList.get(position);
                    user.firstName = firstName;
                    user.lastName = lastName;
                    user.hobby = hobby;
                    user.setImageUri(imageUri);
                    userAdapter.notifyItemChanged(position);
                }
            }
        }
    }
}