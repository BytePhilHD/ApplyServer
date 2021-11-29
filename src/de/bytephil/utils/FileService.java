package de.bytephil.utils;

import io.javalin.core.util.FileUtil;
import org.codehaus.plexus.util.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {


    public void copyDirectoryfromResources(String resourcesName, String destinationDirectoryLocation) throws IOException, URISyntaxException {
        if (new File(destinationDirectoryLocation).exists()) {
            return;
        }
        URL res = getClass().getClassLoader().getResource(resourcesName);
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

    public void copyFile(File newFile, String existingFile) throws IOException {
        newFile.createNewFile();
        final FileOutputStream configOutputStream = new FileOutputStream(newFile);
        byte[] buffer = new byte[4096];
        final InputStream defaultConfStream = getClass().getClassLoader().getResourceAsStream(existingFile);
        int readBytes;
        while ((readBytes = defaultConfStream.read(buffer)) > 0) {
            configOutputStream.write(buffer, 0, readBytes);
        }
        defaultConfStream.close();
    }
}
