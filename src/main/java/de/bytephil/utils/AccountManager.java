package de.bytephil.utils;

import de.bytephil.enums.MessageType;
import de.bytephil.main.Main;
import de.bytephil.services.EmailService;
import de.bytephil.users.User;
import de.bytephil.users.UserService;

import java.io.*;
import java.util.HashMap;

public class AccountManager {

    private static HashMap<String, String> userRegistration = new HashMap<>();
    private static HashMap<String, String> userRegistrationPW = new HashMap<>();
    private static HashMap<String, String> userRegistrationEmail = new HashMap<>();


    public void addUser(String name, String username, String email, String password) throws IOException {

        if (!new File("data/users.txt").exists()) {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();
            de.bytephil.utils.Console.printout("The users file is missing. Creating a new one.", MessageType.WARNING);
            final File newFile = new File("data/users.txt");
            newFile.createNewFile();
        }

        writeUsertoFile(name, username, email, password);
    }

    public void writeUsertoFile(String name, String username, String email, String password) throws IOException {
        FileWriter fr = new FileWriter("data/users.txt", true);
        BufferedWriter br = new BufferedWriter(fr);
        PrintWriter pr = new PrintWriter(br);
        pr.println("Username=" + username);
        pr.println("Password=" + password);
        pr.close();
        br.close();
        fr.close();
        Console.printout("User " + username + " succefully added", MessageType.INFO);
    }

    public void createAccount(String username, String email, String password) {
        String passwordRegistration = PasswordGenerator.generateRandomPassword(20);
        String link = Main.config.address + "verify?" + passwordRegistration;

        if (!Main.getInstance().testing)
        EmailService.send(email, "Verify your Registration on ApplyServer", "Hi " + username + "! \n \n" +
                "To Complete your registration on ApplyServer, click the following link: " + link + " \n " +
                "If you didn't sent this registration, ignore this email. \n \n" +
                "Made by BytePhil.de");


        userRegistration.put(passwordRegistration, username);
        userRegistrationPW.put(passwordRegistration, password);
        userRegistrationEmail.put(passwordRegistration, email);
    }

    public static boolean checkVerify(String registrationKey) {
        if (userRegistration.containsKey(registrationKey)) {

            String username = userRegistration.get(registrationKey);
            String password = userRegistrationPW.get(registrationKey);
            String email = userRegistrationEmail.get(registrationKey);
            Console.printout("New Account verified: " + username + " with password " + password + " and email " + email, MessageType.INFO);

            User user = new User(username, password, email);
            new UserService().createUser(user);

            userRegistration.remove(registrationKey);
            userRegistrationPW.remove(registrationKey);
            userRegistrationEmail.remove(registrationKey);
            return true;
        }
        return false;
    }
}
