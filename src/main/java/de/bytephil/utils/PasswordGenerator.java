package de.bytephil.utils;

import java.util.Random;

public class PasswordGenerator {

    public static String generateRandomPassword(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
                +"jklmnopqrstuvwxyz!";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
    public static String generateID(int length) {
        String chars = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
