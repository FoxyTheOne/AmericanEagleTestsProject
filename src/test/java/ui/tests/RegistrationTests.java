package ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import ui.models.AccountDetails;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.RegistrationPage;
import utils.FileUtils;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = UI_TAG)
@Feature(value = "Auth tests")
@Tags({@Tag(UI_TAG), @Tag(EXTENDED_TAG)})
class RegistrationTests extends BaseTestSettings {
    HomePage homePage;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        homePage = new HomePage(driver);
    }

    @Test
    @Tags({@Tag(P1_IMPORTANT_TAG), @Tag(DEFECT_TAG)})
    @DisplayName("Register a new account")
    void testNewAccountCartIsEmpty() {
        homePage.header().clickAccountButton();
        RegistrationPage registrationPage = homePage.header().openRegistrationPage();
        AccountDetails accountDetails = registrationPage.generateAccountDetails();

        // Из-за защиты Akamai сайт не позволяет зарегистрироваться через тест
        registrationPage.registerUser(accountDetails);
        FileUtils.writeHtmlInTxtFile(accountDetails.getEmail() + "/" + accountDetails.getFirstName() + "/" + accountDetails.getLastName() + "/" + accountDetails.getPassword() + "/" + accountDetails.getZipCode(), "src/test/resources/registration.txt");

        CartPage cartPage = registrationPage.header().openCartPage();

        assertEquals(0, cartPage.getCartItemCount(), "New account cart should be empty");
    }
}
