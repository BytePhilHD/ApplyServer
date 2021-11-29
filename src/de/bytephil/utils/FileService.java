package de.bytephil.utils;

import java.io.*;

public class FileService {

    public void testandcreateFile(String path, String existingFile) throws IOException {
        if (!new File(path).exists()) {
            final File file = new File(path);
            if (file.isDirectory()) {

            } else {
                try {
                    String folder;
                    int iend = path.indexOf("/");
                    if (iend != -1) {
                        folder = path.substring(0, iend);
                        File dir = new File(folder);
                        if (!dir.exists()) dir.mkdirs();
                    }
                } catch (Exception e1) {
                }

                copyFile(file, existingFile);
            }
        }
    }

    public void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectory(source, destination);
        } else {
            copyDirectoryFiles(source, destination);
        }
    }

    private void copyDirectoryFiles(File sourceFile, File destinationFile)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
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
