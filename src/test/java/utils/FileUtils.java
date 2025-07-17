package utils;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.tests.BaseTestSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSettings.class);

    @Step("Write message in txt file")
    public static void writeHtmlInTxtFile(String message, String filePath) {
        File myFile = new File(filePath);
        try {
            boolean isFileCreated = myFile.createNewFile();

            if (isFileCreated) {
                LOGGER.debug("File was created, path: {}", filePath);
            } else {
                myFile.delete();
                myFile.createNewFile();
                LOGGER.debug("File was deleted and created, path: {}", filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(myFile, true)) {
            fw.write(Objects.requireNonNull(message));
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
    }
}
