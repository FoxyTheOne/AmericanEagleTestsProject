package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.HomePage;
import ui.pageObjects.WomenSkirtsSkortsPage;
import ui.pageObjects.components.HeaderComponent;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static ui.pageObjects.BasePage.*;

@Tags({@Tag(UI_TAG), @Tag(SMOKE_TAG)})
class WomenSkirtsSkortsPageSmokeTests extends BaseTestSettings {
    HomePage homePage;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        homePage = new HomePage(driver);
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Open women skirts & skorts page from header on homepage and check url and titles")
    void openWomenSkirtsSkortsPageTest() {
        // Открываем выпадающее меню
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        // Реализуем переход между страницами:
        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();

        // TODO Словить ShadowDOM, чтобы проверить
        womenSkirtsSkortsPage.closeShadowWindow();

        String actualGeneralWebTitle = womenSkirtsSkortsPage.getGeneralWebTitle();
        String actualCurrentUrl = womenSkirtsSkortsPage.getCurrentUrl();
        String actualAeLogoInBrandSelectorText = womenSkirtsSkortsPage.header().getAeLogoInBrandSelectorText();
        String actualAeLogoInBrandSelectorHref = womenSkirtsSkortsPage.header().getAeLogoInBrandSelectorHref();
        String actualAeLogoInBrandSelectorTitle = womenSkirtsSkortsPage.header().getAeLogoInBrandSelectorTitle();

        assertAll(
                () -> assertTrue(actualGeneralWebTitle.contains(GENERAL_WEB_TITLE_CONTAINS_EXPECTED)),
                () -> assertTrue(actualCurrentUrl.contains(WOMEN_SKIRTS_URL),
                        String.format("actualCurrentUrl = %s was expected to contain %s", actualCurrentUrl, WOMEN_SKIRTS_URL)),
                () -> assertEquals(HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TEXT, actualAeLogoInBrandSelectorText,
                        String.format("actualAeLogoInBrandSelectorText = %s but it was expected to be %s", actualAeLogoInBrandSelectorText, HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TEXT)),
                () -> assertTrue(actualAeLogoInBrandSelectorHref.contains(HOME_URL),
                        String.format("actualAeLogoInBrandSelectorHref = %s was expected to contain %s", actualAeLogoInBrandSelectorHref, HOME_URL)),
                () -> assertTrue(actualAeLogoInBrandSelectorTitle.contains(HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED),
                        String.format("actualAeLogoInBrandSelectorTitle = %s was expected to contain %s", actualAeLogoInBrandSelectorTitle, HeaderComponent.AE_LOGO_IN_BRAND_SELECTOR_TITLE_CONTAINS_EXPECTED))
        );
    }
}
