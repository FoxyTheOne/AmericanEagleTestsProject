package api.controllers.cartController;

import api.models.cartModels.CartResponse;
import io.restassured.response.Response;

public interface ICartController {
    // CREATE, ADD - post
    Response addItemToCart(String skuId, int quantity);

    // GET - get
    CartResponse getBag();

    // FULL UPDATE - put

    // PART UPDATE - patch

    // DELETE - delete
}
