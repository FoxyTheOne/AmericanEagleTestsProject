package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ui.models.AccountDetails;
import utils.TestDataGeneratorUtils;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // -= LOCATORS =-
//    @FindBy(css = ".form-input-login") // element not interactable
    @FindBy(css = "input[placeholder='Email']")
    private WebElement emailInputElement;

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

    @FindBy(css = "input[name='acceptTerms']")
    private WebElement termsCheckbox;

    @FindBy(css = "button[data-test-btn='submit']")
    private WebElement createAccountButton;

    // -= ACTIONS =-
    @Step("Generate account details")
    public AccountDetails generateAccountDetails() {
        return AccountDetails.builder()
                .email(TestDataGeneratorUtils.generateEmail())
                .firstName(TestDataGeneratorUtils.generateFirstName())
                .lastName(TestDataGeneratorUtils.generateLastName())
                .password(TestDataGeneratorUtils.generatePassword())
                .zipCode("64648")
                .build();
    }

    @Step("Register a new user")
    public void registerUser(AccountDetails accountDetails) {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(emailInputElement)).sendKeys(accountDetails.getEmail());
        firstNameInputElement.sendKeys(accountDetails.getFirstName());
        lastNameInputElement.sendKeys(accountDetails.getLastName());
        passwordInputElement.sendKeys(accountDetails.getPassword());
        confirmPasswordInputElement.sendKeys(accountDetails.getPassword());
        zipCodeInputElement.sendKeys(accountDetails.getZipCode());

        new Select(monthSelectElement).selectByValue("1");
        new Select(daySelectElement).selectByValue("1");

        actions
                .scrollToElement(createAccountButton)
                .perform();

//        termsCheckbox.click(); -> Не работает, используем js:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", termsCheckbox);

        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
    }

    // -= METHODS =-
}
