package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static constants.CommonConstants.PRICE_FOR_UNLOCKING_FREE_SHIPPING;

public class WomenSkirtsSkortsPage extends BasePage {
    public WomenSkirtsSkortsPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "div[data-testid='product-content'] div[data-test-product-tile]")
    private List<WebElement> productTiles;

    @FindBy(css = "span._sale-price_gpxs4e[data-test-sale-price]")
    private WebElement productSumInModal;

    // -= ACTIONS =-
    @Step("Adding items to cart until free shipping us unlocked")
    public void addItemsToUnlockFreeShipping() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOfAllElements(productTiles));
        closePopUpWindowIfExists();
        int productTilesCount = productTiles.size();
        double totalSum = 0;

        // Перебираем продукты в каталоге
        for (int i = 0; i < productTilesCount; i++) {
            closePopUpWindowIfExists();
            wait5sec.until(ExpectedConditions.visibilityOfAllElements(productTiles));

            closePopUpWindowIfExists();
            productTiles.get(i).click();
            ProductPage productPage = new ProductPage(driver);
            productPage.selectFirstAvailableSize();

            int productCount = productPage.addToCartMaxQuantityWithoutClosingModal();

            double productSum = getProductPriceFromModal();
            productPage.closeModalAfterAddingToCart();

            totalSum = totalSum + productSum * productCount;
            LOGGER.debug("productSum: {}, productCount: {}", productSum, productCount);
            LOGGER.debug("totalProductSum: {}", productSum * productCount);
            LOGGER.debug("totalSum: {}", totalSum);

            // Возвращаемся на страницу каталога
            productPage.header().openWomenMenu();
            productPage.header().openWomenSkirtsSkortsPage();

            if (totalSum >= PRICE_FOR_UNLOCKING_FREE_SHIPPING) {
                LOGGER.debug("totalSum is greater than minimum price for unlocking free shipping: {}", totalSum);
                break;
            }
        }
    }

    @Step("Get product price from modal")
    public double getProductPriceFromModal() {
        wait5sec.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.modal-dialog")
        ));
        wait5sec.until(ExpectedConditions.visibilityOf(productSumInModal));
        String priceText = productSumInModal.getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(priceText);
    }

    @Step("Wait for product tiles")
    public void waitForProductTiles() {
        closePopUpWindowIfExists();
        if (!productTiles.isEmpty()) {
            wait5sec.until(ExpectedConditions.visibilityOf(productTiles.get(0)));
        }
    }

    // -= METHODS =-
    @Step("Select first available product and open product page")
    public ProductPage selectFirstAvailableProductAndOpenPage() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOfAllElements(productTiles));

        if (!productTiles.isEmpty()) {
            productTiles.get(0).click();
        }
        return new ProductPage(driver);
    }
}
