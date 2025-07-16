package ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * При запуске тестов в этом классе, запустятся тесты из класса CartGuestTests.
 * Перед и после каждого теста будут запущены методы loginUser() и logoutUser()
 */
@Epic(value = UI_TAG)
@Feature(value = "Auth tests")
@Tags({@Tag(UI_TAG), @Tag(DEFECT_TAG), @Tag(NO_SCREENSHOT)}) // Из-за защиты Akamai сайт не позволяет авторизоваться через тест
class CartRegisteredTests extends CartGuestTests {
    private final String USER_EMAIL = config.getAuthEmail();
    private final String USER_PASSWORD = config.getAuthPassword();

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        loginUser();
    }

    @AfterEach
    @Override
    public void tearDown() {
        logoutUser();
        super.tearDown();
    }

    @Test
    @Tags({@Tag(SMOKE_TAG), @Tag(P1_IMPORTANT_TAG)})
    @DisplayName("Log in test")
    void loginUserTest() {
        // Проверка входа в аккаунт
        homePage.header().clickAccountButton();
        String sidetrayTitle = homePage.header().loginModalComponent().getSidetrayTitle();

        assertNotEquals("Account", sidetrayTitle, "You're not logged in");
    }

    @DisplayName("Log in")
    private void loginUser() {
        homePage.header().clickAccountButton();
        homePage.header().clickSignInButton();

        char[] userPasswordCharArray = USER_PASSWORD.toCharArray();

        // Из-за защиты Akamai сайт не позволяет авторизоваться через тест
        homePage.header().loginModalComponent().login(USER_EMAIL, userPasswordCharArray);
    }

    @DisplayName("Log out")
    private void logoutUser() {
        homePage.header().clickAccountButton();

        // Из-за защиты Akamai сайт не позволяет авторизоваться через тест, поэтому выйти он тоже не может
        homePage.header().loginModalComponent().signOut();
    }
}
