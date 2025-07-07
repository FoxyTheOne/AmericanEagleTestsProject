package ui.tests;

import config.ITestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;

import static constants.CommonConstants.*;

/**
 * При запуске тестов в этом классе, запустятся тесты из класса CartGuestTests.
 * Перед и после каждого теста будут запущены методы loginUser() и logoutUser()
 */
@Tags({@Tag(UI_TAG), @Tag(EXTENDED_TAG)})
class CartRegisteredTests extends CartGuestTests {
    static ITestPropertiesConfig configProperties = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());
    private final String USER_EMAIL = configProperties.getAuthEmail();
    private final String USER_PASSWORD = configProperties.getAuthPassword();

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
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Log in")
    void loginUser() {
        homePage.header().clickAccountButton();
        homePage.closeShadowWindow();

        homePage.header().waitForSignInButton(wait5sec);
        homePage.header().clickSignInButton();
        homePage.closeShadowWindow();


        char[] userPasswordCharArray = USER_PASSWORD.toCharArray();
        // TODO Иногда не входит в аккаунт:
        // We've encountered an unexpected error on our end. Please try again later.
        homePage.header().loginModalComponent().login(wait5sec, actions, USER_EMAIL, userPasswordCharArray);
        homePage.closeShadowWindow();

        // TODO проверка входа в аккаунт
//        homePage.header().clickAccountButton();
//        homePage.closeShadowWindow();
//        System.out.println(homePage.header().loginModalComponent().getSidetrayTitle());
    }

    @Test
    @Tag(P1_IMPORTANT_TAG)
    @DisplayName("Log out")
    void logoutUser() {
        homePage.header().clickAccountButton();
        homePage.closeShadowWindow();

        // TODO В этом месте даже если зашел в аккаунт, всё равно показывает, что не вошел (кнопки sign out нет, только sign in)
        homePage.header().loginModalComponent().signOut(wait5sec);
        homePage.closeShadowWindow();

        // TODO проверка выхода из аккаунта
    }
}
