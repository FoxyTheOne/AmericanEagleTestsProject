package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.pageObjects.CartPage;
import ui.pageObjects.RegistrationPage;
import ui.pageObjects.WomenSkirtsSkortsPage;

import java.util.Objects;

public class HeaderComponent extends BaseComponent {
    LoginModalComponent loginModalComponent;

    public static final String AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED = "American Eagle Outfitters";
    public static final String AE_LOGO_IN_BRAND_SELECTOR_TEXT = "Go to aeo homepage";
    public static final String SKIRTS_LINK_TEXT_CONTENT = "Skirts & Skorts";

    public HeaderComponent(WebDriver driver) {
        super(driver);
        loginModalComponent = new LoginModalComponent(driver);
    }

    // -= LOCATORS =-
    @FindBy(css = "a[data-testid='aeo-logo']")
    private WebElement AeLogoInBrandSelector;

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
    private WebElement registerButtonElement;

    @FindBy(css = "a[id$='ember3']")
    private WebElement favoritesLinkElement;

    //    @FindBy(xpath = "//svg[@data-testid='icon-favorites']") // no such element: Unable to locate element: {"method":"xpath","selector":"//svg[@data-testid='icon-favorites']"}
    @FindBy(css = "svg[data-testid='icon-favorites']")
    private WebElement favoritesSvgElement;

    @FindBy(css = "a[id$='ember4']")
    private WebElement cartLinkElement;

    //    @FindBy(xpath = "//svg[@data-testid='icon-bag']") // no such element: Unable to locate element: {"method":"xpath","selector":"//svg[@data-testid='icon-bag']"}
    @FindBy(css = "svg[data-testid='icon-bag']")
    private WebElement cartButtonSvgElement;

    @FindBy(xpath = "//a[@id='ember4']/span/span")
    private WebElement cartIndicatorElement;

    @FindBy(css = "a.qa-tnav-bag-icon")
    private WebElement cartIcon;

    @FindBy(css = "div[class='bloomreach-weblayer']")
    private WebElement contentShadow;

    // -= ACTIONS =-
    public LoginModalComponent loginModalComponent() {
        closePopUpWindowIfExists();
        return loginModalComponent;
    }

    @Step("Get main logo text")
    public String getAeLogoInBrandSelectorText() {
        closePopUpWindowIfExists();
        return AeLogoInBrandSelector.getText();
    }

    @Step("Get main logo link")
    public String getAeLogoInBrandSelectorHref() {
        closePopUpWindowIfExists();
        return AeLogoInBrandSelector.getDomProperty("href");
    }

    @Step("Get main logo title")
    public String getAeLogoInBrandSelectorTitle() {
        closePopUpWindowIfExists();
        return AeLogoInBrandSelector.getDomProperty("title");
    }

    @Step("Check if main logo svg is displayed")
    public Boolean isAeLogoInBrandSelectorSvgDisplayed() {
        closePopUpWindowIfExists();
        // SVG логотип внутри элемента:
        WebElement svg = AeLogoInBrandSelector.findElement(By.cssSelector("svg.aeo-icon-brand-max-aeo"));
        return svg.isDisplayed();
    }

    @Step("Open women menu and wait until skirts catalog link will appear")
    public void openWomenMenu() {
        closePopUpWindowIfExists();
        actions
                .scrollToElement(womenMenuElement)
                .perform();
        actions.moveToElement(womenMenuElement).perform();
        wait5sec.until(ExpectedConditions.visibilityOf(skirtsLinkElement));
    }

    @Step("Get text content of skirts catalog link")
    public String getTextContentOfSkirtsLink() {
        closePopUpWindowIfExists();
        return Objects.requireNonNull(skirtsLinkElement.getDomProperty("textContent")).trim();
    }

    @Step("Get the link of skirts catalog")
    public String getTheSkirtsLink() {
        closePopUpWindowIfExists();
        return skirtsLinkElement.getDomProperty("href");
    }

    @Step("Check if search button is displayed")
    public Boolean isSearchButtonSvgDisplayed() {
        closePopUpWindowIfExists();
        return searchButtonElement.findElement(By.cssSelector("svg")).isDisplayed();
    }

    @Step("Click search button")
    public void clickSearchButton() {
        closePopUpWindowIfExists();
        searchButtonElement.click();
    }

    @Step("Wait until search input will appear and check if search input is displayed")
    public Boolean isSearchInputDisplayed() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(searchInputElement));
        return searchInputElement.isDisplayed();
    }

    @Step("Check if account icon is displayed")
    public Boolean isAccountIconDisplayed() {
        closePopUpWindowIfExists();
        return accountIconElement.isDisplayed();
    }

    @Step("Click account button")
    public void clickAccountButton() {
        closePopUpWindowIfExists();
        accountIconElement.click();
    }

    @Step("Check if sign in button is displayed")
    public Boolean isSignInButtonDisplayed() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(signInButtonElement));
        return signInButtonElement.isDisplayed();
    }

    @Step("Wait until sign in button will appear and click sign in button")
    public void clickSignInButton() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(signInButtonElement));
        signInButtonElement.click();
    }

    @Step("Wait until create account button will appear and check if create account button is displayed")
    public Boolean isCreateAccountButtonDisplayed() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(registerButtonElement));
        return registerButtonElement.isDisplayed();
    }

    @Step("Get the link of favorites button")
    public String getTheFavoritesLink() {
        closePopUpWindowIfExists();
        return favoritesLinkElement.getDomProperty("href");
    }

    @Step("Check if the favorites button svg is displayed")
    public Boolean isTheFavoritesSvgDisplayed() {
        closePopUpWindowIfExists();
        return favoritesSvgElement.isDisplayed();
    }

    @Step("Get the link of cart button")
    public String getTheCartLink() {
        closePopUpWindowIfExists();
        return cartLinkElement.getDomProperty("href");
    }

    @Step("Check if the cart button svg is displayed")
    public Boolean isTheCartSvgDisplayed() {
        closePopUpWindowIfExists();
        return cartButtonSvgElement.isDisplayed();
    }

    @Step("Check if the cart number indication is displayed")
    public Boolean isTheCartIndicationDisplayed() {
        closePopUpWindowIfExists();
        return cartIndicatorElement.isDisplayed();
    }

    @Step("Get the number of cart indication")
    public int getTheNumberOfCartIndication() {
        closePopUpWindowIfExists();
        return Integer.parseInt(cartIndicatorElement.getText());
    }

    // -= METHODS =-
    @Step("Open women skirts & skorts page")
    public WomenSkirtsSkortsPage openWomenSkirtsSkortsPage() {
        closePopUpWindowIfExists();
        skirtsLinkElement.click();
        WomenSkirtsSkortsPage womenSkirtsSkortsPage = new WomenSkirtsSkortsPage(driver);
        womenSkirtsSkortsPage.waitForProductTiles();
        return womenSkirtsSkortsPage;
    }

    @Step("Wait until create account button will appear and open registration page")
    public RegistrationPage openRegistrationPage() {
        closePopUpWindowIfExists();
        wait5sec.until(ExpectedConditions.visibilityOf(registerButtonElement));
        registerButtonElement.click();
        return new RegistrationPage(driver);
    }

    @Step("Open cart page")
    public CartPage openCartPage() {
        closePopUpWindowIfExists();
        cartIcon.click();
        return new CartPage(driver);
    }
}
