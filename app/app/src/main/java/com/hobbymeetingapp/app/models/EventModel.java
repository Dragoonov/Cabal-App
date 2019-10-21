package com.hobbymeetingapp.app.models;

import com.google.gson.annotations.SerializedName;

public class EventModel {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("creator")
    private String creator;

    @SerializedName("date")
    private String date;

    @SerializedName("members")
    private String[] members;

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("finished")
    private boolean finished;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}