package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginModalComponent {
    WebDriver driver;

    public LoginModalComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -= LOCATORS =-
//    @FindBy(css = "input[id='ember38-input']") // не находит
    @FindBy(css = ".form-input-username")
    private WebElement emailInput;

    @FindBy(css = ".form-input-password")
    private WebElement passwordInput;

    @FindBy(css = "button[data-test-btn='submit']")
    private WebElement signInButton;

    @FindBy(css = "a[data-test='create-account']")
    private WebElement createAccountButton;

    @FindBy(css = "button[data-test-id='account-sign-out']")
    private WebElement signOutButton;

    @FindBy(css = "h2[class='modal-title']")
    private WebElement sidetrayTitleElement;

    // -= ACTIONS =-
    @Step("Log in")
    public void login(WebDriverWait wait, Actions actions, String email, char[] passwordArray) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);

        for (char passwordChar : passwordArray) {
            passwordInput.sendKeys(String.valueOf(passwordChar));
        }

//        actions.sendKeys(passwordInput, Keys.chord(String.valueOf(passwordArray)));
//        try {
//            Thread.sleep(4000);
//            signInButton.click();
//        } catch (InterruptedException ignored) {
//        }

        signInButton.click();

        actions.sendKeys(Keys.ESCAPE).perform();
    }

    @Step("Get sidetray title")
    public String getSidetrayTitle() {
        return sidetrayTitleElement.getText();
    }

    @Step("Log out")
    public void signOut(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(signOutButton)).click();
    }

    // -= METHODS =-
}
