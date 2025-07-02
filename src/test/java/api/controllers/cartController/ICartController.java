package api.controllers.cartController;

import api.models.ApiHttpResponse;
import api.models.cartModels.CartResponse;
import api.models.cartModels.InventoryCheckResponse;
import api.models.cartModels.ProductSizesResponse;

public interface ICartController {
    // CREATE, ADD - post
    ApiHttpResponse addItemsToCart(String skuId, int quantity);

    // GET - get
    CartResponse getBag();

    ProductSizesResponse getProductDetailsById(String productId);

    InventoryCheckResponse getCartInventory();

    // FULL UPDATE - put

    // PART UPDATE - patch
    ApiHttpResponse editItemsInCart(String skuId, int quantity, String itemId);

    // DELETE - delete
    ApiHttpResponse deleteItemsInCart(String itemId);
}
