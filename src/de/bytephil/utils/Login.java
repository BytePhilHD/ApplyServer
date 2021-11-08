package de.bytephil.utils;

public class Login {

    /*
        String filename = "phitho|*|Collins234!";     // full file name
        int iend = filename.indexOf("|*|"); //this finds the first occurrence of "."
        int length = filename.length();

        String subString = null;
        if (iend != -1)
        {
            subString= filename.substring(iend+3, length); //this will give abc
        }
        System.out.println(subString);
     */

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
        System.out.println("PW: " + pw);
        System.out.println("Email: " + email);
        return true;

        //TODO Überprüfung ob Email und PW existieren
    }
}
