package com.cabal.app.models;

import com.google.gson.annotations.SerializedName;

public class HobbyTypeModel {

    @SerializedName("type")
    private String type;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("hobbies")
    private HobbyModel[] hobbies;

    private boolean expanded = false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public HobbyModel[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbyModel[] hobbies) {
        this.hobbies = hobbies;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
