package com.cabal.app.models;

import com.google.gson.annotations.SerializedName;

public class HobbyModel {

    @SerializedName("name")
    private String name;

    @SerializedName("photoUrl")
    private String photoUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}