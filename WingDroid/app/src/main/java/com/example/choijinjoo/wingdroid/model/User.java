package com.example.choijinjoo.wingdroid.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */
@DatabaseTable(tableName = "user")
@Parcel(value = Parcel.Serialization.BEAN)
public class User {
    @DatabaseField(id = true)
    String id;
    @DatabaseField
    String login;
    @DatabaseField
    String email;
    @DatabaseField
    String date;
    @SerializedName("avatar_url")
    @DatabaseField
    String photoUrl;

    String fcmToken;

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

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

    public String getFcmToken() { return fcmToken; }

    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    /*
     *  Copy Constructor
     */

    public User(FirebaseUser firebaseUser, String fcmToken) {
        this.id = firebaseUser.getUid();
        this.login = firebaseUser.getDisplayName();
        this.email = firebaseUser.getEmail();
        this.fcmToken = fcmToken;
        if (firebaseUser.getPhotoUrl() != null)
            this.photoUrl = firebaseUser.getPhotoUrl().toString();
    }

}
