package de.bytephil.utils;

import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import de.bytephil.enums.MessageType;
import io.javalin.core.util.FileUtil;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileService {

    public void copyDirectoryfromResources(String resourcesName, String destinationDirectoryLocation) throws IOException, URISyntaxException {
        if (new File(destinationDirectoryLocation).exists()) {
            return;
        }
        URL res = getClass().getClassLoader().getResource(resourcesName);
        System.out.println("Ressource " + res);
        File file = Paths.get(res.toURI()).toFile();
        String sourceDirectoryLocation = file.getAbsolutePath();
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
