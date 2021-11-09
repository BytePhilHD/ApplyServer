package de.bytephil.utils;

import de.bytephil.enums.MessageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LogFile {

    public void logFileCreation() throws IOException {
        if (!new File("logs/log.txt").exists()) {
            File dir = new File("logs");
            if (!dir.exists()) dir.mkdirs();
            de.bytephil.utils.Console.printout("The log file is missing. Creating a new one.", MessageType.WARNING);
            final File newFile = new File("logs/log.txt");
            newFile.createNewFile();
        }
    }
}
