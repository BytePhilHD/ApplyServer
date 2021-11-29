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

    public void testandcreateFile(String path, String existingFile, boolean isdirectory) throws IOException {
        if (!new File(path).exists()) {
            final File destinationFile = new File(path);
            final File sourceDirectory1 = new File(existingFile);

            /*
            try {
                String folder;
                int iend = path.indexOf("/");
                if (iend != -1) {
                    folder = path.substring(0, iend);
                    File dir = new File(folder);
                    if (!dir.exists()) dir.mkdirs();
                }
            } catch (Exception e1) {}

             */

            if (isdirectory) {

                //FileUtils.copyDirectory(sourceDirectory, destinationFile);
            } else {
                copyFile(destinationFile, existingFile);
            }
        }
    }
    public void copyDirectory(String resourcesName, String destinationDirectoryLocation) throws IOException, URISyntaxException {
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
