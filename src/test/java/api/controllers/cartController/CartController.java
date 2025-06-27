package api.controllers.cartController;

import api.controllers.tokenController.TokenManager;
import api.models.cartModels.AddItemRequest;
import api.models.cartModels.CartResponse;
import config.ITestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * BagController не знает о ролях.
 * Просто вызывает TokenManager.getToken() — и получает нужный токен Роль уже была установлена расширением → нет дублирования
 */
public class CartController implements ICartController {
//    RequestSpecification requestSpecification;
    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";

    ITestPropertiesConfig configProperties = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());

    RequestSpecification requestSpecification = given();

    // При инициализации контроллера устанавливаем нужны нам token
    public CartController() {
        RestAssured.defaultParser = Parser.JSON;
        requestSpecification.accept(JSON);
        requestSpecification.contentType(JSON);
        requestSpecification.baseUri(configProperties.getApiBaseUrl());
        requestSpecification.header("aesite", "AEO_US");
        requestSpecification.header("x-access-token", TokenManager.getToken()); // Получаем нужный токен, guest или auth
        requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add item to bag")
    public Response addItemToCart(String skuId, int quantity) {
        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        AddItemRequest request = new AddItemRequest(List.of(item));

        return given(requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Get bag")
    public CartResponse getBag() {
        return given(requestSpecification)
                .when()
                .get(BAG_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(CartResponse.class);
    }
}
