package com.example.choijinjoo.wingdroid.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class AuthResponse {
    @SerializedName("access_token")
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
