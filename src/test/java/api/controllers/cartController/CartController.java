package api.controllers.cartController;

import api.controllers.tokenController.TokenManager;
import api.models.ApiHttpResponse;
import api.models.cartModels.AddItemRequest;
import api.models.cartModels.CartResponse;
import api.models.cartModels.EditItemRequest;
import config.ITestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
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
    private static final String PRODUCT_SIZES_ENDPOINT = "/ugp-api/catalog/v1/product/sizes";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";

    ITestPropertiesConfig configProperties = ConfigFactory.create(ITestPropertiesConfig.class, System.getProperties());

    RequestSpecification requestSpecification = given();

    // При инициализации контроллера устанавливаем нужный нам token
    public CartController() {
        RestAssured.defaultParser = Parser.JSON;
        requestSpecification.accept(JSON);
        requestSpecification.contentType(JSON);
        requestSpecification.baseUri(configProperties.getApiBaseUrl());
        requestSpecification.header("aesite", "AEO_US");
        requestSpecification.header("Aecountry", "US"); //
        requestSpecification.header("Aelang", "en_US"); //
        requestSpecification.header("x-access-token", TokenManager.getToken()); // Получаем нужный токен, guest или auth
        requestSpecification.filter(new AllureRestAssured());
    }

    // CREATE, ADD - post
    @Step("Add item to bag")
    public ApiHttpResponse addItemsToCart(String skuId, int quantity) {
        // Создаём объект товара
        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        // Создаём объект запроса (body)
        AddItemRequest request = new AddItemRequest(List.of(item));

        requestSpecification.body(request);
        return new ApiHttpResponse(given(requestSpecification)
                .when().post(ITEMS_ENDPOINT)
                .then());
    }

    // GET - get
    @Step("Get bag")
    public CartResponse getBag() {
        return given(requestSpecification)
                .when()
                .get(BAG_ENDPOINT)
                .then()
                .statusCode(200)
//                .log().body()
                .extract()
                .as(CartResponse.class);
    }

    @Step("Open quick shop by id")
    public ApiHttpResponse openQuickShopById(String productId) {
        return new ApiHttpResponse(given(requestSpecification)
                .when().get(PRODUCT_SIZES_ENDPOINT + "?productIds=" + productId)
                .then());
    }

    @Step("Open cart inventory")
    public ApiHttpResponse getCartInventory() {
        return new ApiHttpResponse(given(requestSpecification)
                .when().get(BAG_ENDPOINT + "?couponErrorBehavior=cart&inventoryCheck=true")
                .then());
    }

    // FULL UPDATE - put

    // PART UPDATE - patch
    @Step("Edit item in bag")
    public ApiHttpResponse editItemsInCart(String skuId, int quantity, String itemId) {
        // Создаём объект товара
        EditItemRequest.Item item = new EditItemRequest.Item(skuId, quantity, itemId);
        // Создаём объект запроса (body)
        EditItemRequest request = new EditItemRequest(List.of(item));

        requestSpecification.body(request);
        return new ApiHttpResponse(given(requestSpecification)
                .when().patch(ITEMS_ENDPOINT)
                .then());
    }

    // DELETE - delete
    @Step("Delete item in bag")
    public ApiHttpResponse deleteItemsInCart(String itemId) {
        return new ApiHttpResponse(given(requestSpecification)
                .when().delete(ITEMS_ENDPOINT + "?itemIds=" + itemId)
                .then());
    }
}