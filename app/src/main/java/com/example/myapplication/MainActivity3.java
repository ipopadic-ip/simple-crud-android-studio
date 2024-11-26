package com.example.myapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.Manifest;


import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, hobbyInput;
    private ImageView selectedImageView;
    private Button uploadButton, addButton;
    private Uri selectedImageUri;

    private static final int REQUEST_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }

        // Initialize input fields and buttons
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        hobbyInput = findViewById(R.id.hobbyInput);
        selectedImageView = findViewById(R.id.selectedImageView);
        uploadButton = findViewById(R.id.uploadButton);
        addButton = findViewById(R.id.addButton);

        // Set up image upload button
        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        // Check if this is an edit operation
        Intent intent = getIntent();
        boolean isEdit = intent.getBooleanExtra("isEdit", false);
        int position = intent.getIntExtra("position", -1);
        if (isEdit) {
            // Pre-fill the fields with existing user data
            firstNameInput.setText(intent.getStringExtra("firstName"));
            lastNameInput.setText(intent.getStringExtra("lastName"));
            hobbyInput.setText(intent.getStringExtra("hobby"));
            String imageUriString = intent.getStringExtra("imageUri");
            if (imageUriString != null) {
                selectedImageUri = Uri.parse(imageUriString);
                selectedImageView.setImageURI(selectedImageUri);
            }
            addButton.setText("Update User");
        }

        // Set up add button to return user input
        addButton.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String hobby = hobbyInput.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("firstName", firstName);
            resultIntent.putExtra("lastName", lastName);
            resultIntent.putExtra("hobby", hobby);
            if (selectedImageUri != null) {
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }
            if (isEdit) {
                resultIntent.putExtra("position", position);  // Pass the position back
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied to access external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Log.d("MainActivity3", "Selected Image URI: " + selectedImageUri);
            selectedImageView.setImageURI(selectedImageUri);
        } else {
            Log.d("MainActivity3", "Image selection failed or cancelled.");
        }
    }
}