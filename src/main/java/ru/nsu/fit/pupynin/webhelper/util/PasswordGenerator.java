package ru.nsu.fit.pupynin.webhelper.util;

import java.util.Random;

public class PasswordGenerator {
    public static String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder pass = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            pass.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return pass.toString();
    }
}
