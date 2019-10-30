package com.hobbymeetingapp.app.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("nick")
    private String nick;

    @SerializedName("email")
    private String email;

    @SerializedName("tokenId")
    private String tokenId;

    @SerializedName("avatarUri")
    private String avatarUri;

    @SerializedName("hobbies")
    private HobbyModel[] hobbies;

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
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public HobbyModel[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbyModel[] hobbies) {
        this.hobbies = hobbies;
    }
}
