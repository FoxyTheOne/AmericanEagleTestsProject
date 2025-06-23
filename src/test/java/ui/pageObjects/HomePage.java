package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
        open();
    }

    // -= LOCATORS =-

    // -= ACTIONS =-
    @Step("Open homepage")
    private void open() {
        driver.get(BASE_URL);
    }

    // -= METHODS =-
}
