package com.cabal.app.Utils;

import android.util.Pair;

import com.cabal.app.models.HobbyModel;
import com.cabal.app.models.UserModel;

import java.util.List;

public class User {
    private static UserModel loggedUser;

    private static Pair<Double, Double> currentCoordinates;

    private static List<HobbyModel> hobbies;

    public static UserModel getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(UserModel loggedUser) {
        User.loggedUser = loggedUser;
    }

    public static String getUserNick(){
        return loggedUser.getNick();
    }
    public static Pair<Double, Double> getCurrentCoordinates() {
        return currentCoordinates;
    }

    public static void setCurrentCoordinates(Pair<Double, Double> currentCoordinates) {
        User.currentCoordinates = currentCoordinates;
    }

    public static List<HobbyModel> getHobbies() {
        return hobbies;
    }

    public static void setHobbies(List<HobbyModel> hobbies) {
        User.hobbies = hobbies;
    }
}
