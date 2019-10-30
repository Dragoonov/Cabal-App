package com.hobbymeetingapp.app.Utils;

import com.hobbymeetingapp.app.models.UserModel;

public class User {
    private static UserModel loggedUser;

    public static String getUserNick(){
        return loggedUser.getNick();
    }

    public static void saveUser(UserModel user) {
        loggedUser = user;
    }
}
