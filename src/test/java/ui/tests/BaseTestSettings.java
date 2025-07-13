package ui.tests;

import config.ITestPropertiesConfig;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.extension.AfterTestExtension;
import utils.FileUtils;

import java.time.Duration;

@Feature("Extensions")
@ExtendWith(AfterTestExtension.class)
public class BaseTestSettings {
    ITestPropertiesConfig config = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSettings.class);
    protected WebDriver driver;

    @BeforeEach
    void setup() {
        if (config.LocalOSWindows7()) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Get page source and write it in txt file")
    public void writeHtmlInTxtFile() {
        String message = driver.getPageSource();
        FileUtils.writeHtmlInTxtFile(message, "src/test/resources/my_txt.txt");
    }
}
