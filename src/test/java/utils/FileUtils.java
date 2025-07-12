package utils;

import io.qameta.allure.Step;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileUtils {
    @Step("Write message in txt file")
    public static void writeHtmlInTxtFile(String message, String filePath) {
        File myFile = new File(filePath);
        try {
            boolean isFileCreated = myFile.createNewFile();

            if (isFileCreated) {
                System.out.println("File was created, path: " + filePath);
            } else {
                myFile.delete();
                myFile.createNewFile();
                System.out.println("File was deleted and created, path: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(myFile, true)) {
            fw.write(Objects.requireNonNull(message));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
