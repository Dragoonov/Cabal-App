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
