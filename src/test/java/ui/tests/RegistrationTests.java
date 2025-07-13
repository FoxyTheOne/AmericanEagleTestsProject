package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.RegistrationPage;
import utils.FileUtils;
import utils.TestDataGeneratorUtils;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        // Генерация тестовых данных
        String email = TestDataGeneratorUtils.generateEmail();
        String firstName = TestDataGeneratorUtils.generateFirstName();
        String lastName = TestDataGeneratorUtils.generateLastName();
        String password = TestDataGeneratorUtils.generatePassword();
        String zipCode = "64648";

        // Из-за защиты Akamai сайт не позволяет зарегистрироваться через тест
        registrationPage.registerUser(email, firstName, lastName, password, zipCode);
        FileUtils.writeHtmlInTxtFile(email + "/" + firstName + "/" + lastName + "/" + password + "/" + zipCode, "src/test/resources/registration.txt");

        CartPage cartPage = registrationPage.header().openCartPage();

        assertEquals(0, cartPage.getCartItemCount(), "New account cart should be empty");
    }
}
