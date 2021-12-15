package de.bytephil.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ApplicationService {

    public static void addnew(String name, String discord) throws IOException {
        FileWriter fileWriter = new FileWriter("data/Applications.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println("Username: " + name);
        printWriter.println("Discord name: " + discord);
        printWriter.close();

        readFile();

        //TODO Change Service for Saving data to MySQL!
    }

    public static void readFile() {
        File file = new File("data/Applications.txt");

    }
}
