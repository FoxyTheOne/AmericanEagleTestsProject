package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @FindBy(css = "button[aria-label='increase']")
    private WebElement addQuantityButton;

    @FindBy(xpath = "//div[@class='modal-dialog']/div/div/button")
    private WebElement closeButton;

    // -= ACTIONS =-
    @Step("Select first available size")
    public void selectFirstAvailableSize() {
        closePopUpWindowIfExists();
        LOGGER.debug("Скролл");
        actions
                .scrollToElement(iconReturnItemsSvg)
                .perform();

        closePopUpWindowIfExists();
        LOGGER.debug("Раскрываем выпадающий список размеров");
        wait10sec.until(ExpectedConditions.elementToBeClickable(sizeDropdownToggle)).click();

        LOGGER.debug("Ждем появления доступных размеров");
        wait10sec.until(ExpectedConditions.visibilityOfAllElements(availableSizes));

        LOGGER.debug("Выбираем первый доступный размер");
        if (!availableSizes.isEmpty()) {
            LOGGER.debug("Клик");
            availableSizes.get(0).click();
        } else {
            throw new RuntimeException("No available sizes found");
        }
    }

    @Step("Click add to bag button")
    public void addToCart() {
        closePopUpWindowIfExists();
        LOGGER.debug("addToBagButton click in addToCart");
        wait10sec.until(ExpectedConditions.elementToBeClickable(addToBagButton)).click();

        LOGGER.debug("closeButton click in after adding to cart");
        closeButton.click();
    }

    @Step("Click add to bag button MAX times without closing modal")
    public int addToCartMaxQuantityWithoutClosingModal() {
        closePopUpWindowIfExists();

        int clickCount = 0;
        while (addQuantityButton.isEnabled()) {
            addQuantityButton.click();
            clickCount++;
        }

        wait10sec.until(ExpectedConditions.elementToBeClickable(addToBagButton)).click();
        return clickCount;
    }

    @Step("Close modal after adding to cart")
    public void closeModalAfterAddingToCart() {
        closeButton.click();
    }

    @Step("Get product price")
    public double getProductPrice() {
        closePopUpWindowIfExists();
        // Обрабатываем случай, когда цена содержит дополнительный текст (например "Now $44.95")
        String priceText = wait10sec.until(ExpectedConditions.visibilityOf(productPriceElement))
                .getText()
                .replace("Now", "")
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(priceText);
    }

    // -= METHODS =-
}
