package api.extensions;

import api.controllers.tokenController.TokenManager;
import api.models.cartModels.UserRole;
import config.ITestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit-расширение - GuestTokenExtension. В beforeAll() устанавливает роль (TokenManager.setCurrentRole(...)) и инициализируется токен (TokenManager.getToken())
 */
public class GuestTokenExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        TokenManager.setCurrentRole(UserRole.GUEST);
        TokenManager.getToken();
    }
}