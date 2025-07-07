package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "svg[data-testid='icon-extended_returns']")
    private WebElement iconReturnItemsSvg;

    @FindBy(css = "div[data-test-select-custom='size']")
    private WebElement sizeSelectContainer;

    @FindBy(css = "button[data-test-btn='addToBag']")
    private WebElement addToBagButton;

    @FindBy(css = "div[data-test-product-prices] div.product-sale-price")
    private WebElement productPriceElement;

    @FindBy(css = ".dropdown-toggle")
    private WebElement sizeDropdownToggle;

    @FindBy(css = "div[data-test-select-custom='size'] .dropdown-menu li:not(.visually-disabled)")
    private List<WebElement> availableSizes;

    //    @FindBy(css = ".modal-dialog.modal-content.modal-header.btn-close")
    @FindBy(xpath = "//div[@class='modal-dialog']/div/div/button")
    private WebElement closeButton;

    // -= ACTIONS =-
    @Step("Select first available size")
    public void selectFirstAvailableSize(WebDriverWait wait, Actions actions) {
        LOGGER.debug("Скролл");
        actions
                .scrollToElement(iconReturnItemsSvg)
                .perform();

        try {
            LOGGER.debug("Раскрываем выпадающий список размеров");
            wait.until(ExpectedConditions.elementToBeClickable(sizeDropdownToggle)).click();
        } catch (TimeoutException e) {
            closeShadowWindow();
            wait.until(ExpectedConditions.elementToBeClickable(sizeDropdownToggle)).click();
        }

        LOGGER.debug("Ждем появления доступных размеров");
        wait.until(ExpectedConditions.visibilityOfAllElements(availableSizes));

        LOGGER.debug("Выбираем первый доступный размер");
        if (!availableSizes.isEmpty()) {
            LOGGER.debug("Клик");
            availableSizes.get(0).click();
        } else {
            throw new RuntimeException("No available sizes found");
        }
    }

    @Step("Click add to bag button")
    public void addToCart(WebDriverWait wait, Actions actions) {
        LOGGER.debug("addToBagButton click in addToCart");
        wait.until(ExpectedConditions.elementToBeClickable(addToBagButton)).click();

//        LOGGER.debug("Keys.ESCAPE click in addToCart");
//        actions.sendKeys(Keys.ESCAPE).perform();

        LOGGER.debug("closeButton click in addToCart");
        closeButton.click();
    }

    @Step("Get product price")
    public double getProductPrice(WebDriverWait wait) {
        String priceText;
        try {
            // Обрабатываем случай, когда цена содержит дополнительный текст (например "Now $44.95")
            priceText = wait.until(ExpectedConditions.visibilityOf(productPriceElement))
                    .getText()
                    .replace("Now", "")
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
        } catch (TimeoutException e) {
            closeShadowWindow();
            priceText = wait.until(ExpectedConditions.visibilityOf(productPriceElement))
                    .getText()
                    .replace("Now", "")
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
        }
        return Double.parseDouble(priceText);
    }

    // -= METHODS =-
}
