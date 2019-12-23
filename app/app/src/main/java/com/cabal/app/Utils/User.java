package com.cabal.app.Utils;

import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.UserModel;


public class User {

    private static UserModel loggedUser;

    public static int getId() {
        return loggedUser.getId();
    }

    public static void setId(int id) {
        loggedUser.setId(id);
    }

    public static String getAvatarImage() {
        return loggedUser.getAvatarImage();
    }

    public static void setAvatarImage(String avatarImage) {
        loggedUser.setAvatarImage(avatarImage);
    }

    public static double[] getCoordinates() {
        return loggedUser.getCoordinates();
    }

    public static void setCoordinates(double[] coordinates) {
        loggedUser.setCoordinates(coordinates);
    }

    public static int getRadius() {
        return loggedUser.getRadius();
    }

    public static void setRadius(int radius) {
        loggedUser.setRadius(radius);
    }

    public static String getNick() {
        return loggedUser.getNick();
    }

    public static void setNick(String nick) {
        loggedUser.setNick(nick);
    }

    public static String getEmail() {
        return loggedUser.getEmail();
    }

    public static void setEmail(String email) {
        loggedUser.setEmail(email);
    }

    public static String getTokenId() {
        return loggedUser.getTokenId();
    }

    public static void setTokenId(String tokenId) {
        loggedUser.setTokenId(tokenId);
    }

    public static String getAvatarUri() {
        return loggedUser.getAvatarUri();
    }

    public static void setAvatarUri(String avatarUri) {
        loggedUser.setAvatarUri(avatarUri);
    }

    public static HobbyModel[] getHobbies() {
        return loggedUser.getHobbies();
    }

    public static void setHobbies(HobbyModel[] hobbies) {
        loggedUser.setHobbies(hobbies);
    }

    public static void instantiate() {
        if (loggedUser == null) {
            loggedUser = new UserModel();
        }
    }


    public static void setLoggedUser(UserModel loggedUser) {
        User.loggedUser = loggedUser;
    }

    public static UserModel getLoggedUser() {
        return loggedUser;
    }


}
