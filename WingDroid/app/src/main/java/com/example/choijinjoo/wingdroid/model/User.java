package com.example.choijinjoo.wingdroid.model;

import com.google.firebase.auth.FirebaseUser;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class User {
    String id;
    String name;
    String email;
    String photoUrl;
    String fcmToken;
    List<String> bookmarks;

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<String> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public String getFcmToken() { return fcmToken; }

    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public User(String name) {
        this.name = name;
    }



    /*
     *  Copy Constructor
     */

    public User(FirebaseUser firebaseUser, String fcmToken) {
        this.id = firebaseUser.getUid();
        this.name = firebaseUser.getDisplayName();
        this.email = firebaseUser.getEmail();
        this.fcmToken = fcmToken;
        if (firebaseUser.getPhotoUrl() != null)
            this.photoUrl = firebaseUser.getPhotoUrl().toString();
    }

}
