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
// Methods -> for ex open another Page Object
//    // Чтобы перейти на Web form, нам нужно нажать ссылку на главной странице, поэтому переход туда описываем здесь
//    @Step("Open Web form page")
//    public WebFormPage openWebFormPage() {
//        driver.findElement(By.linkText("Web form")).click();
//        return new WebFormPage(driver);
//    }
//
//    @Step("Open Navigation page")
//    public NavigationPage openNavigationPage() {
//        driver.findElement(By.linkText("Navigation")).click();
//        return new NavigationPage(driver);
//    }
}
