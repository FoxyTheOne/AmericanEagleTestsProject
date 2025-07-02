package api.tests.cart;

import api.extensions.GuestTokenExtension;
import constants.ApiConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static constants.CommonConstants.*;

@Tags({@Tag(API_TAG), @Tag(SMOKE_TAG)})
@ExtendWith(GuestTokenExtension.class)
class CartApiSmokeTests extends BaseCartApiTests {
    // CREATE, ADD - post
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: add items to cart")
    void addItemsToCartSmokeTest() {
        cartSteps.addItemsToCart(ApiConstants.MAN_CONVERSE.sku, 1);
    }

    // GET - get
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: check the bag")
    void checkTheBagSmokeTest() {
        cartSteps.getItemsInBagList();
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: get product details by id")
    void getProductDetailsByIdSmokeTest() {
        cartController.getProductDetailsById(ApiConstants.MAN_JEANS.productId);
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: get cart inventory")
    void getCartInventorySmokeTest() {
        cartController.getCartInventory();
    }

    // FULL UPDATE - put

    // PART UPDATE - patch
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: edit items in cart")
    void editItemsInCartSmokeTest() {
        String itemId = cartSteps.addItemsToCart(ApiConstants.WOMAN_SKIRT.sku, 1)
                .getIdOfTheFirstItemInBag();
        cartSteps.editItemsInCart(ApiConstants.WOMAN_SKIRT.sku, 2, itemId);
    }

    // DELETE - delete
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: delete item from cart")
    void deleteItemFromCartSmokeTest() {
        String itemId = cartSteps.addItemsToCart(ApiConstants.WOMAN_SKIRT.sku, 1)
                .getIdOfTheFirstItemInBag();
        cartSteps.deleteItemsInCart(itemId);
    }
}
