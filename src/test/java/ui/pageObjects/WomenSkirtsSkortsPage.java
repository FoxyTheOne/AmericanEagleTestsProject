package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WomenSkirtsSkortsPage extends BasePage {
    public WomenSkirtsSkortsPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "div[data-testid='product-content'] div[data-test-product-tile]")
    private List<WebElement> productTiles;

    // -= ACTIONS =-

    // -= METHODS =-
    @Step("Select first available product and open product page")
    public ProductPage selectFirstAvailableProductAndOpenPage(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productTiles));
        } catch (TimeoutException e) {
            closeShadowWindow();
            wait.until(ExpectedConditions.visibilityOfAllElements(productTiles));
        }
        if (!productTiles.isEmpty()) {
            productTiles.get(0).click();
        }
        return new ProductPage(driver);
    }
}
