package ui.tests;

import config.ITestPropertiesConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.extension.AfterTestExtension;
import utils.FileUtils;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

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
        initDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Capture screenshot (spoiler)")
    public void captureScreenshotSpoiler() {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Step("Get page source and write it in txt file")
    public void writeHtmlInTxtFile() {
        String message = driver.getPageSource();
        FileUtils.writeHtmlInTxtFile(message, "src/test/resources/my_txt.txt");
    }

    private void initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Allure.addAttachment("remote", remoteUrl);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            options.addArguments("--window-size=1920,1080");
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
                driver.manage().window().setSize(new Dimension(1920, 1080));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }
    }

//    private void initDriver() {
//        String remoteUrl = System.getenv("SELENIDE_REMOTE_URL");
//        if (remoteUrl != null && !remoteUrl.isEmpty()) {
//            Allure.addAttachment("remote", remoteUrl);
//            ChromeOptions options = new ChromeOptions();
//
//            // Общие настройки
//            options.addArguments("--headless");  // Add headless mode
//            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
//            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
//            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
//
//            // Уникальный user-data-dir для каждого экземпляра (В CI/CD был конфликт)
//            options.addArguments("--user-data-dir=/tmp/chrome_profile_" + UUID.randomUUID());
//
//            // Дополнительные параметры для стабильности
//            options.addArguments("--remote-debugging-port=0");
//            options.addArguments("--disable-setuid-sandbox");
//            options.addArguments("--disable-extensions");
//
//            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
//            try {
//                driver = new RemoteWebDriver(new URL(remoteUrl), options);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
//            }
//        } else {
//            driver = new ChromeDriver();
//        }
//    }
}
