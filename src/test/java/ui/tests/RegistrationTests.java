package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.RegistrationPage;
import utils.TestDataGeneratorUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Register a new account")
    void testNewAccountCartIsEmpty() {
        homePage.header().clickAccountButton();
        homePage.closeShadowWindow();

        homePage.header().waitForCreateAccountButton(wait5sec);
        Boolean isCreateAccountButtonDisplayed = homePage.header().isCreateAccountButtonDisplayed();

        assertTrue(isCreateAccountButtonDisplayed);

        RegistrationPage registrationPage = homePage.header().openRegistrationPage();
        homePage.closeShadowWindow();

        // Генерация тестовых данных
        String email = TestDataGeneratorUtils.generateEmail();
        String firstName = TestDataGeneratorUtils.generateFirstName();
        String lastName = TestDataGeneratorUtils.generateLastName();
        String password = TestDataGeneratorUtils.generatePassword();
        String zipCode = "123456";

        writeHtmlInFile(email, firstName, lastName, password, zipCode); // TODO после починки теста сохранять в файл только зарегистрированные аккаунты
        // Регистрация
        registrationPage.registerUser(wait5sec, actions, email, firstName, lastName, password, zipCode);

        CartPage cartPage = registrationPage.header().openCartPage();

        assertEquals(0, cartPage.getCartItemCount(wait5sec), "New account cart should be empty");
    }

    // TODO Перенести метод в Utils
    private void writeHtmlInFile(String email, String firstName, String lastName, String password, String zipCode) {
        File myFile = new File("src/test/resources/registration.txt");
        try {
            boolean isFileCreated = myFile.createNewFile();

            if (isFileCreated) {
                System.out.println("File was created");
            } else {
                myFile.delete();
                myFile.createNewFile();
                System.out.println("File was deleted and created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(myFile, true)) {
            fw.write(email + " / " + firstName + " / " + lastName + " / " + password + " / " + zipCode);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
