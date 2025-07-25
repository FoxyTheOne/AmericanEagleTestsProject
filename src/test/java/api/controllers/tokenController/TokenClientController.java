package api.controllers.tokenController;

import config.ITestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;

/**
 * Отдельный контроллер для токенов.
 * Передаем авторизационный хедер из свойств, если нужно можно сделать секретными данными, для Guest - общедоступная информация.
 */
public class TokenClientController {
    static ITestPropertiesConfig configProperties = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());
    private static final String TOKEN_ENDPOINT = "/ugp-api/auth/oauth/v4/token";

    public static RequestSpecification guestAuthSpec() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept(JSON)
                .contentType(URLENC)
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authorization", configProperties.getGuestHeaderAuth())
                .filter(new AllureRestAssured());
    }

    // В связи с защитой Akamai метод НЕ работает и не используется в данном проекте
    public static RequestSpecification authAuthSpec() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept("application/vnd.oracle.resource+json")
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authority", "www.ae.com")
                .header("authorization", configProperties.getAuthHeaderAuth())

                .header("origin", "https://www.ae.com")
                .header("referer", "https://www.ae.com/us/en")
//                .header("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
//                .header("sec-ch-ua-mobile", "?0")
//                .header("sec-ch-ua-platform", "\"Windows\"")
//                .header("sec-fetch-dest", "empty")
//                .header("sec-fetch-mode", "cors")
//                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .cookie("brand", "aeo") // Куки нужны, но другие

                .filter(new AllureRestAssured());
    }

    @Step("Get guest token")
    public static String getGuestToken() {
        Response response = guestAuthSpec()
                .when()
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.jsonPath().getString("access_token");
    }

    // В связи с защитой Akamai метод НЕ работает и не используется в данном проекте
    @Step("Get auth token")
    public static String getAuthToken() {
        String email = configProperties.getAuthEmail();
        String password = configProperties.getAuthPassword();

        Response response = authAuthSpec()
                .when()
                .formParam("grant_type", "password")
                .formParam("username", email)
                .formParam("password", password)
                .post(TOKEN_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        return response.jsonPath().getString("access_token");
    }
}


