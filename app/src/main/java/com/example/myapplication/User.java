package com.example.myapplication;

import android.net.Uri;

public class User {
    String firstName;
    String lastName;
    String hobby;
    private Uri imageUri;

    public User(String firstName, String lastName, String hobby, Uri imageUri) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobby = hobby;
        this.imageUri = imageUri;
    }
    public User(String firstName, String lastName, String hobby) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobby = hobby;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHobby() {
        return hobby;
    }
    public Uri getImageUri() {
        return imageUri;  // Getter for imageUri
    }
    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

