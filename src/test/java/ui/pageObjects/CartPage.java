package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @FindBy(css = "div[data-testid='row-shipping-value']")
    private WebElement shippingPrice;

    @FindBy(css = "div[data-testid='row-total-value']")
    private WebElement subTotalPrice;

    @FindBy(css = "div[id='notifications-wormhole-destination']")
    private WebElement test;

    // -= ACTIONS =-
    @Step("Get cart item count")
    public int getCartItemCount() {
        if (!cartItems.isEmpty()) {
            closePopUpWindowIfExists();
            wait5sec.until(ExpectedConditions.visibilityOf(cartItems.get(0)));
            return cartItems.size();
        } else {
            return 0;
        }
    }

    @Step("Get quantity for first item")
    public int getQuantityForFirstItem() {
        closePopUpWindowIfExists();
        WebElement quantityElement = wait5sec.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".cart-item-quantity")));
        return Integer.parseInt(quantityElement.getText().replace("Qty:", "").trim());
    }

    @Step("Remove first item")
    public void removeFirstItem(int currentNumberOfItems) {
        if (!removeButtons.isEmpty()) {
            closePopUpWindowIfExists();
            wait5sec.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();
            wait5sec.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("div[data-testid='commerce-item']"), currentNumberOfItems));
        }
    }

    @Step("Get merchandise total")
    public double getMerchandiseTotal() {
        closePopUpWindowIfExists();
        String totalText = wait5sec.until(ExpectedConditions.visibilityOf(merchandiseTotal))
                .getText()
                .replace("$", "")
                .replace(",", "");
        return Double.parseDouble(totalText);
    }

    @Step("Increase item quantity")
    public void increaseQuantity() {
        closePopUpWindowIfExists();
        LOGGER.debug("editButton click");
        wait5sec.until(ExpectedConditions.elementToBeClickable(editButton)).click();

        LOGGER.debug("Скролл");
        actions
                .scrollToElement(updateBagButton)
                .perform();

        LOGGER.debug("quantityIncreaseButton click");
        wait5sec.until(ExpectedConditions.elementToBeClickable(quantityIncreaseButton)).click();

        LOGGER.debug("updateBagButton click");
        updateBagButton.click();

        // Ждем обновления корзины
        wait5sec.until(ExpectedConditions.invisibilityOf(updateBagButton));
    }

    @Step("Get the price of the first item in cart")
    public double getFirstItemPrice() {
        closePopUpWindowIfExists();

        if (itemPrices.isEmpty()) {
            throw new RuntimeException("No items in cart");
        }

        String priceText = wait5sec.until(ExpectedConditions.visibilityOf(itemPrices.get(0)))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(priceText);
    }

    @Step("Get the shipping price in cart")
    public double getShippingPrice() {
        closePopUpWindowIfExists();

        String priceText = wait5sec.until(ExpectedConditions.visibilityOf(shippingPrice)).getText();

        if (priceText.equals("Free")) {
            return 0;
        } else {
            return Double.parseDouble(priceText
                    .replace("$", "")
                    .replace(",", "")
                    .trim());
        }
    }

    @Step("Get the total price in cart")
    public double getSubTotalPrice() {
        closePopUpWindowIfExists();

        String priceText = wait5sec.until(ExpectedConditions.visibilityOf(subTotalPrice))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(priceText);
    }

    @Step("Clear the bag")
    public void clearTheBag() {
        closePopUpWindowIfExists();

        int itemCount = getCartItemCount();

        for (int i = 1; i <= itemCount; i++) {
            int currentCount = getCartItemCount();
            removeFirstItem(currentCount);
        }
    }

    @Step("Get the shipping message")
    public String getShippingMessage() {
        closePopUpWindowIfExists();
        return freeShippingMessage.getText();
    }

    // -= METHODS =-
}
