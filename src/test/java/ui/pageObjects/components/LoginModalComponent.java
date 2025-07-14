package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginModalComponent extends BaseComponent {
    public LoginModalComponent(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = ".form-input-username")
    private WebElement emailInput;

    @FindBy(css = ".form-input-password")
    private WebElement passwordInput;

    @FindBy(css = "button[data-test-btn='submit']")
    private WebElement signInButton;

    @FindBy(css = "a[data-test='create-account']")
    private WebElement createAccountButton;

    @FindBy(css = "button[data-test-btn='sign-out']")
    private WebElement signOutButton;

    @FindBy(xpath = "//div[@class='modal-dialog']/div/div/h2")
    private WebElement sidetrayTitleElement;

    // -= ACTIONS =-
    @Step("Log in")
    public void login(String email, char[] passwordArray) {
        closePopUpWindowIfExists();
        wait10sec.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);

        for (char passwordChar : passwordArray) {
            passwordInput.sendKeys(String.valueOf(passwordChar));
        }

        signInButton.click();
        closePopUpWindowIfExists();
        waitingForSignOutButton();

        actions.sendKeys(Keys.ESCAPE).perform();
    }

    @Step("Get sidetray title")
    public String getSidetrayTitle() {
        closePopUpWindowIfExists();
        return sidetrayTitleElement.getText();
    }

    @Step("Waiting for log out button")
    public void waitingForSignOutButton() {
        closePopUpWindowIfExists();
        wait10sec.until(ExpectedConditions.elementToBeClickable(signOutButton));
    }

    @Step("Log out")
    public void signOut() {
        waitingForSignOutButton();
        signOutButton.click();
    }

    // -= METHODS =-
}
