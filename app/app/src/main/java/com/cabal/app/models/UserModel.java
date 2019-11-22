package com.cabal.app.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("nick")
    private String nick;

    @SerializedName("email")
    private String email;

    @SerializedName("tokenId")
    private String tokenId;

    @SerializedName("avatarImage")
    private String avatarImage;

    @SerializedName("hobbies")
    private HobbyModel[] hobbies;

    @SerializedName("coordinates")
    private double[] coordinates;

    @SerializedName("radius")
    private int radius;

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAvatarUri() {
        return avatarImage;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarImage = avatarUri;
    }

    public HobbyModel[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbyModel[] hobbies) {
        this.hobbies = hobbies;
    }
}
