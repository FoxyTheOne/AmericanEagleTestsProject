package ui.pageObjects.components;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pageObjects.CartPage;
import ui.pageObjects.RegistrationPage;
import ui.pageObjects.WomenSkirtsSkortsPage;

import java.util.Objects;

public class HeaderComponent {
    WebDriver driver;
    LoginModalComponent loginModalComponent;

    public static final String AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED = "American Eagle Outfitters";
    public static final String AE_LOGO_IN_BRAND_SELECTOR_TEXT = "Go to aeo homepage";
    public static final String SKIRTS_LINK_TEXT_CONTENT = "Skirts & Skorts";

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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

    @FindBy(css = "a.qa-tnav-bag-icon")
    private WebElement cartIcon;

    @FindBy(css = "div[class='bloomreach-weblayer']")
    private WebElement contentShadow;

    // -= ACTIONS =-
    public LoginModalComponent loginModalComponent() {
        return loginModalComponent;
    }

    @Step("Get main logo text")
    public String getAeLogoInBrandSelectorText() {
        return AeLogoInBrandSelector.getText();
    }

    @Step("Get main logo link")
    public String getAeLogoInBrandSelectorHref() {
        return AeLogoInBrandSelector.getDomProperty("href");
    }

    @Step("Get main logo title")
    public String getAeLogoInBrandSelectorTitle() {
        return AeLogoInBrandSelector.getDomProperty("title");
    }

    @Step("Check if main logo svg is displayed")
    public Boolean isAeLogoInBrandSelectorSvgDisplayed() {
        // SVG логотип внутри элемента:
        WebElement svg = AeLogoInBrandSelector.findElement(By.cssSelector("svg.aeo-icon-brand-max-aeo"));
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

    @Step("Click sign in button")
    public void clickSignInButton() {
        signInButtonElement.click();
    }

    @Step("Wait until create account button will appear")
    public void waitForCreateAccountButton(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOf(registerButtonElement));
    }

    @Step("Check if create account button is displayed")
    public Boolean isCreateAccountButtonDisplayed() {
        return registerButtonElement.isDisplayed();
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

    @Step("Find and close shadow window")
    private void closeShadowWindow() {
        try {
            SearchContext shadowRoot = contentShadow.getShadowRoot();
            WebElement closeButton = shadowRoot.findElement(By.cssSelector("button[class='close']"));
            closeButton.click();
            System.out.println("Shadow DOM closed");
        } catch (org.openqa.selenium.NoSuchElementException | StaleElementReferenceException ignored) {
            // Игнорируем, если не было всплывающего окна
        }
    }

    // -= METHODS =-
    @Step("Open women skirts & skorts page")
    public WomenSkirtsSkortsPage openWomenSkirtsSkortsPage() {
        try {
            skirtsLinkElement.click();
        } catch (TimeoutException e) {
            closeShadowWindow();
            skirtsLinkElement.click();
        }
        return new WomenSkirtsSkortsPage(driver);
    }

    @Step("Open registration page")
    public RegistrationPage openRegistrationPage() {
        try {
            registerButtonElement.click();
        } catch (TimeoutException e) {
            closeShadowWindow();
            registerButtonElement.click();
        }
        return new RegistrationPage(driver);
    }

    @Step("Open cart page")
    public CartPage openCartPage() {
        try {
            cartIcon.click();
        } catch (TimeoutException e) {
            closeShadowWindow();
            cartIcon.click();
        }
        return new CartPage(driver);
    }
}
