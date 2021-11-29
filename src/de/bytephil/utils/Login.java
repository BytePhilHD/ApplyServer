package de.bytephil.utils;

public class Login {

    private static String emailRoot = "root@root.com";
    private static String password = "1234";


    public static boolean login(String webSocketAnswer) {
        int iend = webSocketAnswer.indexOf("|*|");
        int length = webSocketAnswer.length();

        String email = null;
        String pw = null;
        if (iend != -1)
        {
            email = webSocketAnswer.substring(0, iend); //this will give abc
            pw = webSocketAnswer.substring(iend+3, length); //this will give abc
        }
        if (pw.equalsIgnoreCase(password) && email.equalsIgnoreCase(emailRoot)) {
            return true;
        }
        return false;
    }
}
