package com.example.filip.gpstracker.utils;

import com.example.filip.gpstracker.pojo.Session;
import com.example.filip.gpstracker.pojo.User;
import com.firebase.client.DataSnapshot;

/**
 * Created by Filip on 06/04/2016.
 */
public class AuthenticationUtils {
    public static boolean checkIfUserAlreadyExists(String usernameToCheck, DataSnapshot snapshot) {
        for (DataSnapshot x : snapshot.getChildren()) {
            User user = x.getValue(User.class);
            if (user.getUsername().equals(usernameToCheck)) return true;
        }
        return false;
    }

    public static boolean checkIfSessionAlreadyExists(String sessionName, DataSnapshot snapshot) {
        for (DataSnapshot x : snapshot.getChildren()) {
            Session session = x.getValue(Session.class);
            if (session.getSessionName().equals(sessionName)) return true;
        }
        return false;
    }
}
