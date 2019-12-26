package com.cabal.app.Utils;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class AfterRegisterUserData {
    private String avatar;
    private String nickname;
    private int searchRadius;
    private int[] interestIds;

    public int getSearchRadius() {
        return searchRadius;
    }

    public void setSearchRadius(int searchRadius) {
        this.searchRadius = searchRadius;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int[] getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(int[] interestIds) {
        this.interestIds = interestIds;
    }

    public AfterRegisterUserData(String avatar, String nickname, int[] interestIds, int searchRadius) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.interestIds = interestIds;
        this.searchRadius = searchRadius;
    }

    @NonNull
    @Override
    public String toString() {
        return "AfterRegisterUserData{" +
                "avatar length='" + avatar.length() + '\'' +
                ", nickname='" + nickname + '\'' +
                ", interestIds=" + Arrays.toString(interestIds) +
                '}';
    }
}
