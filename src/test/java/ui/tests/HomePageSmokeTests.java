package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.components.HeaderComponent;
import ui.pageObjects.HomePage;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static ui.pageObjects.BasePage.*;

@Tags({@Tag(UI_TAG), @Tag(SMOKE_TAG)})
class HomePageSmokeTests extends BaseTestSettings {
    HomePage homePage;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        homePage = new HomePage(driver);
    }

    @Test
    @Tag(IMPORTANT_TAG)
    @DisplayName("Open homepage and check url and titles")
    void openHomePageTest() {
        String actualGeneralWebTitle = homePage.getGeneralWebTitle();
        String actualCurrentUrl = homePage.getCurrentUrl();
        String actualMainLogoText = homePage.header().getMainLogoText();
        String actualMainLogoHref = homePage.header().getMainLogoHref();
        String actualMainLogoTitle = homePage.header().getMainLogoTitle();

        assertAll(
                () -> assertEquals(GENERAL_WEB_TITLE_EXPECTED, actualGeneralWebTitle),
                () -> assertEquals(BASE_URL + HOME_URL, actualCurrentUrl),
                () -> assertEquals(HeaderComponent.MAIN_LOGO_TEXT, actualMainLogoText),
                () -> assertTrue(actualMainLogoHref.contains(HOME_URL)),
                () -> assertTrue(actualMainLogoTitle.contains(HeaderComponent.MAIN_LOGO_TITLE_CONTAINS_EXPECTED))
        );
    }

    @Test
    @Tag(NICE_TO_HAVE_TAG)
    @DisplayName("Check if svg logo is displayed on homepage")
    void isSvgLogoDisplayed() {
        Boolean isSvgLogoDisplayed = homePage.header().isMainLogoSvgDisplayed();

        assertTrue(isSvgLogoDisplayed);
    }

    @Test
    @Tag(CRUCIAL_TAG)
    @DisplayName("Open menu on homepage")
    void openWomenMenu() {
        // Один локатор - один тест. Проверим на всякий случай отдельно
        homePage.header().openWomenMenu(actions);
    }

    @Test
    @Tag(CRUCIAL_TAG)
    @DisplayName("Open menu and find skirts catalog link on homepage")
    void openMenuAndFindSkirtsLink() {
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        String actualSkirtsLinkTextContent = homePage.header().getTextContentOfSkirtsLink();
        String actualSkirtsLinkHref = homePage.header().getTheSkirtsLink();

        assertAll(
                () -> assertEquals(HeaderComponent.SKIRTS_LINK_TEXT_CONTENT, actualSkirtsLinkTextContent),
                () -> assertTrue(actualSkirtsLinkHref.contains(HOME_URL + WOMEN_SKIRTS_URL))
        );
    }

    @Test
    @Tag(NICE_TO_HAVE_TAG)
    @DisplayName("Check search button on homepage")
    void checkSearchButton() {
        Boolean isSearchButtonSvgDisplayed = homePage.header().isSearchButtonSvgDisplayed();

        assertTrue(isSearchButtonSvgDisplayed);
    }

    @Test
    @Tag(NICE_TO_HAVE_TAG)
    @DisplayName("Click search button and check search input is displayed on homepage")
    void checkSearchButtonClick() {
        homePage.header().clickSearchButton();
        homePage.header().waitForSearchInput(wait5sec);
        Boolean isSearchInputDisplayed = homePage.header().isSearchInputDisplayed();

        assertTrue(isSearchInputDisplayed);
    }

    @Test
    @Tag(CRUCIAL_TAG)
    @DisplayName("Check account button on homepage")
    void checkAccountButton() {
        Boolean isAccountIconDisplayed = homePage.header().isAccountIconDisplayed();

        assertTrue(isAccountIconDisplayed);
    }

    @Test
    @Tag(CRUCIAL_TAG)
    @DisplayName("Check sign in button on homepage")
    void checkSignInButton() {
        homePage.header().clickAccountButton();

// TODO тут раньше появлялось какое-то окошко, пока не могу его словить

        homePage.header().waitForSignInButton(wait5sec);
        Boolean isSignInButtonDisplayed = homePage.header().isSignInButtonDisplayed();

        assertTrue(isSignInButtonDisplayed);
    }

    @Test
    @Tag(CRUCIAL_TAG)
    @DisplayName("Check create account button on homepage")
    void checkCreateAccountButton() {
        homePage.header().clickAccountButton();

// TODO тут раньше появлялось какое-то окошко, пока не могу его словить
// Закрываем рекламу, если появилась (пример)
//        try {
//            WebElement closeAd = wait5sec.until(ExpectedConditions.elementToBeClickable(
//                    By.cssSelector("button.close-ad, .modal-close")
//            ));
//            closeAd.click();
//        } catch (TimeoutException e) {
//            // Рекламы нет, продолжаем
//        }
// или
//        try {
//            driver.findElement(By.cssSelector(".ad-close-button")).click();
//        } catch (NoSuchElementException ignored) {}

        homePage.header().waitForCreateAccountButton(wait5sec);
        Boolean isCreateAccountButtonDisplayed = homePage.header().isCreateAccountButtonDisplayed();

        assertTrue(isCreateAccountButtonDisplayed);
    }

    @Test
    @Tag(IMPORTANT_TAG)
    @DisplayName("Check favorites button link on homepage")
    void checkFavoritesLink() {
        String actualFavoritesHref = homePage.header().getTheFavoritesLink();

        assertEquals(BASE_URL + HOME_URL + FAVORITES_URL, actualFavoritesHref);
    }

    @Test
    @Tag(NICE_TO_HAVE_TAG)
    @DisplayName("Check favorites button svg on homepage")
    void checkFavoritesSvg() {
        Boolean isTheFavoritesSvgDisplayed = homePage.header().isTheFavoritesSvgDisplayed();

        assertTrue(isTheFavoritesSvgDisplayed);
    }


    @Test
    @Tag(IMPORTANT_TAG)
    @DisplayName("Check cart button link on homepage")
    void checkCartLink() {
        String actualCartHref = homePage.header().getTheCartLink();

        assertEquals(BASE_URL + HOME_URL + CART_URL, actualCartHref);
    }

    @Test
    @Tag(NICE_TO_HAVE_TAG)
    @DisplayName("Check cart button svg on homepage")
    void checkCartSvg() {
        Boolean isTheCartSvgDisplayed = homePage.header().isTheCartSvgDisplayed();

        assertTrue(isTheCartSvgDisplayed);
    }

    @Test
    @Tag(IMPORTANT_TAG)
    @DisplayName("Check cart button number indicator on homepage")
    void checkCartIndicator() {
        Boolean isTheCartIndicationDisplayed = homePage.header().isTheCartIndicationDisplayed();

        assertTrue(isTheCartIndicationDisplayed);

        int actualNumberOfCartIndication = homePage.header().getTheNumberOfCartIndication();

        assertTrue(actualNumberOfCartIndication >= 0);
    }
}
