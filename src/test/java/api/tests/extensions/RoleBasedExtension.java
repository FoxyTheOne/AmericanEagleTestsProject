package api.tests.extensions;

import api.controllers.tokenController.TokenManager;
import api.models.cartModels.UserRole;
import config.ITestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit-расширение - RoleBasedExtension. В beforeAll() устанавливает роль (TokenManager.setCurrentRole(...)) и инициализируется токен (TokenManager.getToken())
 */
public class RoleBasedExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        ITestPropertiesConfig config = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());

        UserRole role = UserRole.valueOf(config.getTestMode().toUpperCase());
        TokenManager.setCurrentRole(role);
        TokenManager.getToken();
    }
}