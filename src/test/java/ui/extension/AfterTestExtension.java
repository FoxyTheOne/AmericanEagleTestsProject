package ui.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.tests.BaseTestSettings;

import static constants.CommonConstants.NO_SCREENSHOT;

public class AfterTestExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        // Метод будет выполняться только в случае падения тестов
        if (context.getExecutionException().isPresent()) {

            Object testInstance = context.getRequiredTestInstance();

            if (testInstance instanceof BaseTestSettings) {
                BaseTestSettings baseTest = (BaseTestSettings) testInstance;
                baseTest.writeHtmlInTxtFile();
                if (!context.getTags().contains(NO_SCREENSHOT)) {
                    baseTest.captureScreenshotSpoiler();
                }
            }
        }
    }
}
