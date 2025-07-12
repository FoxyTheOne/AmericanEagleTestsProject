package ui.tests;

import config.ITestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import ui.pageObjects.HomePage;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * При запуске тестов в этом классе, запустятся тесты из класса CartGuestTests.
 * Перед и после каждого теста будут запущены методы loginUser() и logoutUser()
 */
@Tags({@Tag(UI_TAG), @Tag(EXTENDED_TAG), @Tag(DEFECT_TAG)}) // Из-за защиты от ботов, сайт не позволяет авторизоваться через тест
// class CartRegisteredTests extends CartGuestTests {
// TODO Т.к. авторизация не работает, убрала наследование от CartGuestTests. Вернуть, если методы будут работать
class CartRegisteredTests extends BaseTestSettings {
    static ITestPropertiesConfig configProperties = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());
    private final String USER_EMAIL = configProperties.getAuthEmail();
    private final String USER_PASSWORD = configProperties.getAuthPassword();
    HomePage homePage; // TODO убрать при возврате наследования от CartGuestTests

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        homePage = new HomePage(driver); // TODO убрать при возврате наследования от CartGuestTests
        loginUser();
    }

    @AfterEach
    @Override
    public void tearDown() {
        logoutUser();
        super.tearDown();
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Log in test")
    void loginUserTest() {
        // Проверка входа в аккаунт
        homePage.header().clickAccountButton();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        String sidetrayTitle = homePage.header().loginModalComponent().getSidetrayTitle();

        assertNotEquals("Account", sidetrayTitle, "You're not logged in");
    }

    @DisplayName("Log in")
    private void loginUser() {
        homePage.header().clickAccountButton();
        homePage.header().clickSignInButton();

        char[] userPasswordCharArray = USER_PASSWORD.toCharArray();
        // TODO Не входит в аккаунт:
        // We've encountered an unexpected error on our end. Please try again later.
        homePage.header().loginModalComponent().login(USER_EMAIL, userPasswordCharArray);
    }

    @DisplayName("Log out")
    private void logoutUser() {
        homePage.header().clickAccountButton();

        // TODO Не может выйти, потому что не зашёл
        homePage.header().loginModalComponent().signOut();

        // TODO проверка выхода из аккаунта
    }
}
