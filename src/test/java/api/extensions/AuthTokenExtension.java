package api.extensions;

import api.controllers.tokenController.TokenManager;
import api.models.cartModels.UserRole;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit-расширение - AuthTokenExtension. В beforeAll() устанавливает роль (TokenManager.setCurrentRole(...)) и инициализируется токен (TokenManager.getToken())
 */
public class AuthTokenExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        TokenManager.setCurrentRole(UserRole.AUTH);
        TokenManager.getToken();
    }
}
