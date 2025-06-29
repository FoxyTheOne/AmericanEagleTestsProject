package api.tests.cart;

import api.tests.extensions.GuestTokenExtension;
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
        cartController.addItemsToCart(TEST_SKU_ID, 1)
                .statusCodeIs(202);
    }

    // GET - get
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: check the bag")
    void checkTheBagSmokeTest() {
        cartController.getBag().getData();
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: open quick shop")
    void openQuickShopSmokeTest() {
        cartController.openQuickShopById(TEST_PRODUCT_ID_4)
                .statusCodeIs(200)
                .getProductSizesResponse();
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: get cart inventory")
    void getCartInventorySmokeTest() {
        cartController.getCartInventory()
                .statusCodeIs(200)
                .getInventoryCheckResponse();
    }

    // FULL UPDATE - put

    // PART UPDATE - patch
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: edit items in cart")
    void editItemsInCartSmokeTest() {
        cartController.addItemsToCart(TEST_SKU_ID, 1)
                .statusCodeIs(202);
        String itemId = cartController.getBag()
                .getData()
                .getItems()
                .get(0)
                .getItemId();
        cartController.editItemsInCart(TEST_SKU_ID, 2, itemId)
                .statusCodeIs(202);

    }

    // DELETE - delete
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Smoke test: delete item from cart")
    void deleteItemFromCartSmokeTest() {
        cartController.addItemsToCart(TEST_SKU_ID_3, 1)
                .statusCodeIs(202);
        String itemId = cartController.getBag()
                .getData()
                .getItems()
                .get(0)
                .getItemId();
        cartController.deleteItemsInCart(itemId)
                .statusCodeIs(202);
    }
}
