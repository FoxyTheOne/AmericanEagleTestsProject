package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
//    @FindBy(css = ".form-input-login") // element not interactable
//    @FindBy(css = "input[id='ember76-input']") // no such element: Unable to locate element
    @FindBy(css = "input[placeholder='Email']")
    private WebElement emailInputElement;

    //    @FindBy(css = ".form-input-firstname") // element not interactable
    @FindBy(css = "input[placeholder='First Name']")
    private WebElement firstNameInputElement;

    @FindBy(css = "input[placeholder='Last Name']")
    private WebElement lastNameInputElement;

    @FindBy(css = "input[placeholder='Password']")
    private WebElement passwordInputElement;

    @FindBy(css = "input[placeholder='Confirm Password']")
    private WebElement confirmPasswordInputElement;

    @FindBy(css = "input[placeholder='Zip Code']")
    private WebElement zipCodeInputElement;

    @FindBy(css = "select[name='month']")
    private WebElement monthSelectElement;

    @FindBy(css = "select[name='day']")
    private WebElement daySelectElement;

    @FindBy(css = "input[name='acceptTerms']") // Expected condition failed: waiting for element to be clickable
//    @FindBy(css = ".aeo-checkbox-label") // Переходит по ссылке
//    @FindBy(xpath = "//div[@data-test-checkbox='acceptTerms']/input") // Expected condition failed: waiting for element to be clickable
//    @FindBy(css = "div[data-test-checkbox='acceptTerms']")
    private WebElement termsCheckbox;

    @FindBy(css = "button[data-test-btn='submit']")
    private WebElement createAccountButton;

    // -= ACTIONS =-
    @Step("Register a new user")
    public void registerUser(WebDriverWait wait, Actions actions, String email, String password, String firstName, String lastName, String zipCode) {
        wait.until(ExpectedConditions.visibilityOf(emailInputElement)).sendKeys(email);
        firstNameInputElement.sendKeys(firstName);
        lastNameInputElement.sendKeys(lastName);
        passwordInputElement.sendKeys(password);
        confirmPasswordInputElement.sendKeys(password);
        zipCodeInputElement.sendKeys(zipCode);

        // Select birthday (random values)
        new Select(monthSelectElement).selectByValue("1");
        new Select(daySelectElement).selectByValue("1");

        actions
                .scrollToElement(createAccountButton)
                .perform();

        // TODO нажатие на checkbox
//        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
//        actions.moveToElement(termsCheckbox).click();
        termsCheckbox.click();

        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
    }

    // -= METHODS =-
}
