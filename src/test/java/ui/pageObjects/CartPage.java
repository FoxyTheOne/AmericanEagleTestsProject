package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "div[data-testid='commerce-item']")
    private List<WebElement> cartItems;

    @FindBy(css = "button[data-test-btn='removeCommerceItem']")
    private List<WebElement> removeButtons;

    @FindBy(css = "div[data-testid='row-merchandise-value']")
    private WebElement merchandiseTotal;

    //    @FindBy(css = "div[data-test-shipping-message]")
    @FindBy(xpath = "//div[@class='_message-container_4drgt5']/span")
    private WebElement freeShippingMessage;

    @FindBy(css = "button[data-test-btn='editCommerceItem']")
    private WebElement editButton;

    @FindBy(css = "button.qty-inc-btn")
    private WebElement quantityIncreaseButton;

    @FindBy(css = "button.qa-item-btn-edit")
    private WebElement updateBagButton;

    @FindBy(css = "span[data-test-cart-item-sale-price]")
    private List<WebElement> itemPrices;

    // -= ACTIONS =-
    @Step("Get cart item count")
    public int getCartItemCount(WebDriverWait wait) {
        if (!cartItems.isEmpty()) {
            try {
                wait.until(ExpectedConditions.visibilityOf(cartItems.get(0)));
            } catch (TimeoutException e) {
                closeShadowWindow();
                wait.until(ExpectedConditions.visibilityOf(cartItems.get(0)));
            }
            return cartItems.size();
        } else {
            return 0;
        }
    }

    @Step("Get quantity for first item")
    public int getQuantityForFirstItem(WebDriverWait wait) {
        // TODO переделать с @FindBy
        WebElement quantityElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".cart-item-quantity")));
        return Integer.parseInt(quantityElement.getText().replace("Qty:", "").trim());
    }

    @Step("Remove first item")
    public void removeFirstItem(WebDriverWait wait) {
        if (!removeButtons.isEmpty()) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();
            } catch (TimeoutException e) {
                closeShadowWindow();
                wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();
            }
        }
    }

    @Step("Get merchandise total")
    public double getMerchandiseTotal(WebDriverWait wait) {
        String totalText;
        try {
            totalText = wait.until(ExpectedConditions.visibilityOf(merchandiseTotal))
                    .getText()
                    .replace("$", "")
                    .replace(",", "");
        } catch (TimeoutException e) {
            closeShadowWindow();
            totalText = wait.until(ExpectedConditions.visibilityOf(merchandiseTotal))
                    .getText()
                    .replace("$", "")
                    .replace(",", "");
        }
        return Double.parseDouble(totalText);
    }

    @Step("Increase item quantity")
    public void increaseQuantity(WebDriverWait wait, Actions actions) {
        LOGGER.debug("editButton click");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        } catch (TimeoutException e) {
            closeShadowWindow();
            wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        }

        LOGGER.debug("Скролл");
        actions
                .scrollToElement(updateBagButton)
                .perform();

        LOGGER.debug("quantityIncreaseButton click");
        wait.until(ExpectedConditions.elementToBeClickable(quantityIncreaseButton)).click();

        LOGGER.debug("updateBagButton click");
        updateBagButton.click();

        // Ждем обновления корзины
        wait.until(ExpectedConditions.invisibilityOf(updateBagButton));
    }

    public double getFirstItemPrice(WebDriverWait wait) {
        if (itemPrices.isEmpty()) {
            throw new RuntimeException("No items in cart");
        }

        String priceText;
        try {
            priceText = wait.until(ExpectedConditions.visibilityOf(itemPrices.get(0)))
                    .getText()
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
        } catch (TimeoutException e) {
            closeShadowWindow();
            priceText = wait.until(ExpectedConditions.visibilityOf(itemPrices.get(0)))
                    .getText()
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
        }

        return Double.parseDouble(priceText);
    }

    public void clearTheBag(WebDriverWait wait) {
        int itemCount = getCartItemCount(wait);

        for (int i = 1; i <= itemCount; i++) {
            removeFirstItem(wait);
        }
    }

    public String getshippingMessage() {
//        return freeShippingMessage.getDomProperty("text");
        return freeShippingMessage.getText();
    }

    // -= METHODS =-
}
