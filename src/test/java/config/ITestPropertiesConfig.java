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

    @Config.Key("isRemote")
    Boolean isRemote();

    @Config.Key("LocalOSWindows7")
    Boolean LocalOSWindows7();
}
