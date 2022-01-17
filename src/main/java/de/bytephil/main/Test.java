package de.bytephil.main;

import de.bytephil.enums.Rank;
import de.bytephil.users.Application;
import de.bytephil.users.ApplicationService;
import de.bytephil.users.User;
import de.bytephil.users.UserService;
import de.bytephil.utils.WebSocketHandler;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        String input = "BytePhil|*|PhilsPW!|'|bytephil@gmail.com";

        int iend = input.indexOf("|*|");
        int iend1 = input.indexOf("|'|");
        int length = input.length();

        String username = null;
        String password = null;
        String email = null;
        if (iend != -1) {
            username = input.substring(0, iend);
            password = input.substring(iend + 3, iend1);
            email = input.substring(iend1 + 3, length);
        }

        System.out.println("Username: " + username + ", PW: " + password + ", Email: " + email);

        // Output: "Username: BytePhil, PW: PhilsPW!, Email: bytephil@gmail.com"
    }

    public void testMethod() {

    }
}
