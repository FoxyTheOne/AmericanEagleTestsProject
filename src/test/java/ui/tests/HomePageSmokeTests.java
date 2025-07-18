package ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import ui.pageObjects.HomePage;
import ui.pageObjects.components.HeaderComponent;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static ui.pageObjects.BasePage.*;

@Epic(value = UI_TAG)
@Feature(value = "Smoke home page tests")
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
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Smoke test: open homepage and check url and titles")
    void openHomePageTest() {
        String actualGeneralWebTitle = homePage.getGeneralWebTitle();
        String actualCurrentUrl = homePage.getCurrentUrl();
        String actualAeLogoInBrandSelectorText = homePage.header().getAeLogoInBrandSelectorText();
        String actualAeLogoInBrandSelectorHref = homePage.header().getAeLogoInBrandSelectorHref();
        String actualAeLogoInBrandSelectorTitle = homePage.header().getAeLogoInBrandSelectorTitle();

        assertAll(
                () -> assertTrue(actualGeneralWebTitle.contains(GENERAL_WEB_TITLE_CONTAINS_EXPECTED)),
                () -> assertEquals(BASE_URL + HOME_URL, actualCurrentUrl),
                () -> assertEquals(HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TEXT, actualAeLogoInBrandSelectorText),
                () -> assertTrue(actualAeLogoInBrandSelectorHref.contains(HOME_URL), "actualAeLogoInBrandSelectorHref = " + actualAeLogoInBrandSelectorHref + " and doesn't contain " + HOME_URL),
                () -> assertTrue(actualAeLogoInBrandSelectorTitle.contains(HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED), "actualAeLogoInBrandSelectorTitle = " + actualAeLogoInBrandSelectorTitle + " and doesn't contain " + HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED)
        );
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: check if svg logo is displayed on homepage")
    void isSvgLogoDisplayed() {
        Boolean isAeLogoInBrandSelectorSvgDisplayed = homePage.header().isAeLogoInBrandSelectorSvgDisplayed();

        assertTrue(isAeLogoInBrandSelectorSvgDisplayed);
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Smoke test: open menu on homepage")
    void openWomenMenu() {
        // Один локатор - один тест. Проверим на всякий случай отдельно
        homePage.header().openWomenMenu();
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Smoke test: open menu and find skirts catalog link on homepage")
    void openMenuAndFindSkirtsLink() {
        homePage.header().openWomenMenu();

        String actualSkirtsLinkTextContent = homePage.header().getTextContentOfSkirtsLink();
        String actualSkirtsLinkHref = homePage.header().getTheSkirtsLink();

        assertAll(
                () -> assertEquals(HeaderComponent.SKIRTS_LINK_TEXT_CONTENT, actualSkirtsLinkTextContent),
                () -> assertTrue(actualSkirtsLinkHref.contains(HOME_URL + WOMEN_SKIRTS_URL))
        );
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: check search button on homepage")
    void checkSearchButton() {
        Boolean isSearchButtonSvgDisplayed = homePage.header().isSearchButtonSvgDisplayed();

        assertTrue(isSearchButtonSvgDisplayed);
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: click search button and check search input is displayed on homepage")
    void checkSearchButtonClick() {
        homePage.header().clickSearchButton();
        Boolean isSearchInputDisplayed = homePage.header().isSearchInputDisplayed();

        assertTrue(isSearchInputDisplayed);
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Smoke test: check account button on homepage")
    void checkAccountButton() {
        Boolean isAccountIconDisplayed = homePage.header().isAccountIconDisplayed();

        assertTrue(isAccountIconDisplayed);
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Smoke test: check sign in button on homepage")
    void checkSignInButton() {
        homePage.header().clickAccountButton();

        Boolean isSignInButtonDisplayed = homePage.header().isSignInButtonDisplayed();

        assertTrue(isSignInButtonDisplayed);
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Smoke test: check create account button on homepage")
    void checkCreateAccountButton() {
        homePage.header().clickAccountButton();

        assertTrue(homePage.header().isRegisterButtonDisplayed());
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Smoke test: check favorites button link on homepage")
    void checkFavoritesLink() {
        String actualFavoritesHref = homePage.header().getTheFavoritesLink();

        assertEquals(BASE_URL + HOME_URL + FAVORITES_URL, actualFavoritesHref);
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: check favorites button svg on homepage")
    void checkFavoritesSvg() {
        Boolean isTheFavoritesSvgDisplayed = homePage.header().isTheFavoritesSvgDisplayed();

        assertTrue(isTheFavoritesSvgDisplayed);
    }


    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Smoke test: check cart button link on homepage")
    void checkCartLink() {
        String actualCartHref = homePage.header().getTheCartLink();

        assertEquals(BASE_URL + HOME_URL + CART_URL, actualCartHref);
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: check cart button svg on homepage")
    void checkCartSvg() {
        Boolean isTheCartSvgDisplayed = homePage.header().isTheCartSvgDisplayed();

        assertTrue(isTheCartSvgDisplayed);
    }

    @Test
    @Tag(P2_NICE_TO_HAVE_TAG)
    @DisplayName("Smoke test: check cart button number indicator on homepage")
    void checkCartIndicator() {
        Boolean isTheCartIndicationDisplayed = homePage.header().isTheCartIndicationDisplayed();

        assertTrue(isTheCartIndicationDisplayed);

        int actualNumberOfCartIndication = homePage.header().getTheNumberOfCartIndication();

        assertTrue(actualNumberOfCartIndication >= 0);
    }
}
