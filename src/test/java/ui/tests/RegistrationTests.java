package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.RegistrationPage;
import utils.FileUtils;
import utils.TestDataGeneratorUtils;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({@Tag(UI_TAG), @Tag(EXTENDED_TAG)})
class RegistrationTests extends BaseTestSettings {
    HomePage homePage;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        homePage = new HomePage(driver);
    }

    // TODO нажатие на checkbox не работает.
    // TODO Исправить генерацию фамилии, пароля, zip
    @Test
    @Tags({@Tag(P1_IMPORTANT_TAG), @Tag(DEFECT_TAG)})
    // Из-за защиты от ботов, сайт не позволяет зарегистрироваться через тест
    @DisplayName("Register a new account")
    void testNewAccountCartIsEmpty() {
        homePage.header().clickAccountButton();

        Boolean isCreateAccountButtonDisplayed = homePage.header().isCreateAccountButtonDisplayed();

        assertTrue(isCreateAccountButtonDisplayed);

        RegistrationPage registrationPage = homePage.header().openRegistrationPage();

        // Генерация тестовых данных
        String email = TestDataGeneratorUtils.generateEmail();
        String firstName = TestDataGeneratorUtils.generateFirstName();
        String lastName = TestDataGeneratorUtils.generateLastName();
        String password = TestDataGeneratorUtils.generatePassword();
        String zipCode = "123456";

        FileUtils.writeHtmlInTxtFile(email + "/" + firstName + "/" + lastName + "/" + password + "/" + zipCode, "src/test/resources/registration.txt"); // TODO после починки теста сохранять в файл только зарегистрированные аккаунты
        // Регистрация
        registrationPage.registerUser(email, firstName, lastName, password, zipCode);

        CartPage cartPage = registrationPage.header().openCartPage();

        assertEquals(0, cartPage.getCartItemCount(), "New account cart should be empty");
    }
}
