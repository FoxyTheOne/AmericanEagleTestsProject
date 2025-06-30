package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:default.properties"
})
public interface ITestPropertiesConfig extends Config {
    // Пропишем эти поля в properties, когда будем запускать в CI/CD. При необходимости сейчас можно задать эти поля через -D
    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    // Пропишем эти поля в properties, когда будем запускать в CI/CD. При необходимости сейчас можно задать эти поля через -D
    @Key("remoteUrl")
    String remoteUrl();

    @Config.Key("LocalOSWindows7")
    Boolean LocalOSWindows7();

    @Key("baseUrl")
    String getApiBaseUrl();

    @Key("guest.header.auth")
    String getGuestHeaderAuth();

    @Key("auth.header.auth")
    String getAuthHeaderAuth();

    @Key("auth.email")
    String getAuthEmail();

    @Key("auth.password")
    String getAuthPassword();

    // Метод для определения режима запуска
    @Key("test.mode")
    @DefaultValue("GUEST")
    String getTestMode();
}
