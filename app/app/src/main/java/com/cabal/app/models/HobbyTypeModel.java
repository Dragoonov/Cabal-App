package com.cabal.app.models;

import com.google.gson.annotations.SerializedName;

public class HobbyTypeModel {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("hobbies")
    private HobbyModel[] hobbies;

    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private boolean expanded = false;

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
