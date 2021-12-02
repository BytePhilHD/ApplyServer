package de.bytephil.utils;

import de.bytephil.main.Main;

public class LogInService {

    //private static String emailRoot = "root@root.com";
   // private static String password = "1234";


    public static boolean login(String webSocketAnswer) {
        int iend = webSocketAnswer.indexOf("|*|");
        int length = webSocketAnswer.length();
        String password = Main.password;
        String username = Main.username;

        String email = null;
        String pw = null;
        if (iend != -1)
        {
            email = webSocketAnswer.substring(0, iend); //this will give abc
            pw = webSocketAnswer.substring(iend+3, length); //this will give abc
        }
        return pw.equalsIgnoreCase(password) && email.equalsIgnoreCase(username);
    }
}
