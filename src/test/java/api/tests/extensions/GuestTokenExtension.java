package api.tests.extensions;

import api.controllers.tokenController.TokenManager;
import api.models.cartModels.UserRole;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit-расширение - GuestTokenExtension и заготовка для будущего AuthTokenExtension
 * В beforeAll() устанавливает роль (TokenManager.setCurrentRole(...)) и инициализируется токен (TokenManager.getToken())
 */
public class GuestTokenExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        // TODO Здесь мы устанавливаем роль перед тестами. Доделать Auth
        TokenManager.setCurrentRole(UserRole.GUEST);
        TokenManager.getToken();
    }
}
