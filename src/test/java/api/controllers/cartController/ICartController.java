package api.controllers.cartController;

import api.models.ApiHttpResponse;
import api.models.cartModels.CartResponse;

public interface ICartController {
    // CREATE, ADD - post
    ApiHttpResponse addItemsToCart(String skuId, int quantity);

    // GET - get
    CartResponse getBag();

    // FULL UPDATE - put

    // PART UPDATE - patch
    ApiHttpResponse editItemsInCart(String skuId, int quantity, String itemId);

    // DELETE - delete
}
