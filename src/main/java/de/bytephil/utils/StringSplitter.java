package de.bytephil.utils;

import de.bytephil.main.Main;

public class StringSplitter {

    public static void createAccount(String webSocketAnswer) {
        int iend = webSocketAnswer.indexOf("|*|");
        int iend1 = webSocketAnswer.indexOf("|'|");
        int length = webSocketAnswer.length();

        String email = null;
        String pw = null;
        String user = null;
        if (iend != -1)
        {
            user = webSocketAnswer.substring(0, iend); //this will give abc
            email = webSocketAnswer.substring(iend+3, iend1); //this will give abc
            pw = webSocketAnswer.substring(iend1+3, length);
        }
        new AccountManager().createAccount(user, email, pw);
    }
}
