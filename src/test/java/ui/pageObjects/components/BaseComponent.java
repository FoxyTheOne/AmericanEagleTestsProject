package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BaseComponent {
    WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait5sec;
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseComponent.class);

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
        wait5sec = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // -= LOCATORS =-
    @FindBy(css = "div[class='bloomreach-weblayer']")
    private WebElement contentShadow;

    // -= ACTIONS =-
    @Step("Find and close shadow window")
    public void closePopUpWindowIfExists() {
        try {
            SearchContext shadowRoot = contentShadow.getShadowRoot();
            WebElement closeButton = shadowRoot.findElement(By.cssSelector("button[class='close']"));
            closeButton.click();
            LOGGER.debug("Pop up window closed");
        } catch (org.openqa.selenium.NoSuchElementException | StaleElementReferenceException ignored) {
            // Игнорируем, если не было всплывающего окна
        }
    }

    // -= METHODS =-
}
