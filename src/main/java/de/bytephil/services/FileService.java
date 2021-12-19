package de.bytephil.services;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

public class FileService {

    public void copyDirectoryfromResources(String resourcesName, String destinationDirectoryLocation) throws IOException, URISyntaxException {
        if (new File(destinationDirectoryLocation).exists()) {
            return;
        }

        URL res = getClass().getClassLoader().getResource(resourcesName);
        System.out.println("Resource " + res);
        assert res != null;
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