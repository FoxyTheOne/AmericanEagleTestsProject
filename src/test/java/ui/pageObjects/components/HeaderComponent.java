package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class HeaderComponent {
    WebDriver driver;

    public static final String MAIN_LOGO_TITLE_CONTAINS_EXPECTED = "American Eagle Outfitters";
    public static final String MAIN_LOGO_TEXT = "Go to aeo homepage";
    public static final String SKIRTS_LINK_TEXT_CONTENT = "Skirts & Skorts";

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -= LOCATORS =-
    @FindBy(css = "a[data-testid='aeo-logo']")
    private WebElement mainLogo;

    @FindBy(xpath = "//a[text()='Women']")
    private WebElement womenMenuElement;

    @FindBy(xpath = "//a[contains(@class, '_column-link_') and contains(@href, 'skirts-skorts')]")
    private WebElement skirtsLinkElement;

    @FindBy(css = "button[data-test-btn='search-cta']")
    private WebElement searchButtonElement;

    @FindBy(css = "input[type='search']")
    private WebElement searchInputElement;

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement accountIconElement;

    @FindBy(xpath = "//button[@data-test-btn='signin']")
    private WebElement signInButtonElement;

    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButtonElement;

    //    @FindBy(xpath = "//a[@id='ember3']") // Поиском по ember3 находит разные, поэтому уточняю с помощью $
    @FindBy(css = "a[id$='ember3']")
    private WebElement favoritesLinkElement;

    //    @FindBy(xpath = "//svg[@data-testid='icon-favorites']") // no such element: Unable to locate element: {"method":"xpath","selector":"//svg[@data-testid='icon-favorites']"}
    @FindBy(css = "svg[data-testid='icon-favorites']")
    private WebElement favoritesSvgElement;

    //    @FindBy(xpath = "//a[@id='ember4']") // Поиском по ember4 находит разные, поэтому уточняю с помощью $
    @FindBy(css = "a[id$='ember4']")
    private WebElement cartLinkElement;

    //    @FindBy(xpath = "//svg[@data-testid='icon-bag']") // no such element: Unable to locate element: {"method":"xpath","selector":"//svg[@data-testid='icon-bag']"}
    @FindBy(css = "svg[data-testid='icon-bag']")
    private WebElement cartButtonSvgElement;

    @FindBy(xpath = "//a[@id='ember4']/span/span")
    private WebElement cartIndicatorElement;

    // -= ACTIONS =-
    @Step("Get main logo text")
    public String getMainLogoText() {
        return mainLogo.getText();
    }

    @Step("Get main logo link")
    public String getMainLogoHref() {
        return mainLogo.getDomProperty("href");
    }

    @Step("Get main logo title")
    public String getMainLogoTitle() {
        return mainLogo.getDomProperty("title");
    }

    @Step("Check if main logo svg is displayed")
    public Boolean isMainLogoSvgDisplayed() {
        // SVG логотип внутри элемента:
        WebElement svg = mainLogo.findElement(By.cssSelector("svg.aeo-icon-brand-max-aeo"));
        return svg.isDisplayed();
    }

    @Step("Open women menu")
    public void openWomenMenu(Actions actions) {
        actions.moveToElement(womenMenuElement).perform();
    }

    @Step("Wait until skirts catalog link will appear")
    public void waitForSkirtsLink(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(skirtsLinkElement));
    }

    @Step("Get text content of skirts catalog link")
    public String getTextContentOfSkirtsLink() {
        return Objects.requireNonNull(skirtsLinkElement.getDomProperty("textContent")).trim();
    }

    @Step("Get the link of skirts catalog")
    public String getTheSkirtsLink() {
        return skirtsLinkElement.getDomProperty("href");
    }

    @Step("Check if search button is displayed")
    public Boolean isSearchButtonSvgDisplayed() {
        return searchButtonElement.findElement(By.cssSelector("svg")).isDisplayed();
    }

    @Step("Click search button")
    public void clickSearchButton() {
        searchButtonElement.click();
    }

    @Step("Wait until search input will appear")
    public void waitForSearchInput(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(searchInputElement));
    }

    @Step("Check if search input is displayed")
    public Boolean isSearchInputDisplayed() {
        return searchInputElement.isDisplayed();
    }

    @Step("Check if account icon is displayed")
    public Boolean isAccountIconDisplayed() {
        return accountIconElement.isDisplayed();
    }

    @Step("Click account button")
    public void clickAccountButton() {
        accountIconElement.click();
    }

    @Step("Wait until sign in button will appear")
    public void waitForSignInButton(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(signInButtonElement));
    }

    @Step("Check if sign in button is displayed")
    public Boolean isSignInButtonDisplayed() {
        return signInButtonElement.isDisplayed();
    }

    @Step("Wait until create account button will appear")
    public void waitForCreateAccountButton(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(createAccountButtonElement));
    }

    @Step("Check if create account button is displayed")
    public Boolean isCreateAccountButtonDisplayed() {
        return createAccountButtonElement.isDisplayed();
    }

    @Step("Get the link of favorites button")
    public String getTheFavoritesLink() {
        return favoritesLinkElement.getDomProperty("href");
    }

    @Step("Check if the favorites button svg is displayed")
    public Boolean isTheFavoritesSvgDisplayed() {
        return favoritesSvgElement.isDisplayed();
    }

    @Step("Get the link of cart button")
    public String getTheCartLink() {
        return cartLinkElement.getDomProperty("href");
    }

    @Step("Check if the cart button svg is displayed")
    public Boolean isTheCartSvgDisplayed() {
        return cartButtonSvgElement.isDisplayed();
    }

    @Step("Check if the cart number indication is displayed")
    public Boolean isTheCartIndicationDisplayed() {
        return cartIndicatorElement.isDisplayed();
    }

    @Step("Get the number of cart indication")
    public int getTheNumberOfCartIndication() {
        return Integer.parseInt(cartIndicatorElement.getText());
    }

    // -= METHODS =-
}
