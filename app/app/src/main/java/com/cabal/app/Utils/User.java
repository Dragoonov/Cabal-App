package com.cabal.app.Utils;

import com.cabal.app.models.UserModel;

public class User {
    private static UserModel loggedUser;

    public static String getUserNick(){
        return loggedUser.getNick();
    }

    public static void saveUser(UserModel user) {
        loggedUser = user;
    }
}
