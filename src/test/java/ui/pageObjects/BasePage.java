package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.pageObjects.components.HeaderComponent;

/**
 * Класс для общих элементов сайта и общих действий
 */
public class BasePage {
    WebDriver driver;
    HeaderComponent header;
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    // Константы, используемые на разных страницах
    public static final String BASE_URL = "https://www.ae.com";
    public static final String HOME_URL = "/us/en";
    public static final String WOMEN_SKIRTS_URL = "/c/women/bottoms/skirts-skorts";
    public static final String FAVORITES_URL = "/favorites";
    public static final String CART_URL = "/cart";
    public static final String GENERAL_WEB_TITLE_CONTAINS_EXPECTED = " | American Eagle";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        header = new HeaderComponent(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "div[class='bloomreach-weblayer']")
    private WebElement contentShadow;

    // -= ACTIONS =-
    public HeaderComponent header() {
        return header;
    }

    @Step("Get general web site title")
    public String getGeneralWebTitle() {
        return driver.getTitle();
    }

    @Step("Get current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Find and close shadow window")
    public void closeShadowWindow() {
        try {
            SearchContext shadowRoot = contentShadow.getShadowRoot();
            WebElement closeButton = shadowRoot.findElement(By.cssSelector("button[class='close']"));
            closeButton.click();
            System.out.println("Shadow DOM closed");
        } catch (org.openqa.selenium.NoSuchElementException | StaleElementReferenceException ignored) {
            // Игнорируем, если не было всплывающего окна
        }
    }

    // -= METHODS =-
}
