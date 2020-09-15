package iomanager;

import java.io.*;
import java.nio.file.*;

public class InputOutputManagerUtils {

    public static void writeToFile(String path, String message) throws FileNotExistsException, IOException {
        File file = new File(path);
        if (!file.exists())
            throw new FileNotExistsException("This file is not exist. Please create file first.");
        try {
            Files.write(file.toPath(), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static String readFile(String path) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static boolean createFile(String path) {
        boolean successfullyCreated = false;
        File f = new File(path);

        successfullyCreated = f.getParentFile().mkdirs();
        try {
            successfullyCreated = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return successfullyCreated;
    }
}
